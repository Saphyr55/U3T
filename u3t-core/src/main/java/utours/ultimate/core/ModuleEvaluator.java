package utours.ultimate.core;

import java.util.List;
import java.util.Map;

public interface ModuleEvaluator {

    void evaluate();

    Map<Class<?>, ComponentWrapper> getUniqueComponents();

    Map<Class<?>, List<ComponentWrapper>> getAdditionalComponents();

    Map<String, ComponentWrapper> getComponents();

}
