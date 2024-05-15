package utours.ultimate.core.internal;

import utours.ultimate.core.*;

import java.lang.invoke.MethodHandle;
import java.util.*;

public class AnnotationModuleEvaluator implements ModuleEvaluator {

    private final Map<String, ComponentProvider> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentProvider>> additionalComponents = new HashMap<>();
    private Map<Class<?>, ComponentProvider> uniqueComponents = new HashMap<>();

    private final Map<Class<?>, Map<ComponentId, MethodHandle>> factoryMethodHandlesMapped;
    private final Map<Class<?>, MethodHandle> constructors;
    private final Map<Class<?>, ComponentId> uniqueMap;
    private final ComponentGraph componentGraph;

    public AnnotationModuleEvaluator(ComponentGraph componentGraph,
                                     Map<Class<?>, Map<ComponentId, MethodHandle>> factoryMethodHandlesMapped,
                                     Map<Class<?>, MethodHandle> constructors,
                                     Map<Class<?>, ComponentId> uniqueMap) {

        this.componentGraph = componentGraph;
        this.factoryMethodHandlesMapped = factoryMethodHandlesMapped;
        this.constructors = constructors;
        this.uniqueMap = uniqueMap;
    }

    @Override
    public void evaluate() {

        for (ComponentId componentId : componentGraph.getTopologicalOrderingComponents()) {
            Class<?> clazz = componentId.getClazz();

            System.out.println(clazz.getName());

            if (uniqueMap.containsKey(clazz)) {

                processUnique(clazz);

            } else if (factoryMethodHandlesMapped.containsKey(clazz)) {

                var handles = factoryMethodHandlesMapped.get(clazz);
                handles.entrySet().stream().findFirst().ifPresent(e -> {
                    processFactoryMethod(e.getKey(), e.getValue(), componentId);
                });

            } else if (constructors.containsKey(clazz)) {
                processConstructor(constructors.get(clazz), componentId);
            }

        }

    }

    private void processUnique(Class<?> interfaceClass) {
        var clazz = uniqueMap.get(interfaceClass);
        var cwProvider = componentsById.get(clazz.getId());
        componentsById.put(interfaceClass.getName(), cwProvider);
        uniqueComponents.put(interfaceClass, cwProvider);
    }

    private void processFactoryMethod(ComponentId componentIdFactory, MethodHandle mh, ComponentId componentId) {
        try {

            var factory = componentsById
                    .get(componentIdFactory.getId())
                    .get()
                    .getComponent();

            mh = mh.bindTo(factory);
            var obj = mh.invoke();
            var cw = new ComponentWrapper(componentId.getClazz(), obj);

            componentsById.put(componentId.getId(), ComponentProvider.singleton(cw));

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void processConstructor(MethodHandle methodHandle, ComponentId componentId) {
        try {

            List<Object> args = new ArrayList<>();
            List<Class<?>> parameters = methodHandle.type().parameterList();

            for (Class<?> param : parameters) {
                var id = getComponentId(param).getId();
                var cwProvider = componentsById.get(id);
                var cw = cwProvider.get();
                if (cw == null) throw new IllegalStateException();
                args.add(cw.getComponent());
            }

            var object = methodHandle.invokeWithArguments(args);
            var cw = new ComponentWrapper(componentId.getClazz(), object);
            var provider = ComponentProvider.singleton(cw);

            componentsById.put(componentId.getId(), provider);

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Class<?>, ComponentProvider> getUniqueComponents() {
        return uniqueComponents;
    }

    private Map.Entry<Class<?>, ComponentProvider> toUnique(Map.Entry<String, ComponentProvider> e) {
        try {
            var clazz = Class.forName(e.getKey());
            return Map.entry(clazz, e.getValue());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Map<Class<?>, List<ComponentProvider>> getAdditionalComponents() {
        return additionalComponents;
    }

    @Override
    public Map<String, ComponentProvider> getComponents() {
        return componentsById;
    }

    public ComponentId getComponentId(Class<?> clazz) {
        return componentGraph.getComponents().stream()
                .filter(componentId -> componentId.getClazz().equals(clazz))
                .findFirst()
                .orElseGet(() -> ComponentId.ofClass(clazz));
    }

}
