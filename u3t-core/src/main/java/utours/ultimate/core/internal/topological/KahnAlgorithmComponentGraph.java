package utours.ultimate.core.internal.topological;

import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ComponentId;
import utours.ultimate.core.OrderedComponentProvider;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class KahnAlgorithmComponentGraph implements OrderedComponentProvider {

    private final ComponentGraph componentGraph;
    private final List<ComponentId> components;

    public KahnAlgorithmComponentGraph(ComponentGraph componentGraph) {
        this.componentGraph = componentGraph;
        this.components = componentGraph.getComponents();
    }

    public int[] kahnsAlgorithm() {

        int[] degrees = new int[components.size()];

        for (ComponentId component : components) {
            int index = componentGraph.indexOf(component);
            degrees[index] = componentGraph.predecessors().get(index).size();
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int idx = 0; idx < components.size(); idx++) {
            if (degrees[idx] == 0) {
                queue.offer(idx);
            }
        }

        int index = 0;
        int[] order = new int[components.size()];

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order[index++] = current;
            for (Integer to : componentGraph.successors().get(current)) {
                degrees[to] = degrees[to] - 1;
                if (degrees[to] == 0) {
                    queue.offer(to);
                }
            }
        }

        if (index != components.size()) {
            throw new IllegalStateException("Directed acyclic graph is requiered.");
        }

        return order;
    }


    @Override
    public List<ComponentId> getOrderedComponents() {
        return Arrays.stream(kahnsAlgorithm())
                .mapToObj(componentGraph::fromIndex)
                .toList()
                .reversed();
    }
}
