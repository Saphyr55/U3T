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
        var p = predecessor.computeIfAbsent(to, ignored -> new ArrayList<>());
        if (!p.contains(from)) {
            p.add(from);
        }
        var p2 = graph.computeIfAbsent(from, ignored -> new ArrayList<>());
        if (!p2.contains(to)) {
            p2.add(to);
        }
    }

    public void removeDependenciesOf(ComponentId id) {
        removeDependenciesOf(indexOf(id));
    }

    public void removeDependenciesOf(int index) {
        var succeccors = graph.get(index);
        for (int i = 0; i < succeccors.size(); i++) {
            var preds = predecessor.get(succeccors.get(i));
            if (preds.contains(index)) {
                preds.remove((Object) index);
            }
        }
        succeccors.clear();
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

    public List<ComponentId> getTopologicalOrderingComponents() {
        var orderer = new TopologicalOrderingComponentGraph(this);
        orderer.run();
        return orderer.getOrderedComponents();
    }

    public Map<Integer, List<Integer>> predecessors() {
        return predecessor;
    }

    public void printGraph() {

        for (ComponentId component : components) {
            System.out.println(component.clazz().getSimpleName());
            System.out.println("SUCC {");
            for (Integer i : getGraph().get(indexOf(component))) {
                System.out.println("\t" + components.get(i).clazz().getSimpleName());
            }
            System.out.println("}");
            System.out.println("PRED {");
            for (Integer i : predecessors().get(indexOf(component))) {
                System.out.println("\t" + components.get(i).clazz().getSimpleName());
            }
            System.out.println("}");
        }

    }


}
