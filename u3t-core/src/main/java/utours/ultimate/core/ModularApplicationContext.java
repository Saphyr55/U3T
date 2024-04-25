package utours.ultimate.core;

import utours.ultimate.core.internal.ContainerImpl;
import utours.ultimate.core.internal.ModuleEvaluator;

import java.util.Map;

public class ModularApplicationContext implements ApplicationContext {

    private final ModuleProvider moduleProvider;
    private final Container container;

    public ModularApplicationContext(ModuleProvider moduleProvider) {
        this.moduleProvider = moduleProvider;
        this.container = new ContainerImpl(null);
    }

    @Override
    public ContainerReadOnly getContainerReadOnly() {

        try {
            Module module = moduleProvider.provideModule();

            ModuleEvaluator evaluator = new ModuleEvaluator();

            evaluator.evaluate(module);
            for (Map.Entry<String, ComponentWrapper> el : evaluator.getComponents().entrySet()) {
                ComponentWrapper cw = el.getValue();
                container.storeComponent(cw.getComponentClass(), cw.getComponent());
            }

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return container;
    }

}
