package utours.ultimate.core.internal;

import utours.ultimate.core.*;
import utours.ultimate.core.settings.ClassPathSettingsLoader;
import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.settings.SettingsLoader;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModuleContextImpl implements ModuleContext {

    private final String identifier;
    private final Container container;
    private final ModuleEvaluatorProvider moduleEvaluatorProvider;
    private final Settings settings = new Settings();

    public ModuleContextImpl(ModuleEvaluatorProvider moduleEvaluatorProvider) {
        this(UUID.randomUUID().toString(), moduleEvaluatorProvider);
    }

    public ModuleContextImpl(String identifier,
                             ModuleEvaluatorProvider moduleEvaluatorProvider) {

        this(identifier, moduleEvaluatorProvider, defaultSettingsLoader());
    }

    public ModuleContextImpl(String identifier,
                             ModuleEvaluatorProvider moduleEvaluatorProvider,
                             SettingsLoader settingsLoader) {

        this(identifier, moduleEvaluatorProvider, settingsLoader, defaultContainer());
    }

    private ModuleContextImpl(String identifier,
                              ModuleEvaluatorProvider moduleEvaluatorProvider,
                              SettingsLoader settingsLoader,
                              Container container) {

        this.identifier = identifier;
        this.moduleEvaluatorProvider = moduleEvaluatorProvider;
        this.container = container;

        settingsLoader.load(settings);
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public Settings settings() {
        return settings;
    }

    @Override
    public void load() {
        ModuleEvaluator evaluator = moduleEvaluatorProvider.provideModuleEvaluator();
        load(container, evaluator);
    }

    private void load(Container container, ModuleEvaluator evaluator) {
        try {

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
    public ReadOnlyContainer getContainerReadOnly() {
        return container;
    }

    @Override
    public void mergeModule(String identifier) {
        ModuleContext context = ModuleContextRegistry.getDefault().get(identifier);
        if (context instanceof ModuleContextImpl impl) {
            ModuleEvaluator evaluator = impl.moduleEvaluatorProvider.provideModuleEvaluator();
            load(container, evaluator);
        }
    }

    private static Container defaultContainer() {
        return new ContainerImpl();
    }

    private static SettingsLoader defaultSettingsLoader() {
        return new ClassPathSettingsLoader();
    }

}
