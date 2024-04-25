package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentWrapper;
import utours.ultimate.core.Module;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

public class ModuleEvaluator {

    private final Map<String, ComponentWrapper> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentWrapper>> additionalComponents = new HashMap<>();

    public void evaluate(Module module) throws Throwable {

        for (Module.Component component : module.getComponents()) {
            evaluate(component);
        }

        for (Module.AdditionalComponent additionalComponent : module.getAdditionalComponents()) {
            evaluate(additionalComponent);
        }

    }

    public void evaluate(Module.AdditionalComponent additionalComponent) throws Throwable {
        Class<?> clazz = Class.forName(additionalComponent.getClassName());
        for (Module.Component component : additionalComponent.getComponents()) {
            evaluate(component);
            ComponentWrapper componentWrapper = componentsById.get(component.getId());
            additionalComponents
                    .computeIfAbsent(clazz, aClass -> new LinkedList<>())
                    .add(componentWrapper);
        }
        for (Module.RefComponent refComponent : additionalComponent.getRefComponents()) {
            ComponentWrapper componentWrapper = componentsById.get(refComponent.getRef());
            additionalComponents
                    .computeIfAbsent(clazz, aClass -> new LinkedList<>())
                    .add(componentWrapper);
        }
    }

    public void evaluate(Module.Component component) throws Throwable {

        String className = component.getClassName();
        Class<?> clazz = Class.forName(className);

        Object object = instantiate(clazz, component);

        ComponentWrapper componentWrapper = new ComponentWrapper();
        componentWrapper.setComponent(object);
        componentWrapper.setComponentClass(clazz);

        componentsById.put(component.getId(), componentWrapper);
    }

    public Object instantiate(Class<?> aClass, Module.Component component) throws Throwable {

        Module.ConstructorArgs constructorArgs = component.getConstructorArgs();

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findConstructor(aClass, methodType(constructorArgs));

        for (int i = 0; i < constructorArgs.getArgs().size(); i++) {
            Module.Arg mArg = constructorArgs.getArgs().get(i);
            ComponentWrapper componentWrapper = componentsById.get(mArg.getValue());
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

    public Map<String, ComponentWrapper> getComponents() {
        return componentsById;
    }

    public Map<Class<?>, List<ComponentWrapper>> getAdditionalComponents() {
        return additionalComponents;
    }


}
