package utours.ultimate.core.provider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ModuleEvaluator;
import utours.ultimate.core.component.AComponent;
import utours.ultimate.core.component.BComponent;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationModuleEvaluatorProviderTest {

    static AnnotationModuleEvaluatorProvider provider;
    static ComponentGraph graph;
    static ModuleEvaluator evaluator;

    @BeforeAll
    static void beforeAll() {
        provider = new AnnotationModuleEvaluatorProvider("utours.ultimate.core.component");
        evaluator = provider.provideModuleEvaluator();
        evaluator.evaluate();
        graph = provider.getComponentGraph();
    }

    @Test
    void check_have_factory_method() {

        // assertTrue(evaluator.getUniqueComponents().containsKey(AComponent.class));
    }

    @Test
    void check_have_dependencies_in_graph() {

        var indexAC = graph.indexOf(AComponent.class);
        var indexBC = graph.indexOf(BComponent.class);

        assertTrue(graph.getGraph().get(indexBC).contains(indexAC));
    }

    @Test
    void check_have_all_components_in_graph() {

        assertTrue(graph.getComponents().contains(AComponent.class));
        assertTrue(graph.getComponents().contains(BComponent.class));
    }

}