package utours.ultimate.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Module {

    public static class Value<T> {
        private String id;
        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public static class UniqueComponent {

        private String className;
        private Component component;
        private RefComponent refComponent;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Component getComponent() {
            return component;
        }

        public RefComponent getRefComponent() {
            return refComponent;
        }

        public void setComponent(Component component) {
            this.component = component;
        }

        public void setRefComponent(RefComponent refComponent) {
            this.refComponent = refComponent;
        }


    }

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

    }

    public static class Factory {

        private String methodName;
        private String reference;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
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
        private String derived;
        private ConstructorArgs constructorArgs;
        private Factory factory;

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

        public Factory getFactory() {
            return factory;
        }

        public void setFactory(Factory factory) {
            this.factory = factory;
        }

        public void setDerived(String derived) {
            this.derived = derived;
        }

        public String getDerived() {
            return derived;
        }

    }
    private List<Component> components;
    private List<AdditionalComponent> additionalComponents;
    private List<UniqueComponent> uniqueComponents;
    private Map<Class<?>, List<Value<?>>> values;

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<UniqueComponent> getUniqueComponents() {
        return uniqueComponents;
    }

    public void setUniqueComponents(List<UniqueComponent> uniqueComponents) {
        this.uniqueComponents = uniqueComponents;
    }

    public List<AdditionalComponent> getAdditionalComponents() {
        return additionalComponents;
    }

    public void setAdditionalComponents(List<AdditionalComponent> additionalComponents) {
        this.additionalComponents = additionalComponents;
    }

    public Map<Class<?>, List<Value<?>>> getValues() {
        return values;
    }

    public void setValues(Map<Class<?>, List<Value<?>>> values) {
        this.values = values;
    }

}
