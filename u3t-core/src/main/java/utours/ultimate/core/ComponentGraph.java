package utours.ultimate.core;

import java.util.*;

public class ComponentGraph {

    private final Map<ComponentWrapper, List<ComponentWrapper>> graph;

    public ComponentGraph() {
        graph = new HashMap<>();
    }

    public void addComponent(ComponentWrapper component) {
        graph.computeIfAbsent(component, k -> new LinkedList<>());
    }

    public void addComponent(ComponentWrapper component, List<ComponentWrapper> dependencies) {
        graph.put(component, dependencies);
    }

    public Map<ComponentWrapper, List<ComponentWrapper>> getGraph() {
        return graph;
    }

}
