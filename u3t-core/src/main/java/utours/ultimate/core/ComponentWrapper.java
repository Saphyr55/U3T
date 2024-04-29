package utours.ultimate.core;

import java.util.List;

public sealed interface ComponentWrapper {

    final class Data implements ComponentWrapper {

        private Class<?> componentClass;
        private Object component;

        public Data() {

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

    final class Group implements ComponentWrapper {

        private Class<?> componentClass;
        private List<ComponentWrapper> components;

        public void setComponents(List<ComponentWrapper> components) {
            this.components = components;
        }

        public Class<?> getComponentClass() {
            return componentClass;
        }

        public void setComponentClass(Class<?> componentClass) {
            this.componentClass = componentClass;
        }

        public List<ComponentWrapper> getComponents() {
            return components;
        }

    }

}
