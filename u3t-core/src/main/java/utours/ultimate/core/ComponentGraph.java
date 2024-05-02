package utours.ultimate.core;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentGraph {

    private int lastIndex = 0;
    private final Map<Integer, List<Integer>> graph;
    private Map<Class<?>, Integer> components;

    public ComponentGraph() {
        this.components = new HashMap<>();
        this.graph = new HashMap<>();
    }

    public void addComponent(Class<?> component) {
        if (!components.containsKey(component)) {
            this.components.put(component, lastIndex++);
            this.graph.putIfAbsent(indexOf(component), new LinkedList<>());
        }
    }

    public int indexOf(Class<?> component) {
        return components.get(component);
    }

    public void addEdge(Class<?> from, Class<?> to) {
        var idxFrom = indexOf(from);
        var idxTo = indexOf(to);
        addEdge(idxFrom, idxTo);
    }

    public void addEdge(int from, int to) {
        graph.computeIfAbsent(from, k -> new LinkedList<>()).add(to);
    }

    public Map<Class<?>, Integer> getComponents() {
        return components;
    }

    public Map<Integer, List<Integer>> getGraph() {
        return graph;
    }

    public void sort() {
        components = components.entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(graph.get(o1.getValue()).size(), graph.get(o2.getValue()).size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
