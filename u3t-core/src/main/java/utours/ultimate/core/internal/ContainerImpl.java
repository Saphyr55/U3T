package utours.ultimate.core.internal;

import utours.ultimate.core.Container;

import java.util.*;

public class ContainerImpl implements Container {

    private final Map<Class<?>, List<Object>> additionalComponents;
    private final Map<Class<?>, Object> uniqueComponents;
    private final Map<String, Object> components;

    public ContainerImpl() {
        this.additionalComponents = new HashMap<>();
        this.uniqueComponents = new HashMap<>();
        this.components = new HashMap<>();
    }

    @Override
    public <T> List<T> getAdditionalComponent(Class<T> componentClass) {
        return additionalComponents.computeIfAbsent(componentClass, this::listSupplier).stream()
                .map(componentClass::cast)
                .toList();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T getUniqueComponent(Class<T> componentClass) {
        return (T) uniqueComponents.get(componentClass);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T getComponent(String identifier) {
        return (T) components.get(identifier);
    }

    @Override
    public <T> void storeUniqueComponent(Class<T> tClass, T component) {
        uniqueComponents.put(tClass, component);
    }

    @Override
    public <T> void removeUniqueComponent(Class<T> tClass) {
        uniqueComponents.remove(tClass);
    }

    @Override
    public <T> void storeAdditionalComponent(Class<T> tClass, T component) {
        additionalComponents
                .computeIfAbsent(tClass, this::listSupplier)
                .add(component);
    }

    @Override
    public <T> void removeAdditionalComponents(Class<T> tClass) {
        additionalComponents.remove(tClass);
    }

    @Override
    public <T> void storeComponent(String id, T component) {
        components.put(id, component);
    }

    private <T> List<Object> listSupplier(Class<T> componentClass) {
        return new LinkedList<>();
    }

}
