package utours.ultimate.core;

import utours.ultimate.core.internal.TopologicalOrderingComponentGraph;

import java.util.*;

public class ComponentGraph {

    private final Map<Integer, List<Integer>> graph;
    private final Map<Integer, List<Integer>> predecessor;
    private final List<ComponentId> components;

    public ComponentGraph() {
        this.components = new LinkedList<>();
        this.graph = new HashMap<>();
        this.predecessor = new HashMap<>();
    }

    public void addComponent(ComponentId component) {
        if (!components.contains(component)) {
            this.components.add(component);
            this.predecessor.putIfAbsent(indexOf(component), new LinkedList<>());
            this.graph.putIfAbsent(indexOf(component), new LinkedList<>());
        }
    }

    public int indexOf(ComponentId component) {
        return components.indexOf(component);
    }

    public ComponentId fromIndex(int index) {
        return components.get(index);
    }

    public void addDependency(ComponentId from, ComponentId to) {
        var idxFrom = indexOf(from);
        var idxTo = indexOf(to);
        addDependency(idxFrom, idxTo);
    }

    public void addDependency(int from, int to) {
        predecessor.computeIfAbsent(to, k -> new LinkedList<>()).add(from);
        graph.computeIfAbsent(from, k -> new LinkedList<>()).add(to);
    }

    public boolean hasNode(ComponentId node) {
        return components.contains(node);
    }

    public boolean hasNode(int node) {
        return graph.containsKey(node);
    }

    public List<ComponentId> getComponents() {
        return components;
    }

    public Map<Integer, List<Integer>> getGraph() {
        return graph;
    }

    public List<ComponentId> getTopologicalOrderingComponents(Iterator<ComponentId> iterator) {
        List<ComponentId> sortedComponents = new ArrayList<>();
        while (iterator.hasNext()) {
            var next = iterator.next();
            if (!sortedComponents.contains(next))
                sortedComponents.add(next);
        }
        return sortedComponents;
    }

    public List<ComponentId> getTopologicalOrderingComponents() {
        Iterator<ComponentId> iterator = new TopologicalOrderingComponentGraph(this);
        return getTopologicalOrderingComponents(iterator).reversed();
    }

    public Map<Integer, List<Integer>> predecessors() {
        return predecessor;
    }


}
