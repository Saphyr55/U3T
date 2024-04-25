package utours.ultimate.core;

public class ComponentWrapper {

    private Class<?> componentClass;
    private Object component;

    public ComponentWrapper(Class<?> componentClass, Object component) {
        this.componentClass = componentClass;
        this.component = component;
    }

}
