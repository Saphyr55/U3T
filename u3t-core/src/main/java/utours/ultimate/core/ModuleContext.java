package utours.ultimate.core;

/**
 *
 */
public interface ModuleContext {

    /**
     * Load all components.
     */
    void load();

    /**
     * Give the container that contains every component loaded.
     *
     * @return container.
     */
    Container getContainer();

    /**
     * Give a read only container that contains every component loaded.
     *
     * @return read only container.
     */
    ContainerReadOnly getContainerReadOnly();

    /**
     * Instantiate the module context implementation.
     *
     * @param moduleProvider module provider.
     * @return module context.
     */
    static ModuleContext newInstance(ModuleProvider moduleProvider) {
        return new ModuleContextImpl(moduleProvider);
    }

    /**
     * Instantiate the module context implementation and load the context.
     *
     * @param moduleProvider module provider.
     * @return module context.
     */
    static ModuleContext newInstanceAndLoad(ModuleProvider moduleProvider) {
        ModuleContext moduleContext = newInstance(moduleProvider);
        moduleContext.load();
        return moduleContext;
    }

}
