package utours.ultimate.core.internal;

import java.util.List;

public class ComponentRegistry {

    private List<Class<?>> components;

    public ComponentRegistry register(Class<?> componentClass) {
        components.add(componentClass);
        return this;
    }

    public List<Class<?>> getComponents() {
        return components;
    }

}
