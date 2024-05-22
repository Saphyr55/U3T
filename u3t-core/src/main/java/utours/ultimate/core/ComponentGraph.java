package utours.ultimate.core;

import utours.ultimate.core.internal.topological.KahnAlgorithmComponentGraph;
import utours.ultimate.core.internal.topological.TopologicalOrderingComponentGraph;

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

    public Map<Integer, List<Integer>> successors() {
        return graph;
    }

    public List<ComponentId> getTopologicalOrderingComponents() {
        return getTopologicalOrderingComponents(new KahnAlgorithmComponentGraph(this));
    }

    public List<ComponentId> getTopologicalOrderingComponents(OrderedComponentProvider provider) {
        return provider.getOrderedComponents();
    }

    public Map<Integer, List<Integer>> predecessors() {
        return predecessor;
    }

    public void printGraph() {

        for (int j = 0; j < components.size(); j++) {

            ComponentId component = components.get(j);

            System.out.println(component.identifier());
            System.out.println("SUCC {");

            for (Integer i : successors().get(j)) {
                if (i < 0) continue;
                System.out.println("\t"
                        + components.get(i).identifier()
                        + " Class: " + components.get(i).clazz().getName());
            }

            System.out.println("}");
            System.out.println("PRED {");
            for (Integer i : predecessors().get(j)) {
                if (i < 0) continue;
                System.out.println("\t"
                        + components.get(i).identifier()
                        + " Class: " + components.get(i).clazz().getName());
            }
            System.out.println("}");
        }

    }


}
