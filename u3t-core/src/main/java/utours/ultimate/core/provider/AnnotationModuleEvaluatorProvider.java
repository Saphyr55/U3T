package utours.ultimate.core.provider;

import utours.ultimate.core.*;
import utours.ultimate.core.internal.AnnotationModuleEvaluator;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.ConstructorProperties;
import utours.ultimate.core.steorotype.FactoryMethod;
import utours.ultimate.core.steorotype.Mapping;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationModuleEvaluatorProvider implements ModuleEvaluatorProvider {

    private final ComponentGraph componentGraph;
    private final Map<Class<?>, ComponentId> uniqueMap;

    public AnnotationModuleEvaluatorProvider(String... packageNames) {
        this.componentGraph = new ComponentGraph();
        this.uniqueMap = new HashMap<>();
        this.setupNodes(Arrays.stream(packageNames)
                .flatMap(this::classesOfPackageName)
                .collect(Collectors.toSet()));
    }


    @Override
    public ModuleEvaluator provideModuleEvaluator() {

        Map<Class<?>, Map<ComponentId, MethodHandle>> factoryHandles = new HashMap<>();
        Map<Class<?>, MethodHandle> constructors = setupConstructorsDependencies();

        for (ComponentId componentId : componentGraph.getComponents()) {
            setFactoryMethods(factoryHandles, componentId);
        }

        return new AnnotationModuleEvaluator(
                componentGraph,
                factoryHandles,
                constructors,
                uniqueMap
        );
    }

    private void setFactoryMethods(Map<Class<?>, Map<ComponentId, MethodHandle>> factoryHandles,
                                   ComponentId componentId) {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class<?> clazz = componentId.getClazz();
        List<Throwable> errors = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            try {

                // We filter every method that not annotated by FactoryMethod.
                if (!method.isAnnotationPresent(FactoryMethod.class)) {
                    continue;
                }

                // We look up the method handle from the factory method.
                var mh = lookup.unreflect(method);
                var returnType = mh.type().returnType();
                var componentReturnTypeId = getComponentId(returnType);

                // We add the factory method handle in the map factory handles,
                // if not exist, we create the map.
                factoryHandles
                        .computeIfAbsent(returnType, c -> new HashMap<>())
                        .put(componentId, mh);

                // Add an eta dependency from the return component to the factory component.
                componentGraph.addDependency(componentReturnTypeId, componentId);

            } catch (Exception e) {
                errors.add(e);
            }

        }

        throwOnErrors(errors);
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

        for (ComponentId currentComponentId : componentGraph.getComponents()) {
            try {
                Class<?> clazz = currentComponentId.getClazz();

                // We filter every component that are not an interface.
                if (clazz.isInterface()) {
                    continue;
                }

                // When it's a mapping, we set up the class in the interfaces map.
                if (clazz.isAnnotationPresent(Mapping.class)) {
                    setupMappingClass(clazz);
                }

                // We get the constructor annotated by ConstructorProperties,
                // if it's not found get the default constructor.
                Constructor<?> declaredConstructor = getConstructorProperties(clazz);
                Class<?>[] paramTypes = declaredConstructor.getParameterTypes();

                // We add the dependencies corresponding to the current component.
                Arrays.stream(paramTypes)
                        .map(this::getComponentId)
                        .filter(componentGraph::hasNode)
                        .forEach(id -> {
                            componentGraph.addDependency(currentComponentId, id);
                        });

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

        throwOnErrors(errors);

        return constructors;
    }

    private void setupMappingClass(Class<?> clazz) {

        Mapping mapping = clazz.getAnnotation(Mapping.class);
        ComponentId componentId = getComponentId(clazz);

        Class<?> interfaceClass = isDeterministicMapping(mapping)
                // The default interface is the first interface implemented.
                // Unable to determine the interface to map, if there are any interface implemented.
                ? getFirstInterfaceImplemented(clazz)
                : mapping.clazz();

        ComponentId interfaceId = getComponentId(interfaceClass);

        // Check the mapping type.
        switch (mapping.type()) {
            case Unique -> {

                if (hasUniqueMapping(mapping, interfaceClass)) {
                    throw getErrorUniqueMapping(interfaceClass);
                }

                if (mapping.activate()) {
                    uniqueMap.put(interfaceClass, componentId);
                }

            }
            case Additional -> throw new RuntimeException("Not implemented yet.");
        }

        // We add a lambda dependency.
        componentGraph.addDependency(interfaceId, componentId);
    }

    private boolean hasUniqueMapping(Mapping mapping, Class<?> interfaceClass) {
        boolean isActivate = mapping.activate();
        return isActivate && uniqueMap.containsKey(interfaceClass);
    }

    private IllegalStateException getErrorUniqueMapping(Class<?> interfaceClass) {
        String nameClassAlreadyImplemented = uniqueMap.get(interfaceClass).getClazz().getName();
        String errorMsg = "Already have "
                + interfaceClass.getName()
                + " mapped with "
                + nameClassAlreadyImplemented;
        return new IllegalStateException(errorMsg);
    }

    private static Class<?> getFirstInterfaceImplemented(Class<?> clazz) {
        if (hasInterfaces(clazz)) {
            return clazz.getInterfaces()[0];
        }
        throw new IllegalStateException("The " + clazz.getName() + " has any interface to map.");
    }

    private void setupNodes(Set<Class<?>> classes) {
        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                Component component = clazz.getAnnotation(Component.class);
                componentGraph.addComponent(getComponentId(clazz, component));
            }
        }
    }

    private ComponentId getComponentId(Class<?> clazz, Component component) {
        if (component.id().isEmpty()) {
            return ComponentId.ofClass(clazz);
        }
        return ComponentId.of(clazz, component.id());
    }

    private Constructor<?> getConstructorProperties(Class<?> clazz) {

        Constructor<?> declaredConstructor = getFirstDeclaredConstructor(clazz);
        boolean found = false;

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(ConstructorProperties.class) && !found) {
                declaredConstructor = constructor;
                found = true;
            }
        }

        return declaredConstructor;
    }

    private static Constructor<?> getFirstDeclaredConstructor(Class<?> clazz) {
        if (clazz.getDeclaredConstructors().length < 1) return null;
        return clazz.getDeclaredConstructors()[0];
    }

    private static boolean isDeterministicMapping(Mapping mapping) {
        return mapping.clazz().equals(Class.class);
    }

    private static boolean hasInterfaces(Class<?> clazz) {
        return clazz.getInterfaces().length > 0;
    }

    public ComponentId getComponentId(Class<?> clazz) {
        return componentGraph.getComponents().stream()
                .filter(componentId -> componentId.getClazz().equals(clazz))
                .findFirst()
                .orElseGet(() -> ComponentId.ofClass(clazz));
    }

    public ComponentGraph getComponentGraph() {
        return componentGraph;
    }

    private Stream<? extends Class<?>> classesOfPackageName(String packageName) {
        return ClassProviderManager.classesOf(packageName).stream();
    }

    private static void throwOnErrors(List<Throwable> errors) {
        if (!errors.isEmpty()) {
            throw errors.stream()
                    .reduce(AnnotationModuleEvaluatorProvider::throwableReducer)
                    .map(IllegalStateException::new)
                    .orElseThrow();
        }
    }

    private static Throwable throwableReducer(Throwable e, Throwable e2) {
        e.addSuppressed(e2);
        return e;
    }

}
