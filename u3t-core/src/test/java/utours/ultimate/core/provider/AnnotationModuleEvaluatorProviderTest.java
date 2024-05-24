package utours.ultimate.core.provider;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.core.*;
import utours.ultimate.core.component.*;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationModuleEvaluatorProviderTest {

    static AnnotationComponentAnalyser analyser;
    static ComponentEvaluator evaluator;
    static ComponentGraph graph;
    static ModuleContext context;

    @BeforeAll
    static void beforeAll() {
        analyser = new AnnotationComponentAnalyser();
        context = ModuleContext.of(ContextTest.class);
        analyser.addModuleContext(context);
        evaluator = analyser.evaluator();
        evaluator.evaluate();
        graph = analyser.getComponentGraph();
    }

    @Test
    void check_identifier_in_container() {

        ComponentProvider provider = evaluator.getComponents().get("Internal.CComponent");
        assertNotNull(provider);

        ComponentWrapper cw = provider.get();
        assertNotNull(cw);

        Object o = cw.getComponent();
        Class<?> componentClass = cw.getComponentClass();

        assertEquals(CComponent.class, componentClass);
        assertInstanceOf(CComponent.class, o);
    }

    @Test
    void check_have_dependencies_in_graph() {

        var indexAC = graph.indexOf(ComponentId.ofClass(AComponent.class));
        var indexBC = graph.indexOf(ComponentId.ofClass(BComponent.class));

        assertEquals(-1, indexAC);
        assertNotEquals(-1, indexBC);
    }

    @Test
    void check_additional_component_exist_in_graph() {
        var indexIAddC = graph.indexOf(ComponentId.ofClass(IAddComponent.class));
        assertNotEquals(-1, indexIAddC);

        var indexAAdd = graph.indexOf(ComponentId.ofClass(AAddComponent.class));
        assertNotEquals(-1, indexAAdd);

        var indexBAdd = graph.indexOf(ComponentId.ofClass(BAddComponent.class));
        assertNotEquals(-1, indexBAdd);
    }

    @Test
    void check_additional_component_in_evaluating() {

        var componentsProvider = evaluator.getAdditionalComponents().get(IAddComponent.class);

        var aAddProvider = evaluator.getComponents().get(AAddComponent.class.getName());
        var bAddProvider = evaluator.getComponents().get(BAddComponent.class.getName());

        assertTrue(componentsProvider.contains(aAddProvider));
        assertTrue(componentsProvider.contains(bAddProvider));
    }

    @Test
    void check_have_dependencies_additional_component() {

        var indexIAddC = graph.indexOf(ComponentId.ofClass(IAddComponent.class));
        var indexAAdd = graph.indexOf(ComponentId.ofClass(AAddComponent.class));
        var indexBAdd = graph.indexOf(ComponentId.ofClass(BAddComponent.class));

        var deps = graph.successors().get(indexIAddC);

        assertTrue(deps.contains(indexAAdd));
        assertTrue(deps.contains(indexBAdd));
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
        assertNotNull(dComponent);
        assertEquals("Hello World", dComponent.giveHelloWorldString());
    }

    @Test
    void check_have_dependencies_with_factory() {

        var indexAC = graph.indexOf(ComponentId.ofClass(AComponent.class));
        var indexFactoryC = graph.indexOf(ComponentId.ofClass(IFactoryComponent.class));

        assertEquals(-1, indexAC);
        assertNotEquals(-1, indexFactoryC);
    }

    @Test
    void check_have_every_components_in_graph() {
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(FactoryComponent.class)));
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(IFactoryComponent.class)));
        assertTrue(graph.getComponents().contains(ComponentId.ofClass(BComponent.class)));
        assertTrue(graph.getComponents().contains(ComponentId.of(CComponent.class, "Internal.CComponent")));
    }

}