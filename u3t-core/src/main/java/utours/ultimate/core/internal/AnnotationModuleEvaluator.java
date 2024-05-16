package utours.ultimate.core.internal;

import utours.ultimate.core.*;

import java.lang.invoke.MethodHandle;
import java.util.*;
import java.util.stream.IntStream;

public class AnnotationModuleEvaluator implements ModuleEvaluator {

    private final Map<String, ComponentProvider> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentProvider>> additionalComponents = new HashMap<>();
    private final Map<Class<?>, ComponentProvider> uniqueComponents = new HashMap<>();

    private final Map<Class<?>, Map<ComponentId, MethodHandle>> factoryMethodHandlesMapped;
    private final Map<Class<?>, MethodHandle> constructors;
    private final Map<Class<?>, ComponentId> uniqueMap;
    private final Map<Class<?>, List<ComponentId>> additionalMap;
    private final ComponentGraph componentGraph;

    public AnnotationModuleEvaluator(ComponentGraph componentGraph,
                                     Map<Class<?>, Map<ComponentId, MethodHandle>> factoryMethodHandlesMapped,
                                     Map<Class<?>, MethodHandle> constructors,
                                     Map<Class<?>, ComponentId> uniqueMap,
                                     Map<Class<?>, List<ComponentId>> additionalMap) {

        this.componentGraph = componentGraph;
        this.factoryMethodHandlesMapped = factoryMethodHandlesMapped;
        this.constructors = constructors;
        this.uniqueMap = uniqueMap;
        this.additionalMap = additionalMap;
    }

    @Override
    public void evaluate() {
        List<ComponentId> componentIds = componentGraph.getTopologicalOrderingComponents();
        List<Throwable> errors = ErrorManager.forEachOf(componentIds, this::processComponentId);
        ErrorManager.throwErrorsOf(errors);
    }

    private void processComponentId(ComponentId componentId) {
        Class<?> clazz = componentId.clazz();

        System.out.println(clazz.getName());

        if (additionalMap.containsKey(clazz)) {
            processAdditional(componentId);
        } else if (uniqueMap.containsKey(clazz)) {
            processUnique(componentId);
        } else if (factoryMethodHandlesMapped.containsKey(clazz)) {
            var handles = factoryMethodHandlesMapped.get(clazz);
            handles.entrySet().stream().findFirst().ifPresent(e -> {
                processFactoryMethod(e.getKey(), e.getValue(), componentId);
            });
        } else if (constructors.containsKey(clazz)) {
            processConstructor(constructors.get(clazz), componentId);
        }

    }

    private void processAdditional(ComponentId componentId) {
        List<ComponentId> classes = additionalMap.get(componentId.clazz());
        List<ComponentProvider> providers = classes.stream()
                .map(aClass -> componentsById.get(aClass.identifier()))
                .toList();
        additionalComponents.put(componentId.clazz(), providers);
    }

    private void processUnique(ComponentId interfaceId) {
        ComponentId componentId = uniqueMap.get(interfaceId.clazz());
        ComponentProvider provider = componentsById.get(componentId.identifier());
        componentsById.put(interfaceId.clazz().getName(), provider);
        uniqueComponents.put(interfaceId.clazz(), provider);
    }

    private void processFactoryMethod(ComponentId componentIdFactory, MethodHandle mh, ComponentId componentId) {
        try {
            Object factory = componentsById
                    .get(componentIdFactory.identifier())
                    .get()
                    .getComponent();

            mh = mh.bindTo(factory);

            Object result = mh.invoke();
            ComponentWrapper cw = new ComponentWrapper(componentId.clazz(), result);
            String identifier = componentId.identifier();

            componentsById.put(identifier, ComponentProvider.singleton(cw));

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void processConstructor(MethodHandle methodHandle, ComponentId componentId) {
        try {

            List<Object> args = new ArrayList<>();
            List<Class<?>> parameters = methodHandle.type().parameterList();

            for (Class<?> param : parameters) {
                var identifier = getComponentId(param).identifier();
                var cwProvider = componentsById.get(identifier);

                if (cwProvider == null) {
                    throw new IllegalStateException(identifier + " was not found.");
                }

                var cw = cwProvider.get();

                if (cw == null) {
                    throw new IllegalStateException("The provider of " + identifier + " provide nothing.");
                }

                args.add(cw.getComponent());
            }

            var object = methodHandle.invokeWithArguments(args);
            var cw = new ComponentWrapper(componentId.clazz(), object);
            var provider = ComponentProvider.singleton(cw);

            componentsById.put(componentId.identifier(), provider);

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

    public ComponentId getComponentId(Class<?> clazz) {
        return componentGraph.getComponents().stream()
                .filter(componentId -> componentId.clazz().equals(clazz))
                .findFirst()
                .orElseGet(() -> ComponentId.ofClass(clazz));
    }

}
