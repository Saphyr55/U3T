package utours.ultimate.core.internal;

import utours.ultimate.core.*;
import utours.ultimate.core.Module;

import java.util.List;
import java.util.Map;

public class ModuleContextImpl implements ModuleContext {

    private final ModuleEvaluatorProvider moduleEvaluatorProvider;
    private final Container container;

    public ModuleContextImpl(ModuleEvaluatorProvider moduleEvaluatorProvider) {
        this.moduleEvaluatorProvider = moduleEvaluatorProvider;
        this.container = new ContainerImpl();
    }

    @Override
    public void load() {
        try {

            ModuleEvaluator evaluator = moduleEvaluatorProvider.provideModuleEvaluator();
            evaluator.evaluate();

            for (Map.Entry<String, ComponentProvider> e : evaluator.getComponents().entrySet()) {
                String s = e.getKey();
                ComponentWrapper v = e.getValue().get();
                container.storeComponent(s, v.getComponent());
            }

            for (ComponentProvider provider : evaluator.getUniqueComponents().values()) {
                ComponentWrapper cw = provider.get();
                container.storeUniqueComponent(cw.getComponentClass(), cw.getComponent());
            }

            for (Map.Entry<Class<?>, List<ComponentProvider>> entry : evaluator.getAdditionalComponents().entrySet()) {
                Class<?> cClass = entry.getKey();
                List<ComponentProvider> providers = entry.getValue();
                providers.forEach(cw -> container.storeAdditionalComponent(cClass, cw.get().getComponent()));
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
