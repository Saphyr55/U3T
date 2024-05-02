package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ComponentWrapper;
import utours.ultimate.core.ModuleEvaluator;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationModuleEvaluator implements ModuleEvaluator {

    private final Map<String, ComponentWrapper> componentsById = new HashMap<>();
    private final Map<Class<?>, List<ComponentWrapper>> additionalComponents = new HashMap<>();
    private final Map<Class<?>, ComponentWrapper> uniqueComponents = new HashMap<>();

    private final Map<Class<?>, Map<Class<?>, MethodHandle>> factoryMethodHandlesMapped;
    private final Map<Class<?>, MethodHandle> constructors;
    private final ComponentGraph componentGraph;

    public AnnotationModuleEvaluator(ComponentGraph componentGraph,
                                     Map<Class<?>, Map<Class<?>, MethodHandle>> factoryMethodHandlesMapped,
                                     Map<Class<?>, MethodHandle> constructors) {

        this.componentGraph = componentGraph;
        this.factoryMethodHandlesMapped = factoryMethodHandlesMapped;
        this.constructors = constructors;
    }


    @Override
    public void evaluate() {

        componentGraph.getGraph();

    }


    @Override
    public Map<Class<?>, ComponentWrapper> getUniqueComponents() {
        return uniqueComponents;
    }

    @Override
    public Map<Class<?>, List<ComponentWrapper>> getAdditionalComponents() {
        return additionalComponents;
    }

    @Override
    public Map<String, ComponentWrapper> getComponents() {
        return componentsById;
    }

}
