package utours.ultimate.core;

import java.util.Arrays;
import java.util.List;

public class Module {

    public static class AdditionalComponent {
        private List<Component> components;
        private List<RefComponent> refComponents;
        private String className;

        public List<Component> getComponents() {
            return components;
        }

        public void setComponents(List<Component> components) {
            this.components = components;
        }

        public List<RefComponent> getRefComponents() {
            return refComponents;
        }

        public void setRefComponents(List<RefComponent> refComponents) {
            this.refComponents = refComponents;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        @Override
        public String toString() {
            return "AdditionalComponent{" +
                    "components=" + Arrays.deepToString(components.toArray()) +
                    ", className='" + className + '\'' +
                    '}';
        }
    }

    public static class RefComponent {

        private String ref;

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }
    }

    public static class Arg {
        private String name;
        private String value;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Arg{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

    }

    public static class ConstructorArgs {

        private List<Arg> args;

        public List<Arg> getArgs() {
            return args;
        }

        public void setArgs(List<Arg> args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return "ConstructorArgs{" +
                    "args=" + Arrays.deepToString(args.toArray()) +
                    '}';
        }
    }

    public static class Component {

        private String id;
        private String className;
        private ConstructorArgs constructorArgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public ConstructorArgs getConstructorArgs() {
            return constructorArgs;
        }

        public void setConstructorArgs(ConstructorArgs constructorArgs) {
            this.constructorArgs = constructorArgs;
        }

        @Override
        public String toString() {
            return "Component{" +
                    "id='" + id + '\'' +
                    ", classname='" + className + '\'' +
                    ", constructorArgs=" + constructorArgs +
                    '}';
        }
    }

    private List<Component> components;
    private List<AdditionalComponent> additionalComponents;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<AdditionalComponent> getAdditionalComponents() {
        return additionalComponents;
    }

    public void setAdditionalComponents(List<AdditionalComponent> additionalComponents) {
        this.additionalComponents = additionalComponents;
    }

    @Override
    public String toString() {
        return "Module{" +
                "components=" + Arrays.deepToString(components.toArray()) +
                ",\nadditionalComponents=" + Arrays.deepToString(additionalComponents.toArray()) +
                '}';
    }
}
