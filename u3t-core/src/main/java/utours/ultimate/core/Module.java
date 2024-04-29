package utours.ultimate.core;

import java.util.*;

public class Module {


    public sealed interface Statement { }

    public sealed interface ComponentInterface { }

    public static final class Value<T> {
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

    public static final class Group implements Statement {
        private String className;
        private String id;
        private List<ComponentInterface> componentInterfaces = new ArrayList<>();

        public String getClassName() {
            return className;
        }

        public List<ComponentInterface> getComponentInterfaces() {
            return componentInterfaces;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setComponentInterfaces(List<ComponentInterface> componentInterfaces) {
            this.componentInterfaces = componentInterfaces;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static final class UniqueComponent implements Statement {

        private String className;
        private ComponentInterface componentInterface;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public ComponentInterface getComponentInterface() {
            return componentInterface;
        }

        public void setComponentInterface(ComponentInterface componentInterface) {
            this.componentInterface = componentInterface;
        }

    }

    public static final class AdditionalComponent implements Statement {
        private List<ComponentInterface> componentsInterface;
        private String className;

        public void setComponentsInterface(List<ComponentInterface> componentsInterface) {
            this.componentsInterface = componentsInterface;
        }

        public List<ComponentInterface> getComponentsInterface() {
            return componentsInterface;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

    }

    public static final class RefComponent implements ComponentInterface {

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

    public static final class Factory {

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

    public static final class ConstructorArgs {

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

    public static final class Component implements ComponentInterface, Statement {

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

    private Map<Class<?>, List<Value<?>>> values;
    private List<Statement> statements;

    public List<Statement> getStatements() {
        return statements;
    }

    public Map<Class<?>, List<Value<?>>> getValues() {
        return values;
    }

    public void setValues(Map<Class<?>, List<Value<?>>> values) {
        this.values = values;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}
