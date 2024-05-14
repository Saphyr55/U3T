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
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationModuleEvaluatorProvider implements ModuleEvaluatorProvider {

    private final ComponentGraph componentGraph;
    private final Map<Class<?>, Class<?>> mappedInterfaces;

    public AnnotationModuleEvaluatorProvider(String... packageNames) {
        this.componentGraph = new ComponentGraph();
        this.mappedInterfaces = new HashMap<>();
        setupNodes(Arrays.stream(packageNames)
                .flatMap(packageName -> ClassProvider.classesOf(packageName).stream())
                .collect(Collectors.toSet()));
    }

    @Override
    public ModuleEvaluator provideModuleEvaluator() {

        Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped = new HashMap<>();

        var constructors = setupConstructorsDependencies();
        for (var clazz : componentGraph.getComponents()) {
            setFactoryMethods(factoryMethodHandlesMapped, clazz);
        }

        return new AnnotationModuleEvaluator(componentGraph, factoryMethodHandlesMapped, constructors, mappedInterfaces);
    }

    private void setFactoryMethods(Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped, Class<?> clazz) {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Exception exception = null;

        for (var method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(FactoryMethod.class)) {
                try {
                    var mh = lookup.unreflect(method);
                    var returnType = mh.type().returnType();
                    factoryMethodHandlesMapped.put(returnType, Map.entry(clazz, mh));
                    componentGraph.addDependency(returnType, clazz);
                } catch (Exception e) {
                    exception = Optional.ofNullable(exception).orElseGet(IllegalStateException::new);
                    exception.addSuppressed(e);
                }
            }
        }

        if (Optional.ofNullable(exception).isPresent())
            throw new RuntimeException(exception);

    }

    private Map<Class<?>, MethodHandle> setupConstructorsDependencies() {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Map<Class<?>, MethodHandle> constructors = new HashMap<>();
        Exception exception = null;

        for (var clazz : componentGraph.getComponents()) {

            if (clazz.isInterface()) continue;

            if (clazz.isAnnotationPresent(Mapping.class)) {
                setupMappingClass(clazz);
            }

            var declaredConstructor = getConstructorProperties(clazz);
            var paramTypes = declaredConstructor.getParameterTypes();

            Arrays.stream(paramTypes)
                    .filter(componentGraph::hasNode)
                    .forEach(pt -> componentGraph.addDependency(clazz, pt));

            try {
                var mt = MethodType.methodType(void.class, paramTypes);
                constructors.put(clazz, lookup.findConstructor(clazz, mt));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                exception = Optional.ofNullable(exception).orElseGet(IllegalStateException::new);
                exception.addSuppressed(e);
            }

        }

        if (Optional.ofNullable(exception).isPresent())
            throw new RuntimeException(exception);

        return constructors;
    }

    private void setupMappingClass(Class<?> clazz) {
        Mapping mapping = clazz.getAnnotation(Mapping.class);
        Class<?> interfaceClass;
        if (mapping.clazz().equals(Class.class)) {
            if (clazz.getInterfaces().length == 0) throw new IllegalStateException();
            interfaceClass = clazz.getInterfaces()[0];
        } else {
            interfaceClass = mapping.clazz();
        }
        componentGraph.addDependency(interfaceClass, clazz);
        mappedInterfaces.put(interfaceClass, clazz);
    }

    private void setupNodes(Set<Class<?>> classes) {
        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                componentGraph.addComponent(clazz);
            }
        }
    }

    private boolean isInComponentGraph(Class<?> clazz) {
        return componentGraph.getComponents().contains(clazz);
    }

    private Constructor<?> getConstructorProperties(Class<?> clazz) {

        var declaredConstructor = clazz.getDeclaredConstructors()[0];
        var found = false;
        for (var constructor : clazz.getDeclaredConstructors()) {
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

}
