package utours.ultimate.core.internal;

import utours.ultimate.core.*;

import java.lang.invoke.MethodHandle;
import java.util.*;

public class AnnotationModuleEvaluator implements ModuleEvaluator {

    private final Map<String, ComponentProvider> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentProvider>> additionalComponents = new HashMap<>();
    private final Map<Class<?>, ComponentProvider> uniqueComponents = new HashMap<>();

    private final Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped;
    private final Map<Class<?>, MethodHandle> constructors;
    private final Map<Class<?>, Class<?>> mappedInterfaces;
    private final ComponentGraph componentGraph;

    public AnnotationModuleEvaluator(ComponentGraph componentGraph,
                                     Map<Class<?>, Map.Entry<Class<?>, MethodHandle>> factoryMethodHandlesMapped,
                                     Map<Class<?>, MethodHandle> constructors,
                                     Map<Class<?>, Class<?>> mappedInterfaces) {

        this.componentGraph = componentGraph;
        this.factoryMethodHandlesMapped = factoryMethodHandlesMapped;
        this.constructors = constructors;
        this.mappedInterfaces = mappedInterfaces;
    }

    @Override
    public void evaluate() {

        for (var clazz : componentGraph.getSortedComponents()) {

            System.out.println(clazz.getName());

            if (mappedInterfaces.containsKey(clazz)) {
                clazz = mappedInterfaces.get(clazz);
            }

            if (factoryMethodHandlesMapped.containsKey(clazz)) {
                var e = factoryMethodHandlesMapped.get(clazz);
                processFactoryMethod(e.getKey(), e.getValue(), clazz);
            } else if (constructors.containsKey(clazz)) {
                processConstructor(constructors.get(clazz), clazz);
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
                var cwProvider = componentsById.get(paramClass.getName());
                var cw = cwProvider.get();
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
