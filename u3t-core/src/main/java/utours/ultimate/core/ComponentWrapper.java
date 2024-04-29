package utours.ultimate.core;

public class ComponentWrapper {

    private Class<?> componentClass;
    private Object component;

    public static <T> ComponentWrapper fromObject(T t) {
        ComponentWrapper cw = new ComponentWrapper();
        cw.componentClass = t.getClass();
        cw.component = t;
        return cw;
    }

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
