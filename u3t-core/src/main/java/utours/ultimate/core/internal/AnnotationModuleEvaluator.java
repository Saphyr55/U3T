package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ComponentProvider;
import utours.ultimate.core.ComponentWrapper;
import utours.ultimate.core.ModuleEvaluator;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationModuleEvaluator implements ModuleEvaluator {

    private final Map<String, ComponentProvider> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentProvider>> additionalComponents = new HashMap<>();
    private final Map<Class<?>, ComponentProvider> uniqueComponents = new HashMap<>();

    private final Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped;
    private final Map<Class<?>, MethodHandle> constructors;
    private final ComponentGraph componentGraph;

    public AnnotationModuleEvaluator(ComponentGraph componentGraph,
                                     Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped,
                                     Map<Class<?>, MethodHandle> constructors) {

        this.componentGraph = componentGraph;
        this.factoryMethodHandlesMapped = factoryMethodHandlesMapped;
        this.constructors = constructors;
    }


    @Override
    public void evaluate() {
        componentGraph.sort();

        for (var cClass : componentGraph.getComponents().keySet()) {
            if (factoryMethodHandlesMapped.containsKey(cClass)) {
                var e = factoryMethodHandlesMapped.get(cClass);
                processFactoryMethod(e.getKey(), e.getValue(), cClass);
            }
        }

        for (var cClass : componentGraph.getComponents().keySet()) {
            if (constructors.containsKey(cClass)) {
                processConstructor(constructors.get(cClass), cClass);
            }
        }

    }

    private void processFactoryMethod(Class<?> clazz, MethodHandle mh, Class<?> component) {
        try {
            var factory = componentsById.get(clazz.getName()).get().getComponent();
            mh = mh.bindTo(factory);
            var obj = mh.invoke();
            var cw = new ComponentWrapper(component, obj);
            componentsById.put(component.getName(), ComponentProvider.singleton(cw));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void processConstructor(MethodHandle methodHandle, Class<?> cClass) {
        try {
            List<Object> args = new ArrayList<>();
            for (Class<?> paramClass : methodHandle.type().parameterList()) {
                var cw = componentsById.get(paramClass.getName()).get();
                if (cw == null) throw new IllegalStateException();
                args.add(cw.getComponent());
            }
            var object = methodHandle.invokeWithArguments(args);
            var provider = ComponentProvider.singleton(new ComponentWrapper(cClass, object));
            componentsById.put(cClass.getName(), provider);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Class<?>, ComponentProvider> getUniqueComponents() {
        return uniqueComponents;
    }

    @Override
    public Map<Class<?>, List<ComponentProvider>> getAdditionalComponents() {
        return additionalComponents;
    }

    @Override
    public Map<String, ComponentProvider> getComponents() {
        return componentsById;
    }

}
