package utours.ultimate.core.provider;

import utours.ultimate.core.*;
import utours.ultimate.core.internal.AnnotationModuleEvaluator;
import utours.ultimate.core.internal.ErrorManager;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.ConstructorProperties;
import utours.ultimate.core.steorotype.Mapping;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationModuleEvaluatorProvider implements ModuleEvaluatorProvider {

    private final ComponentGraph componentGraph;
    private final Map<Class<?>, ComponentId> uniqueMap;
    private final Map<Class<?>, List<ComponentId>> additionalMap;

    public AnnotationModuleEvaluatorProvider(String... packageNames) {
        this.componentGraph = new ComponentGraph();
        this.uniqueMap = new HashMap<>();
        this.additionalMap = new HashMap<>();
        this.setNodes(Arrays.stream(packageNames)
                .flatMap(this::classesOfPackageName)
                .collect(Collectors.toSet()));
    }

    /**
     *
     * @return
     */
    @Override
    public ModuleEvaluator provideModuleEvaluator() {

        Map<Class<?>, Map<ComponentId, MethodHandle>> factoryHandles = new HashMap<>();

        for (int i = 0; i < componentGraph.getComponents().size(); i++) {
            ComponentId componentId = componentGraph.getComponents().get(i);
            setFactoryMethods(factoryHandles, componentId);
        }

        Map<Class<?>, MethodHandle> constructors = setupConstructorsDependencies();

//        factoryHandles.forEach((clazz, map) -> {
//            ComponentId returnId = ComponentId.ofClass(clazz);
//            componentGraph.removeDependenciesOf(returnId);
//            map.forEach((componentId, ignored) -> {
//                componentGraph.addDependency(returnId, componentId);
//            });
//        });

        return new AnnotationModuleEvaluator(
                componentGraph,
                factoryHandles,
                constructors,
                uniqueMap,
                additionalMap
        );
    }

    /**
     * Add all nodes, corresponding to the set of class filtered by component.
     *
     * @param classes classes.
     */
    private void setNodes(Set<Class<?>> classes) {
        // We filter every component, and add it in the graph as a node.
        classes.stream()
                .filter(this::isComponent)
                .forEach(this::addComponentInGraph);
    }

    /**
     * Add component in the graph component.
     *
     * @param clazz the class annotated by Component.
     */
    private void addComponentInGraph(Class<?> clazz) {
        Component component =  clazz.getAnnotation(Component.class);
        ComponentId componentId = getComponentId(clazz, component);
        componentGraph.addComponent(componentId);
    }

    /**
     * Set all factory method, and the eta dependency.
     *
     * @param factoryHandles factory method handle map.
     * @param componentId component id.
     */
    private void setFactoryMethods(Map<Class<?>, Map<ComponentId, MethodHandle>> factoryHandles,
                                   ComponentId componentId) {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class<?> clazz = componentId.clazz();
        List<Throwable> errors = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            try {

                // We filter every method that are annotated by Component.
                if (!isComponent(method)) {
                    continue;
                }

                if (isMappedAndActivate(method)) {
                    processMappedMethodComponent(method);
                 }

                // We get the component annotation.
                var component = method.getAnnotation(Component.class);

                // When the component annotation presice the id,
                // we get the id and replace the component id.
                if (!component.id().isBlank()) {
                    componentId = ComponentId.of(componentId.clazz(), component.id());
                }

                // We look up the method handle from the factory method.
                var mh = lookup.unreflect(method);
                var returnType = mh.type().returnType();
                var returnTypeId = getComponentId(returnType);

                // We add the factory method handle in the map factory handles,
                // if not exist, we create the map.
                factoryHandles
                        .computeIfAbsent(returnType, c -> new HashMap<>())
                        .put(componentId, mh);

                // Add an eta dependency from the return component to the factory component.
                componentGraph.addComponent(returnTypeId);
                componentGraph.addDependency(returnTypeId, componentId);

            } catch (Exception e) {
                errors.add(e);
            }

        }

        ErrorManager.throwErrorsOf(errors);
    }

    private void processMappedMethodComponent(Method method) {

        Mapping mapping = method.getAnnotation(Mapping.class);
        if (!mapping.activate()) return;

        // Determine return type.
        Class<?> componentReturnType = isDeterministicMapping(mapping)
                ? method.getReturnType()
                : mapping.clazz();

        if (!method.getReturnType().isAssignableFrom(componentReturnType)) {
            throw new IllegalStateException("%s is not assignable from %s".formatted(
                    method.getReturnType().getName(),
                    componentReturnType.getName()));
        }

        ComponentId componentId = getComponentId(componentReturnType);

        // Process the mapping type.
        onMappingType(mapping, componentId, componentReturnType);
    }

    /**
     * Determine the dependencies based on the constructors properties.
     *
     * @return every method handle mapped by there corresponding class.
     */
    private Map<Class<?>, MethodHandle> setupConstructorsDependencies() {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Map<Class<?>, MethodHandle> constructors = new HashMap<>();
        List<Throwable> errors = new ArrayList<>();

        List<ComponentId> components = componentGraph.getComponents();
        for (int i = 0; i < components.size(); i++) {
            ComponentId componentId = components.get(i);
            try {
                Class<?> clazz = componentId.clazz();

                // We filter every component that are not an interface.
                // Interfaces has any constructors; logic it's, an interface...
                if (clazz.isInterface()) {
                    continue;
                }

                // When it's a mapping, we set up the class in the interfaces map.
                // And if the mapping is activate.
                if (isMappedAndActivate(clazz)) {
                    setupMappingClass(clazz);
                }

                // We get the constructor annotated by ConstructorProperties,
                // if it's not found we the first constructor.
                Constructor<?> declaredConstructor = getConstructorProperties(clazz);
                Class<?>[] paramTypes = declaredConstructor.getParameterTypes();

                // We add the dependencies from params corresponding to the current component.
                Arrays.stream(paramTypes)
                        .map(this::getComponentId)
                        .filter(componentGraph::hasNode)
                        .forEach(paramId -> componentGraph.addDependency(componentId, paramId));

                // We get constructor method type, and we look up for it.
                // Then it is added in constructors map.
                // If an error appeared, we suppressed it.
                MethodType mt = MethodType.methodType(void.class, paramTypes);
                MethodHandle mh = lookup.findConstructor(clazz, mt);
                constructors.put(clazz, mh);

            } catch (NoSuchMethodException | IllegalAccessException e) {
                errors.add(e);
            }
        }

        ErrorManager.throwErrorsOf(errors);

        return constructors;
    }

    /**
     * Get the first constructor annotated by ConstructorProperties,
     * if there are any constructor annotated, get the first declared constructor.
     * Otherwise, throw an error.
     *
     * @param clazz the class that contain the constructor.
     * @return the constructor.
     */
    private Constructor<?> getConstructorProperties(Class<?> clazz) {

        Constructor<?> declaredConstructor;

        declaredConstructor = ClassProviderManager
                .getFirstDeclaredConstructor(clazz)
                .orElseThrow(IllegalStateException::new);

        declaredConstructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(this::isConstructorProperties)
                .findFirst()
                .orElse(declaredConstructor);

        return declaredConstructor;
    }

    /**
     * Check if the constructor is annotated by ConstructorProperties.
     *
     * @param constructor the constructor to check.
     * @return true if the constructor is annotated by ConstructorProperties, false otherwise.
     */
    private boolean isConstructorProperties(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(ConstructorProperties.class);
    }

    /**
     * Check if the element passing in parameter is annotated by Mapping and is activated.
     *
     * @param element the element to check.
     * @return return true if is activated and is annotated by Mapping.
     */
    private static boolean isMappedAndActivate(AnnotatedElement element) {
        return element.isAnnotationPresent(Mapping.class) &&
                element.getAnnotation(Mapping.class).activate();
    }

    /**
     * We determine the mapping to do with the class passed in parameter.
     *
     * @param clazz the class to map.
     */
    private void setupMappingClass(Class<?> clazz) {

        Mapping mapping = clazz.getAnnotation(Mapping.class);

        ComponentId componentId = getComponentId(clazz);

        Class<?> interfaceClass = !isDeterministicMapping(mapping)
                ? mapping.clazz()
                // The default interface is the first interface implemented.
                // Unable to determine the interface to map, if there are any interface implemented.
                : ClassProviderManager.getFirstInterfaceImplemented(clazz)
                    .orElseThrow(() -> getErrorMissingInterface(clazz));

        ComponentId interfaceId = getComponentId(interfaceClass);

        // Process the mapping type.
        onMappingType(mapping, componentId, interfaceClass);

        // We add a lambda dependency.
        componentGraph.addDependency(interfaceId, componentId);
    }

    /**
     * The process by mapping type.
     *
     * @param mapping the mapping annotation.
     * @param componentId the component id to map.
     * @param clazz the interface clazz.
     */
    private void onMappingType(Mapping mapping, ComponentId componentId, Class<?> clazz) {
        switch (mapping.type()) {
            case Unique -> onUniqueMapping(mapping, componentId, clazz);
            case Additional -> onAdditionalMapping(componentId, clazz);
        }
    }

    /**
     * The processus with an additional mapping.
     *
     * @param componentId
     * @param interfaceClass
     */
    private void onAdditionalMapping(ComponentId componentId, Class<?> interfaceClass) {
        additionalMap.computeIfAbsent(interfaceClass, k -> new ArrayList<>()).add(componentId);
    }

    /**
     * The processus with a unique mapping.
     * Throw an error, when the interface is already mapped.
     *
     * @param mapping the annotation.
     * @param componentId the component id.
     * @param interfaceClass the interface class.
     */
    private void onUniqueMapping(Mapping mapping, ComponentId componentId, Class<?> interfaceClass) {
        if (hasUniqueMapping(mapping, interfaceClass)) {
            throw getErrorUniqueMapping(interfaceClass);
        }
        uniqueMap.put(interfaceClass, componentId);
    }

    /**
     * Return true, if the mapping is activate and the unique map contain the interface class.
     * Otherwise, return false.
     *
     * @param mapping the mapping.
     * @param interfaceClass the interface.
     *
     * @return true if it has a unique mapping, false otherwise.
     */
    private boolean hasUniqueMapping(Mapping mapping, Class<?> interfaceClass) {
        boolean isActivate = mapping.activate();
        return isActivate && uniqueMap.containsKey(interfaceClass);
    }

    /**
     * Get an exception, when the interface class is already mapped.
     *
     * @param interfaceClass the interface class.
     * @return an exception.
     */
    private IllegalStateException getErrorUniqueMapping(Class<?> interfaceClass) {
        String nameClassAlreadyImplemented = uniqueMap.get(interfaceClass).clazz().getName();
        String errorMsg = "Already have "
                + interfaceClass.getName()
                + " mapped with "
                + nameClassAlreadyImplemented;
        return new IllegalStateException(errorMsg);
    }

    /**
     * Get an error when missing interface to map.
     *
     * @param clazz the class.
     * @return an error.
     */
    private IllegalStateException getErrorMissingInterface(Class<?> clazz) {
        return new IllegalStateException("The " + clazz.getName() + " has any interface to map.");
    }

    /**
     * Check if the element passed in parameter is annotated by Component.
     *
     * @param element the element to check.
     * @return Return true if class passed in parameter is annotated by Component return false otherwise.
     */
    private boolean isComponent(AnnotatedElement element) {
        return element.isAnnotationPresent(Component.class);
    }

    private ComponentId getComponentId(Class<?> clazz, Component component) {
        if (component.id().isEmpty()) {
            return ComponentId.ofClass(clazz);
        }
        return ComponentId.of(clazz, component.id());
    }

    private static boolean isDeterministicMapping(Mapping mapping) {
        return mapping.clazz().equals(Class.class);
    }

    public ComponentId getComponentId(Class<?> clazz) {
        return componentGraph.getComponents().stream()
                .filter(componentId -> componentId.clazz().equals(clazz))
                .findFirst()
                .orElseGet(() -> ComponentId.ofClass(clazz));
    }

    public ComponentGraph getComponentGraph() {
        return componentGraph;
    }

    private Stream<? extends Class<?>> classesOfPackageName(String packageName) {
        return ClassProviderManager.classesOf(packageName).stream();
    }



}
