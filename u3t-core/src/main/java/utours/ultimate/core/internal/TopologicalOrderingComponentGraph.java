package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TopologicalOrderingComponentGraph implements Iterator<Class<?>> {

    private final List<Integer> markedComponents;
    private final List<Class<?>> components;
    private final Stack<Class<?>> componentsStack;
    private final ComponentGraph componentGraph;

    public TopologicalOrderingComponentGraph(ComponentGraph componentGraph) {
        this.components = componentGraph.getComponents();
        this.componentGraph = componentGraph;
        this.markedComponents = new ArrayList<>();
        this.componentsStack = new Stack<>();
        if (!components.isEmpty()) {
            this.componentsStack.push(getMinimalPredClass());
        }
    }

    private Class<?> getMinimalPredClass() {

        int lastMin = Integer.MAX_VALUE;
        int lastIdx = -1;

        for (var pred : componentGraph.getPredecessors().entrySet()) {
            if (pred.getValue().size() < lastMin) {
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
    public Class<?> next() {

        var clazz = componentsStack.pop();
        var s = componentGraph.indexOf(clazz);

        if (!markedComponents.contains(s)) {
            markedComponents.add(s);
            for (Integer t : componentGraph.getGraph().get(s)) {
                if (!markedComponents.contains(t)) {
                    componentsStack.push(componentGraph.fromIndex(t));
                }
            }
        }

        return clazz;
    }

}
