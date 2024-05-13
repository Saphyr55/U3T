package utours.ultimate.core.provider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ModuleEvaluator;
import utours.ultimate.core.component.AComponent;
import utours.ultimate.core.component.BComponent;
import utours.ultimate.core.component.CComponent;
import utours.ultimate.core.component.FactoryComponent;

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
    void check_have_dependencies_in_graph() {

        var indexAC = graph.indexOf(AComponent.class);
        var indexBC = graph.indexOf(BComponent.class);

        var deps = graph.getGraph().get(indexBC);

        assertTrue(deps.contains(indexAC));
    }

    @Test
    void check_have_dependencies_with_factory() {

        var indexAC = graph.indexOf(AComponent.class);
        var indexFactoryC = graph.indexOf(FactoryComponent.class);

        assertTrue(graph.getGraph().get(indexAC).contains(indexFactoryC));
    }

    @Test
    void check_have_all_components_in_graph() {
        assertTrue(graph.getComponents().contains(FactoryComponent.class));
        assertTrue(graph.getComponents().contains(AComponent.class));
        assertTrue(graph.getComponents().contains(BComponent.class));
        assertTrue(graph.getComponents().contains(CComponent.class));
    }

}