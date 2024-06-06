package utours.ultimate.core.internal;

import utours.ultimate.common.JarLoader;
import utours.ultimate.core.*;
import utours.ultimate.core.settings.ClassPathSettingsLoader;
import utours.ultimate.core.settings.ModuleContextSettings;
import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.settings.SettingsLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ModuleContextImpl implements ModuleContext {

    private static final Logger LOGGER = Logger.getLogger(ModuleContextImpl.class.getName());

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

        loadMods();
        loadContainer(container, analyser.evaluator());

        for (ModuleContext moduleContext : ModuleContextRegistry.getDefault().moduleContexts()) {
            System.out.println(moduleContext.getIdentifier());
        }

    }

    private void loadMods() {

        File modFolder = new File("../mod");
        if (!modFolder.exists()) return;

        File[] mods = modFolder.listFiles();

        if (mods == null) return;

        URL[] urls = Arrays.stream(mods)
                .filter(file -> file.getName().endsWith(".jar"))
                .map(JarLoader::urlOfFile)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(URL[]::new);

        try (URLClassLoader classLoader = URLClassLoader.newInstance(urls)) {

            Arrays.stream(classLoader.getURLs()).forEach(System.out::println);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load module context", e);
        }

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
