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

public class AnnotationModuleEvaluatorProvider implements ModuleEvaluatorProvider {

    private final ComponentGraph componentGraph;
    private final Map<Class<?>, Class<?>> mappedInterfaces;

    public AnnotationModuleEvaluatorProvider(String... packageNames) {
        this.componentGraph = new ComponentGraph();
        this.mappedInterfaces = new HashMap<>();
        this.setupNodes(Arrays.stream(packageNames)
                .flatMap(packageName -> ClassProviderManager.classesOf(packageName).stream())
                .collect(Collectors.toSet()));
    }

    @Override
    public ModuleEvaluator provideModuleEvaluator() {

        Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped = new HashMap<>();

        var constructors = setupConstructorsDependencies();

        for (ComponentId componentId : componentGraph.getComponents()) {
            setFactoryMethods(factoryMethodHandlesMapped, componentId);
        }

        return new AnnotationModuleEvaluator(componentGraph, factoryMethodHandlesMapped, constructors, mappedInterfaces);
    }

    private void setFactoryMethods(Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped,
                                   ComponentId componentId) {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Exception exception = null;
        Class<?> clazz = componentId.getClazz();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(FactoryMethod.class)) {
                try {
                    var mh = lookup.unreflect(method);
                    var returnType = mh.type().returnType();
                    factoryMethodHandlesMapped.put(returnType, Map.entry(clazz, mh));
                    componentGraph.addDependency(getComponentId(returnType), componentId);
                } catch (Exception e) {
                    exception = Optional.ofNullable(exception).orElseGet(IllegalStateException::new);
                    exception.addSuppressed(e);
                }
            }
        }

        if (Optional.ofNullable(exception).isPresent()) {
            throw new RuntimeException(exception);
        }

    }

    private Map<Class<?>, MethodHandle> setupConstructorsDependencies() {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Map<Class<?>, MethodHandle> constructors = new HashMap<>();
        Exception exception = null;

        for (ComponentId componentId : componentGraph.getComponents()) {

            Class<?> clazz = componentId.getClazz();

            if (clazz.isInterface()) {
                continue;
            }

            if (clazz.isAnnotationPresent(Mapping.class)) {
                setupMappingClass(clazz);
            }

            Constructor<?> declaredConstructor = getConstructorProperties(clazz);
            Class<?>[] paramTypes = declaredConstructor.getParameterTypes();

            Arrays.stream(paramTypes)
                    .map(this::getComponentId)
                    .filter(componentGraph::hasNode)
                    .forEach(id -> componentGraph.addDependency(componentId, id));

            try {
                var mt = MethodType.methodType(void.class, paramTypes);
                constructors.put(clazz, lookup.findConstructor(clazz, mt));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                exception = Optional.ofNullable(exception).orElseGet(IllegalStateException::new);
                exception.addSuppressed(e);
            }

        }

        if (Optional.ofNullable(exception).isPresent()) {
            throw new RuntimeException(exception);
        }

        return constructors;
    }

    private void setupMappingClass(Class<?> clazz) {

        ComponentId componentId = getComponentId(clazz);
        Mapping mapping = clazz.getAnnotation(Mapping.class);

        Class<?> interfaceClass;

        if (isDeterministicMapping(mapping)) {

            if (!hasInterfaces(clazz)) {
                throw new IllegalStateException("The " + clazz.getName() + " has any interface to map.");
            }

            interfaceClass = clazz.getInterfaces()[0];
        } else {
            interfaceClass = mapping.clazz();
        }

        componentGraph.addDependency(getComponentId(interfaceClass), componentId);
        mappedInterfaces.put(interfaceClass, clazz);
    }

    private static boolean isDeterministicMapping(Mapping mapping) {
        return mapping.clazz().equals(Class.class);
    }

    private static boolean hasInterfaces(Class<?> clazz) {
        return clazz.getInterfaces().length > 0;
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

        Constructor<?> declaredConstructor = clazz.getDeclaredConstructors()[0];
        boolean found = false;
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(ConstructorProperties.class) && !found) {
                declaredConstructor = constructor;
                found = true;
            }
        }

        return declaredConstructor;
    }

    public ComponentGraph getComponentGraph() {
        return componentGraph;
    }

    private ComponentId getComponentId(Class<?> clazz) {
        return componentGraph.getComponents().stream()
                .filter(componentId -> componentId.getClazz().equals(clazz))
                .findFirst()
                .orElseGet(() -> ComponentId.ofClass(clazz));
    }

}
