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
    String identifier();

    /**
     *
     */
    Settings settings();

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
     * @param identifier the identifier of the module to merge.
     */
    void mergeModule(String identifier);

    /**
     * Instantiate a module context.
     *
     * @param identifier the identifier of the module to instanciate must be unique.
     * @param evaluatorProvider the module provider.
     * @return the module context.
     */
    static ModuleContext of(String identifier, ModuleEvaluatorProvider evaluatorProvider) {
        return new ModuleContextImpl(identifier, evaluatorProvider);
    }

    /**
     * Instantiate a module context.
     *
     * @param identifier the identifier of the module to instanciate must be unique.
     * @param evaluatorProvider the module provider.
     * @param settingsLoader the settings loader.
     * @return the module context.
     */
    static ModuleContext of(String identifier, ModuleEvaluatorProvider evaluatorProvider, SettingsLoader settingsLoader) {
        return new ModuleContextImpl(identifier, evaluatorProvider, settingsLoader);
    }

}
