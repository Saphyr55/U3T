package utours.ultimate.core;

import utours.ultimate.core.internal.ContainerImpl;
import utours.ultimate.core.internal.ModuleEvaluator;

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

            evaluator.getComponents().forEach((s, cw) -> {
                container.storeComponent(s, cw.getComponent());
                container.storeUniqueComponent(cw.getComponentClass(), cw.getComponent());
            });

            evaluator.getAdditionalComponents().forEach((cClass, cws) -> {
                cws.forEach(cw -> container.storeAdditionalComponent(cClass, cw.getComponent()));
            });

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
