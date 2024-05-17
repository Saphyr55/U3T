package utours.ultimate.core.internal;

import utours.ultimate.core.*;

import java.lang.invoke.MethodHandle;
import java.util.*;
import java.util.function.Supplier;

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

        componentGraph.printGraph();

        for (ComponentId componentId : componentIds) {
            System.out.println(componentId.clazz().getName());
        }

        List<Throwable> errors = ErrorManager.forEachOf(componentIds, this::processComponentId);
        ErrorManager.throwErrorsOf(errors);
    }

    private void processComponentId(ComponentId componentId) {

        Class<?> clazz = componentId.clazz();

        if (factoryMethodHandlesMapped.containsKey(clazz)) {
            factoryMethodHandlesMapped.get(clazz)
                    .entrySet().stream()
                    .findFirst()
                    .ifPresent(e -> {
                        processFactoryMethod(e.getKey(), e.getValue(), componentId);
                    });

        } else if (constructors.containsKey(clazz)) {
            processConstructor(constructors.get(clazz), componentId);
        }

        if (additionalMap.containsKey(clazz)) {
            processAdditional(componentId);
        } else if (uniqueMap.containsKey(clazz)) {
            processUnique(componentId);
        }

    }

    private void processAdditional(ComponentId componentId) {

        List<ComponentId> classes = Optional.ofNullable(additionalMap.get(componentId.clazz()))
                .orElseThrow(() -> new IllegalStateException(componentId.identifier() + "was not found in additional map."));

        List<ComponentProvider> providers = classes.stream()
                .map(aClass -> componentsById.get(aClass.identifier()))
                .toList();

        additionalComponents.put(componentId.clazz(), providers);
    }

    private void processUnique(ComponentId interfaceId) {

        ComponentId componentId = Optional
                .ofNullable(uniqueMap.get(interfaceId.clazz()))
                .orElseThrow(() -> new IllegalStateException(interfaceId.identifier() + " was not found in unique map."));

        ComponentProvider provider = Optional
                .ofNullable(componentsById.get(componentId.identifier()))
                .orElseThrow(() -> new IllegalStateException(componentId.identifier() + " was not found in components on processing unique component."));

        componentsById.put(interfaceId.clazz().getName(), provider);
        uniqueComponents.put(interfaceId.clazz(), provider);
    }

    private void processFactoryMethod(ComponentId componentIdFactory,
                                      MethodHandle mh,
                                      ComponentId componentId) {
        try {

            ComponentProvider provider = componentsById.get(componentIdFactory.identifier());
            Object factory = Optional.ofNullable(provider)
                    .orElseThrow(() -> new IllegalStateException(
                            "%s was not found in components on processing method of '%s'."
                            .formatted(componentIdFactory.identifier(), componentId.identifier())))
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

                String identifier = getComponentId(param).identifier();

                ComponentProvider provider = Optional
                        .ofNullable(componentsById.get(identifier))
                        .orElseThrow(() -> new IllegalStateException(identifier
                                + " was not found on processing constructor for the component '"
                                + componentId.identifier() +"'."));

                ComponentWrapper cw = Optional
                        .ofNullable(provider.get())
                        .orElseThrow(() -> new IllegalStateException("The provider of '"
                                + identifier
                                + "' provide nothing for the component '"
                                + componentId.identifier() +"'."));

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
