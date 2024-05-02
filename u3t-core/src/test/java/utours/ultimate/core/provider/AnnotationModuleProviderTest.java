package utours.ultimate.core.provider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.component.AComponent;
import utours.ultimate.core.component.BComponent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationModuleProviderTest {

    static AnnotationModuleProvider provider;
    static ComponentGraph graph;

    @BeforeAll
    static void beforeAll() {
        provider = new AnnotationModuleProvider("utours.ultimate.core.component");
        provider.provideModule();
        graph = provider.getComponentGraph();
    }

    @Test
    void check_have_dependencies_in_graph() {

        var indexAC = graph.indexOf(AComponent.class);
        var indexBC = graph.indexOf(BComponent.class);

        assertTrue(graph.getGraph().get(indexBC).contains(indexAC));
    }

    @Test
    void check_have_all_components_in_graph() {

        assertEquals(2, graph.getComponents().size());
        assertTrue(graph.getComponents().contains(AComponent.class));
        assertTrue(graph.getComponents().contains(BComponent.class));
    }

}