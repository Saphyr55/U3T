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
        if (components.contains(component)) return;
        this.components.add(component);
        this.predecessor.putIfAbsent(indexOf(component), new ArrayList<>());
        this.graph.putIfAbsent(indexOf(component), new ArrayList<>());
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
        var p = predecessor.computeIfAbsent(to, k -> new ArrayList<>());
        if (!p.contains(from)) {
            p.add(from);
        }

        var p2 = graph.computeIfAbsent(from, k -> new ArrayList<>());
        if (!p2.contains(to)) {
            p2.add(to);
        }
    }

    public void removeDependenciesOf(ComponentId id) {
        removeDependenciesOf(indexOf(id));
    }

    public void removeDependenciesOf(int index) {
        graph.remove(index);
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
            if (!sortedComponents.contains(next)) {
                sortedComponents.add(next);
            }
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

    public void printGraph() {

        for (ComponentId component : components) {
            System.out.println(component.clazz());
            for (Integer i : getGraph().get(indexOf(component))) {
                System.out.println("\t" + components.get(i).clazz());
            }
        }

    }


}
