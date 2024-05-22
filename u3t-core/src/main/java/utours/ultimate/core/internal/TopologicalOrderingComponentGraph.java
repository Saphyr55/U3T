package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ComponentId;
import utours.ultimate.core.OrderedComponentProvider;

import java.util.*;

public class TopologicalOrderingComponentGraph implements OrderedComponentProvider {
    
    private final List<ComponentId> finalOrderedComponents;
    private final List<List<ComponentId>> orderedComponents;
    private final Set<Integer> markedComponents;
    private final List<ComponentId> components;
    private final Stack<ComponentId> componentsStack;
    private final ComponentGraph componentGraph;

    public TopologicalOrderingComponentGraph(ComponentGraph componentGraph) {

        this.components = componentGraph.getComponents();
        this.componentGraph = componentGraph;
        this.markedComponents = new HashSet<>();
        this.componentsStack = new Stack<>();
        this.orderedComponents = new ArrayList<>();
        this.orderedComponents.add(new ArrayList<>());
        if (!components.isEmpty()) {
            this.componentsStack.push(getMinimalPredComponent());
        }
        finalOrderedComponents = new ArrayList<>();
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

    public void run() {
        while (!componentsStack.isEmpty()) {
            append();
        }
        finalOrderedComponents.addAll(List.copyOf(orderedComponents.getLast()));
    }

    public void append() {
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
                    var s = componentGraph.fromIndex(t);
                    componentsStack.push(s);
                }
            }
        }

        if (!orderedComponents.stream().flatMap(Collection::stream).toList().contains(componentId)) {
            orderedComponents.getLast().add(componentId);
        }

        if (componentsStack.isEmpty() && markedComponents.size() != components.size()) {
            componentsStack.push(getMinimalPredComponent());
            var reversedOrderedComponents = List.copyOf(orderedComponents.getLast()).reversed();
            finalOrderedComponents.addAll(reversedOrderedComponents);
            orderedComponents.add(new ArrayList<>());
        }


    }


    @Override
    public List<ComponentId> getOrderedComponents() {
        return finalOrderedComponents;
    }
}
