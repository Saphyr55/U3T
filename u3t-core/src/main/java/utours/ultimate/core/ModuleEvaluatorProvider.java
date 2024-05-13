package utours.ultimate.core;

public interface ModuleEvaluatorProvider {

    /**
     * Factory method, provide a module.
     *
     * @return a module.
     */
    ModuleEvaluator provideModuleEvaluator();

}
