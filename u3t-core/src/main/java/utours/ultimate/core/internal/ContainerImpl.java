package utours.ultimate.core.internal;

import utours.ultimate.core.Container;

import java.util.*;

public class ContainerImpl implements Container {

    private final Map<Class<?>, List<Object>> additionalComponents;
    private final Map<Class<?>, Object> components;

    public ContainerImpl() {
        this.additionalComponents = new HashMap<>();
        this.components = new HashMap<>();
    }

    @Override
    public <T> List<T> getAdditionalComponent(Class<T> componentClass) {
        return additionalComponents.computeIfAbsent(componentClass, this::listSupplier).stream()
                .map(componentClass::cast)
                .toList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    @Override
    public <T> void storeComponent(Class<T> tClass, T component) {
        components.put(tClass, component);
    }

    @Override
    public <T> void removeComponent(Class<T> tClass) {
        components.remove(tClass);
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

    private <T> List<Object> listSupplier(Class<T> componentClass) {
        return new LinkedList<>();
    }

}
