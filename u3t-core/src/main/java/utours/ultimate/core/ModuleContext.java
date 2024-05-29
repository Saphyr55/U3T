package utours.ultimate.core;

import utours.ultimate.core.internal.ModuleContextImpl;
import utours.ultimate.core.settings.Settings;
import utours.ultimate.core.settings.SettingsLoader;

/**
 *
 */
public interface ModuleContext {

    /**
     * Return the current module context id.
     *
     * @return The current module context id.
     */
    String getIdentifier();

    /**
     *
     */
    Settings getSettings();

    /**
     * Load all components.
     */
    void load();

    /**
     * Give the container that contains every component loaded.
     *
     * @return the container.
     */
    Container getContainer();

    /**
     * Give a read only container that contains every component loaded.
     *
     * @return the read only container.
     */
    ReadOnlyContainer getContainerReadOnly();

    /**
     * Can merge the current module context instance with the module context identifier.
     *
     * @param context the module context to merge.
     */
    void mergeModule(ModuleContext context);

    /**
     * Return the current context class.
     *
     * @return the context class.
     */
    Class<?> getContextClass();

    /**
     * Instantiate a module context.
     *
     * @return the module context.
     */
    static ModuleContext of(Class<?> contextClass) {
        return new ModuleContextImpl(contextClass);
    }

    /**
     * Instantiate a module context.
     *
     * @param settingsLoader the settings loader.
     * @return the module context.
     */
    static ModuleContext of(Class<?> contextClass, SettingsLoader settingsLoader) {
        return new ModuleContextImpl(contextClass, settingsLoader);
    }

}
