package utours.ultimate.core;

import utours.ultimate.core.internal.ModuleContextImpl;

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
    ReadOnlyContainer getContainerReadOnly();

    /**
     * Instantiate the module context implementation.
     *
     * @param moduleEvaluatorProvider module provider.
     * @return module context.
     */
    static ModuleContext create(ModuleEvaluatorProvider moduleEvaluatorProvider) {
        return new ModuleContextImpl(moduleEvaluatorProvider);
    }

    /**
     * Instantiate the module context implementation and load the context.
     *
     * @param moduleEvaluatorProvider module provider.
     * @return module context.
     */
    static ModuleContext createAndLoad(ModuleEvaluatorProvider moduleEvaluatorProvider) {
        ModuleContext moduleContext = create(moduleEvaluatorProvider);
        moduleContext.load();
        return moduleContext;
    }

}
