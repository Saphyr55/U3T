package utours.ultimate.core;

public class ComponentWrapper {

    private Class<?> componentClass;
    private Object component;

    public ComponentWrapper() {

    }

    public void setComponent(Object component) {
        this.component = component;
    }

    public Class<?> getComponentClass() {
        return componentClass;
    }

    public void setComponentClass(Class<?> componentClass) {
        this.componentClass = componentClass;
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent() {
        return (T) component;
    }


}
