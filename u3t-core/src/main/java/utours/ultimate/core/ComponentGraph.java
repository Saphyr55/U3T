package utours.ultimate.core;

import utours.ultimate.core.internal.TopologicalOrderingComponentGraph;

import java.util.*;

public class ComponentGraph {

    private final Map<Integer, List<Integer>> graph;
    private final Map<Integer, List<Integer>> predecessor;
    private final List<Class<?>> components;

    public ComponentGraph() {
        this.components = new LinkedList<>();
        this.graph = new HashMap<>();
        this.predecessor = new HashMap<>();
    }

    public void addComponent(Class<?> component) {
        if (!components.contains(component)) {
            this.components.add(component);
            this.predecessor.putIfAbsent(indexOf(component), new LinkedList<>());
            this.graph.putIfAbsent(indexOf(component), new LinkedList<>());
        }
    }

    public int indexOf(Class<?> component) {
        return components.indexOf(component);
    }

    public Class<?> fromIndex(int index) {
        return components.get(index);
    }

    public void addDependency(Class<?> from, Class<?> to) {
        var idxFrom = indexOf(from);
        var idxTo = indexOf(to);
        addDependency(idxFrom, idxTo);
    }

    public void addDependency(int from, int to) {
        predecessor.computeIfAbsent(to, k -> new LinkedList<>()).add(from);
        graph.computeIfAbsent(from, k -> new LinkedList<>()).add(to);
    }

    public List<Class<?>> getComponents() {
        return components;
    }

    public Map<Integer, List<Integer>> getGraph() {
        return graph;
    }

    public List<Class<?>> getSortedComponents(Iterator<Class<?>> iterator) {
        List<Class<?>> sortedComponents = new LinkedList<>();
        while (iterator.hasNext()) {
            var value = iterator.next();
            if (!sortedComponents.contains(value)) {
                sortedComponents.add(value);
            }
        }
        return sortedComponents;
    }

    public List<Class<?>> getSortedComponents() {
        return getSortedComponents(new TopologicalOrderingComponentGraph(this)).reversed();
    }

    public Map<Integer, List<Integer>> getPredecessors() {
        return predecessor;
    }


}
