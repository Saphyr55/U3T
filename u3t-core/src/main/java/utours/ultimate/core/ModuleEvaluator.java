package utours.ultimate.core;

import java.util.List;
import java.util.Map;

public interface ModuleEvaluator {

    /**
     * Evaluate the module, fill all mapping.
     */
    void evaluate();

    Map<Class<?>, ComponentProvider> getUniqueComponents();

    Map<Class<?>, List<ComponentProvider>> getAdditionalComponents();

    Map<String, ComponentProvider> getComponents();

}
