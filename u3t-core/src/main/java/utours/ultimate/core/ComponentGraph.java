package utours.ultimate.core;

import java.util.*;

public class ComponentGraph {

    private final List<Class<?>> components;
    private final Map<Integer, List<Integer>> graph;

    public ComponentGraph() {
        this.components = new LinkedList<>();
        this.graph = new HashMap<>();
    }

    public void addComponent(Class<?> component) {
        if (!components.contains(component)) {
            this.components.add(component);
        }
    }

    public int indexOf(Class<?> component) {
        return components.indexOf(component);
    }

    public void addEdge(Class<?> from, Class<?> to) {
        var idxFrom = indexOf(from);
        var idxTo = indexOf(to);
        addEdge(idxFrom, idxTo);
    }

    public void addEdge(int from, int to) {
        graph.computeIfAbsent(from, k -> new LinkedList<>()).add(to);
    }

    public List<Class<?>> getComponents() {
        return components;
    }

    public Map<Integer, List<Integer>> getGraph() {
        return graph;
    }


}
