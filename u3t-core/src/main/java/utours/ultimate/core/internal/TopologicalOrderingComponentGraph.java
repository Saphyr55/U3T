package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ComponentId;

import java.util.*;

public class TopologicalOrderingComponentGraph implements Iterator<ComponentId> {

    private final Set<Integer> markedComponents;
    private final List<ComponentId> components;
    private final Stack<ComponentId> componentsStack;
    private final ComponentGraph componentGraph;

    public TopologicalOrderingComponentGraph(ComponentGraph componentGraph) {
        this.components = componentGraph.getComponents();
        this.componentGraph = componentGraph;
        this.markedComponents = new HashSet<>();
        this.componentsStack = new Stack<>();
        if (!components.isEmpty()) {
            this.componentsStack.push(getMinimalPredComponent());
        }
    }

    private ComponentId getMinimalPredComponent() {

        int lastMin = Integer.MAX_VALUE;
        int lastIdx = -1;

        for (var pred : componentGraph.predecessors().entrySet()) {
            if (pred.getValue().size() < lastMin && !markedComponents.contains(pred.getKey())) {
                lastMin = pred.getValue().size();
                lastIdx = pred.getKey();
            }
        }

        if (lastIdx == -1) {
            throw new IllegalStateException("Empty graph.");
        }

        return componentGraph.fromIndex(lastIdx);
    }

    @Override
    public boolean hasNext() {
        return !componentsStack.isEmpty();
    }

    @Override
    public ComponentId next() {

        ComponentId componentId = componentsStack.pop();
        int index = componentGraph.indexOf(componentId);

        if (index == -1) {
            throw new IllegalStateException("The class '" + componentId + "' was not found in dependency graph.");
        }

        if (!markedComponents.contains(index)) {
            markedComponents.add(index);
            List<Integer> successors = componentGraph.getGraph().get(index);
            for (Integer t : successors) {
                if (!markedComponents.contains(t)) {
                    componentsStack.push(componentGraph.fromIndex(t));
                }
            }
        }

        if (componentsStack.isEmpty() && markedComponents.size() != components.size()) {
            componentsStack.push(getMinimalPredComponent());
        }

        return componentId;
    }

}
