package utours.ultimate.core.provider;

import utours.ultimate.core.*;
import utours.ultimate.core.internal.AnnotationModuleEvaluator;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.ConstructorProperties;
import utours.ultimate.core.steorotype.FactoryMethod;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.*;

public class AnnotationModuleEvaluatorProvider implements ModuleEvaluatorProvider {

    private final Set<Class<?>> classes;
    private final ComponentGraph componentGraph;

    public AnnotationModuleEvaluatorProvider(String... packageNames) {

        this.componentGraph = new ComponentGraph();
        this.classes = new HashSet<>();

        for (String packageName : packageNames) {
            classes.addAll(ClassProvider.classesOf(packageName));
        }

    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public ModuleEvaluator provideModuleEvaluator() {

        setupGraph();
        var constructors = setupConstructorsDependencies();

        Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped = new HashMap<>();

        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                setFactoryMethods(factoryMethodHandlesMapped, clazz);
            }
        }

        return new AnnotationModuleEvaluator(componentGraph, factoryMethodHandlesMapped, constructors, new HashMap<>());
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

        for (var clazz : classes) {

            if (clazz.isAnnotationPresent(Component.class) && !clazz.isInterface() && !clazz.isAnonymousClass()) {

                var declaredConstructor = getConstructorProperties(clazz);
                var paramTypes = declaredConstructor.getParameterTypes();

                for (Class<?> paramType : paramTypes) {

                    if (isInComponentGraph(paramType)) {
                        componentGraph.addDependency(clazz, paramType);
                    }

                }

                try {
                    var mt = MethodType.methodType(void.class, paramTypes);
                    constructors.put(clazz, lookup.findConstructor(clazz, mt));
                } catch (NoSuchMethodException | IllegalAccessException e) {
                    exception = Optional.ofNullable(exception).orElseGet(IllegalStateException::new);
                    exception.addSuppressed(e);
                }

            }
        }

        if (Optional.ofNullable(exception).isPresent())
            throw new RuntimeException(exception);

        return constructors;
    }

    private void setupGraph() {
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
