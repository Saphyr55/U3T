package utours.ultimate.core;

public interface ComponentAnalyser {

    /**
     *
     * @param moduleContext
     */
    void addModuleContext(ModuleContext moduleContext);

    /**
     * Factory method, provide a module.
     *
     * @return a module.
     */
    ComponentEvaluator evaluator();

}
