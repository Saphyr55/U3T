package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentFactory;
import utours.ultimate.core.Container;

import java.util.List;
import java.util.Map;

public class ContainerImpl implements Container {

    private final ComponentFactory componentFactory;

    private Map<Class<?>, List<Object>> additionalComponents;
    private Map<Class<?>, Object> components;

    public ContainerImpl(ComponentFactory componentFactory) {
        this.componentFactory = componentFactory;
    }

    public void register(Class<?> componentClass) {
        components.put(componentClass, componentFactory.createComponent(this, componentClass));
    }

    @Override
    public <T> List<T> getAdditionalComponent(Class<T> componentClass) {
        return List.of();
    }

    @Override
    public <T> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    @Override
    public <T> void storeComponent(Class<T> tClass, T component) {

    }

    @Override
    public <T> void removeComponent(Class<T> tClass) {

    }

    @Override
    public <T> void storeAdditionalComponent(Class<T> tClass, T component) {

    }

    @Override
    public <T> void removeAdditionalComponents(Class<T> tClass) {

    }

}
