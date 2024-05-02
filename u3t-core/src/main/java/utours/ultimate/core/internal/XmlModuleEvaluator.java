package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentProvider;
import utours.ultimate.core.ComponentWrapper;
import utours.ultimate.core.Module;
import utours.ultimate.core.ModuleEvaluator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.*;

public class XmlModuleEvaluator implements ModuleEvaluator {

    private final Map<String, ComponentProvider> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentProvider>> additionalComponents = new HashMap<>();
    private final Map<Class<?>, ComponentProvider> uniqueComponents = new HashMap<>();
    private final Module module;
    private Throwable error = null;

    public XmlModuleEvaluator(Module module) {
        this.module = module;
    }

    public void evaluate(Module module) throws Throwable {

        for (var entry : module.getValues().entrySet()) {
            for (var value : entry.getValue()) {
                evaluate(entry.getKey(), value);
            }
        }

        for (Module.Statement statement : module.getStatements()) {
            try {
                switch (statement) {
                    case Module.AdditionalComponent additionalComponent -> evaluate(additionalComponent);
                    case Module.Group group -> evaluate(group);
                    case Module.UniqueComponent uniqueComponent -> evaluate(uniqueComponent);
                    case Module.Component component -> evaluate(component);
                }
            } catch (Throwable t) {
                error = Optional.ofNullable(error).orElse(new IllegalStateException());
                error.addSuppressed(t);
            }
        }

        if (Optional.ofNullable(error).isPresent()) {
            throw error;
        }

    }

    private void evaluate(Module.Group group) throws Throwable {

        ComponentWrapper cw = new ComponentWrapper();
        Class<?> clazz = Class.forName(group.getClassName());
        String id = group.getId();

        List<Object> list = new ArrayList<>();

        for (Module.ComponentInterface componentInterface : group.getComponentInterfaces()) {
            switch (componentInterface) {
                case Module.Component component -> {

                    String className = component.getClassName();
                    Class<?> componentClazz = Class.forName(className);

                    String derivedClassName = component.getDerived();
                    Class<?> derivedClazz = Class.forName(derivedClassName);

                    if (!componentClazz.isAssignableFrom(derivedClazz)) {
                        throw new IllegalStateException("Derived class " + derivedClassName + " is not a subclass of " + clazz);
                    }

                    if (!clazz.isAssignableFrom(componentClazz)) {
                        throw new IllegalStateException("Component class " + className + " is not a subclass of " + clazz);
                    }

                    Object object = instantiate(derivedClazz, component);
                    list.add(object);
                }
                case Module.RefComponent refComponent -> {
                    ComponentWrapper componentWrapper = getComponentById(refComponent.getRef()).get();
                    list.add(componentWrapper.getComponent());
                }
                case Module.RefGroup refGroup -> {
                    ComponentWrapper componentWrapper = getComponentById(refGroup.getRef()).get();
                    List<Object> objects = componentWrapper.getComponent();
                    list.addAll(objects);
                }
            }
        }

        cw.setComponentClass(clazz);
        cw.setComponent(list);

        componentsById.put(id, new ComponentProvider.Singleton(cw));
    }

    private void evaluate(Module.UniqueComponent uniqueComponent) throws Throwable {

        Class<?> clazz = Class.forName(uniqueComponent.getClassName());

        ComponentProvider cw = switch (uniqueComponent.getComponentInterface()) {
            case Module.Component component -> {
                evaluate(component);
                yield getComponentById(component.getId());
            }
            case Module.RefComponent refComponent -> {
                cw = getComponentById(refComponent.getRef());
                uniqueComponents.put(clazz, cw);
                yield cw;
            }
            case Module.RefGroup ignored -> throw new IllegalStateException("Ref group is not supported by unique component.");
        };

        uniqueComponents.put(clazz, cw);
    }

    public void evaluate(Class<?> clazz, Module.Value<?> value) {
        ComponentWrapper cw = new ComponentWrapper();
        cw.setComponentClass(clazz);
        cw.setComponent(value.getValue());
        componentsById.put(value.getId(), new ComponentProvider.Singleton(cw));
    }

    public void evaluate(Module.AdditionalComponent additionalComponent) throws Throwable {

        Class<?> clazz = Class.forName(additionalComponent.getClassName());

        for (Module.ComponentInterface componentInterface : additionalComponent.getComponentsInterface()) {
            switch (componentInterface) {
                case Module.Component component -> {
                    evaluate(component);
                    var provider = getComponentById(component.getId());
                    additionalComponents.computeIfAbsent(clazz, aClass -> new LinkedList<>()).add(provider);
                }
                case Module.RefComponent refComponent -> {
                    var provider = getComponentById(refComponent.getRef());
                    additionalComponents.computeIfAbsent(clazz, aClass -> new LinkedList<>()).add(provider);
                }
                case Module.RefGroup refGroup -> {
                    var componentWrapper = getComponentById(refGroup.getRef()).get();
                    List<Object> objects = componentWrapper.getComponent();

                    checkIsAllSubClasses(clazz, objects);

                    var providers = objects.stream()
                            .map(ComponentWrapper::fromObject)
                            .map(ComponentProvider.Singleton::new)
                            .toList();

                    additionalComponents.computeIfAbsent(clazz, aClass -> new LinkedList<>()).addAll(providers);
                }
            }
        }

    }

    private void evaluate(Module.Component component) throws Throwable {

        String className = component.getClassName();
        Class<?> clazz = Class.forName(className);

        String derivedClassName = component.getDerived();
        Class<?> derivedClazz = Class.forName(derivedClassName);

        if (!clazz.isAssignableFrom(derivedClazz)) {
            throw new IllegalStateException("Derived class " + derivedClassName + " is not a subclass of " + clazz);
        }

        Object object = instantiate(derivedClazz, component);

        ComponentWrapper componentWrapper = new ComponentWrapper();
        componentWrapper.setComponent(object);
        componentWrapper.setComponentClass(clazz);

        componentsById.put(component.getId(), new ComponentProvider.Singleton(componentWrapper));
    }

    public Object instantiate(Class<?> clazz, Module.Component component) throws Throwable {

        Module.ConstructorArgs constructorArgs = component.getConstructorArgs();
        Module.Factory factory = component.getFactory();

        if (constructorArgs != null) {
            return instantiateWithConstructor(clazz, constructorArgs);
        } else if (factory != null) {
            return instantiateWithFactoryMethod(factory);
        } else {
            throw new IllegalStateException("Must have a constructor or a factory method.");
        }
    }

    private Object instantiateWithFactoryMethod(Module.Factory factory) throws Throwable {

        String methodName = factory.getMethodName();
        ComponentWrapper factoryWrapper = getComponentById(factory.getReference()).get();

        Method method = factoryWrapper.getComponentClass().getDeclaredMethod(methodName);

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh;
        mh = lookup.findVirtual(factoryWrapper.getComponentClass(), methodName, MethodType.methodType(method.getReturnType()));
        mh = mh.bindTo(factoryWrapper.getComponent());

        return mh.invoke();
    }

    private Object instantiateWithConstructor(Class<?> clazz, Module.ConstructorArgs constructorArgs) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findConstructor(clazz, methodType(constructorArgs));

        for (int i = 0; i < constructorArgs.getArgs().size(); i++) {
            Module.Arg mArg = constructorArgs.getArgs().get(i);
            ComponentWrapper componentWrapper = getComponentById(mArg.getValue()).get();
            mh = mh.bindTo(componentWrapper.getComponent());
        }

        return mh.invoke();
    }

    public MethodType methodType(Module.ConstructorArgs constructorArgs) throws Throwable {

        if (constructorArgs.getArgs().isEmpty()) {
            return MethodType.methodType(void.class);
        }

        Class<?>[] classes = new Class<?>[constructorArgs.getArgs().size()];
        for (int i = 0; i < classes.length; i++) {
            Module.Arg arg = constructorArgs.getArgs().get(i);
            classes[i] = Class.forName(arg.getType());
        }

        return MethodType.methodType(void.class, classes);
    }

    public Map<String, ComponentProvider> getComponents() {
        return componentsById;
    }

    public Map<Class<?>, List<ComponentProvider>> getAdditionalComponents() {
        return additionalComponents;
    }

    private void checkIsAllSubClasses(Class<?> clazz, List<Object> objects) throws Throwable {
        for (Object object : objects) {
            List<Throwable> exceptions = new ArrayList<>();
            if (!clazz.isInstance(object)) {
                exceptions.add(new IllegalStateException("Derived class '" + object.getClass() + "' is not a subclass of '" + clazz + "'"));
            }
            if (!exceptions.isEmpty()) {
                throw exceptions.stream().reduce((throwable, throwable2) -> {
                    throwable.addSuppressed(throwable2);
                    return throwable;
                }).get();
            }
        }
    }

    public ComponentProvider getComponentById(String id) {
        return Optional.ofNullable(componentsById.get(id))
                .orElseThrow(() -> new IllegalStateException("No component with id '" + id + "'"));
    }

    @Override
    public void evaluate() {
        try {
            evaluate(module);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Class<?>, ComponentProvider> getUniqueComponents() {
        return uniqueComponents;
    }

}
