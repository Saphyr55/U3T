package utours.ultimate.core.provider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.core.ComponentGraph;
import utours.ultimate.core.ComponentId;
import utours.ultimate.core.ModuleEvaluator;
import utours.ultimate.core.component.*;

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

        var indexAC = graph.indexOf(ComponentId.ofClass(AComponent.class));
        var indexBC = graph.indexOf(ComponentId.ofClass(BComponent.class));

        assertNotEquals(-1, indexAC);
        assertNotEquals(-1, indexBC);

        var deps = graph.getGraph().get(indexBC);

        assertTrue(deps.contains(indexAC));
    }

    @Test
    void check_can_access_to_an_interface() {

        var cwProvider = evaluator.getComponents().get(IDComponent.class.getName());

        assertNotNull(cwProvider);

        var cw = cwProvider.get();

        assertNotNull(cw);
        assertNotNull(cw.getComponent());

        assertInstanceOf(IDComponent.class, cw.getComponent());
        assertInstanceOf(DComponent.class, cw.getComponent());

        IDComponent dComponent = cw.getComponent();

        assertEquals("Hello World", dComponent.giveHelloWorldString());
    }

    @Test
    void check_have_dependencies_with_factory() {

        var indexAC = graph.indexOf(ComponentId.ofClass(AComponent.class));
        var indexFactoryC = graph.indexOf(ComponentId.ofClass(FactoryComponent.class));

        assertNotEquals(-1, indexAC);
        assertNotEquals(-1, indexFactoryC);

        assertTrue(graph.getGraph().get(indexAC).contains(indexFactoryC));
    }

    @Test
    void check_have_all_components_in_graph() {
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(FactoryComponent.class)));
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(AComponent.class)));
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(BComponent.class)));
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(CComponent.class)));
    }

}