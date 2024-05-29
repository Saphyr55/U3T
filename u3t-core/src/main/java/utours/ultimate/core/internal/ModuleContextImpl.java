package utours.ultimate.core.internal;

import utours.ultimate.core.*;
import utours.ultimate.core.settings.ClassPathSettingsLoader;
import utours.ultimate.core.settings.ModuleContextSettings;
import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.settings.SettingsLoader;

import java.util.*;

public final class ModuleContextImpl implements ModuleContext {

    private final ComponentAnalyser analyser;
    private final ModuleContextSettings settings;
    private final Container container;
    private final Class<?> contextClass;
    private final String identifier;

    public ModuleContextImpl(Class<?> contextClass) {
        this(contextClass, defaultSettingsLoader(contextClass));
    }

    public ModuleContextImpl(Class<?> contextClass, SettingsLoader settingsLoader) {
        this(contextClass, settingsLoader, defaultContainer());
    }

    private ModuleContextImpl(Class<?> contextClass,
                              SettingsLoader settingsLoader,
                              Container container) {

        this.settings = new ModuleContextSettings(settingsLoader);
        this.contextClass = contextClass;
        this.container = container;
        this.identifier = settings.getIdentifier();
        this.analyser = settings.getComponentEvaluator();
        this.analyser.addModuleContext(this);

        ModuleContextRegistry.getDefault().register(this);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Settings getSettings() {
        return settings.getSettings();
    }

    @Override
    public void load() {
        loadContainer(container, analyser.evaluator());
    }

    private static void loadContainer(Container container, ComponentEvaluator evaluator) {
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
    public void mergeModule(ModuleContext context) {
        analyser.addModuleContext(context);
    }

    @Override
    public Class<?> getContextClass() {
        return contextClass;
    }

    private static Container defaultContainer() {
        return new ContainerImpl();
    }

    private static SettingsLoader defaultSettingsLoader(Class<?> contextClass) {
        return new ClassPathSettingsLoader(contextClass);
    }

}
