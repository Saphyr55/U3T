package utours.ultimate.core.provider;

import utours.ultimate.core.*;
import utours.ultimate.core.Module;
import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.ConstructorProperty;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public class AnnotationModuleProvider implements ModuleProvider {

    private final Set<Class<?>> classes;
    private final ComponentGraph componentGraph;

    public AnnotationModuleProvider(String... packageNames) {
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
    public Module provideModule() {

        setupGraph();
        setupDependencies();

        return new Module();
    }

    private void setupDependencies() {
        for (var clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                var declaredConstructor = getConstructorProperty(clazz);
                var paramTypes = declaredConstructor.getParameterTypes();
                for (Class<?> paramType : paramTypes) {
                    if (isInComponentGraph(paramType)) {
                        componentGraph.addEdge(clazz, paramType);
                    }
                }
            }
        }

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

    private Constructor<?> getConstructorProperty(Class<?> clazz) {
        var declaredConstructor = clazz.getDeclaredConstructors()[0];
        for (var constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(ConstructorProperty.class)) {
                declaredConstructor = constructor;
                break;
            }
        }
        return declaredConstructor;
    }

    public Module.Factory processFactory(Class<?> clazz) {
        Module.Factory factory = new Module.Factory();
        return null;
    }

    public Module.Component processComponent(Component component, Class<?> clazz) {

        Module.Component mComponent = new Module.Component();

        if (component.id().isEmpty())
            mComponent.setId(clazz.getName());
        else
            mComponent.setId(component.id());

        Module.ConstructorArgs constructorArgs = new Module.ConstructorArgs();


        return mComponent;
    }

    public ComponentGraph getComponentGraph() {
        return componentGraph;
    }

}
