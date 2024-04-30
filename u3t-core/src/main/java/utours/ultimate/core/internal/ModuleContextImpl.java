package utours.ultimate.core.internal;

import utours.ultimate.core.*;
import utours.ultimate.core.Module;

import java.util.List;
import java.util.Map;

public class ModuleContextImpl implements ModuleContext {

    private final ModuleProvider moduleProvider;
    private final Container container;

    public ModuleContextImpl(ModuleProvider moduleProvider) {
        this.moduleProvider = moduleProvider;
        this.container = new ContainerImpl();
    }

    @Override
    public void load() {
        try {
            Module module = moduleProvider.provideModule();

            ModuleEvaluator evaluator = new ModuleEvaluator();

            evaluator.evaluate(module);

            for (Map.Entry<String, ComponentWrapper> e : evaluator.getComponents().entrySet()) {
                String s = e.getKey();
                ComponentWrapper v = e.getValue();
                container.storeComponent(s, v.getComponent());
            }

            for (ComponentWrapper cw : evaluator.getUniqueComponents().values()) {
                container.storeUniqueComponent(cw.getComponentClass(), cw.getComponent());
            }

            for (Map.Entry<Class<?>, List<ComponentWrapper>> entry : evaluator.getAdditionalComponents().entrySet()) {
                Class<?> cClass = entry.getKey();
                List<ComponentWrapper> cws = entry.getValue();
                cws.forEach(cw -> container.storeAdditionalComponent(cClass, cw.getComponent()));
            }

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public ContainerReadOnly getContainerReadOnly() {
        return container;
    }

}
