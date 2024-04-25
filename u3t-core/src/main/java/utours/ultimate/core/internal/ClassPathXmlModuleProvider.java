package utours.ultimate.core.internal;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utours.ultimate.core.ClassPathResource;
import utours.ultimate.core.Module;
import utours.ultimate.core.ModuleProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.List;
import java.util.stream.IntStream;

public class ClassPathXmlModuleProvider implements ModuleProvider {

    public static final String MODULE_XML_FILENAME = "module.xml";

    public ClassPathXmlModuleProvider() { }

    private Module interpretXmlModule() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(ClassPathResource.getResourceAsStream(MODULE_XML_FILENAME));

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        XPathExpression componentsExpr = xpath.compile("/module/component");
        XPathExpression additionalComponentsExpr = xpath.compile("/module/add-component");

        NodeList componentNodes = (NodeList) componentsExpr.evaluate(document, XPathConstants.NODESET);
        NodeList additionalComponentNodes = (NodeList) additionalComponentsExpr.evaluate(document, XPathConstants.NODESET);

        List<Module.Component> components = processComponents(componentNodes);
        List<Module.AdditionalComponent> additionalComponents = processAdditionalComponents(additionalComponentNodes);

        Module module = new Module();
        module.setComponents(components);
        module.setAdditionalComponents(additionalComponents);

        return module;
    }

    private List<Module.Component> processComponents(NodeList componentNodes) {
        return IntStream.range(0, componentNodes.getLength())
                .mapToObj(componentNodes::item)
                .map(this::processComponent).toList();
    }

    private Module.ConstructorArgs processConstructorArgs(Node node) {

        List<Module.Arg> args = IntStream.range(0, node.getChildNodes().getLength())
                .mapToObj(i -> node.getChildNodes().item(i))
                .filter(this::isConstructorArgs)
                .findFirst()
                .map(Node::getChildNodes)
                .map(nodes -> IntStream.range(0, nodes.getLength())
                        .mapToObj(nodes::item)
                        .filter(this::isArg)
                        .map(this::processArg).toList())
                .orElse(List.of());

        Module.ConstructorArgs constructorArgs = new Module.ConstructorArgs();
        constructorArgs.setArgs(args);

        return constructorArgs;
    }

    private Module.Arg processArg(Node item) {
        String name = item.getAttributes().getNamedItem("name").getNodeValue();
        String type = item.getAttributes().getNamedItem("type").getNodeValue();
        String value = item.getAttributes().getNamedItem("value").getNodeValue();

        Module.Arg arg = new Module.Arg();
        arg.setName(name);
        arg.setType(type);
        arg.setValue(value);

        return arg;
    }

    private Module.Component processComponent(Node node) {
        String classname = node.getAttributes().getNamedItem("class").getNodeValue();
        String id = node.getAttributes().getNamedItem("id").getNodeValue();

        Module.ConstructorArgs constructorArgs = processConstructorArgs(node);

        Module.Component component = new Module.Component();
        component.setClassName(classname);
        component.setId(id);
        component.setConstructorArgs(constructorArgs);

        return component;
    }

    private List<Module.AdditionalComponent> processAdditionalComponents(NodeList nodes) {
        return IntStream.range(0, nodes.getLength())
                .mapToObj(nodes::item)
                .filter(this::isAddComponent)
                .map(this::processAdditionalComponent)
                .toList();
    }

    private Module.AdditionalComponent processAdditionalComponent(Node node) {

        if (!isAddComponent(node))
            throw new IllegalStateException("Not an additional component.");

        Module.AdditionalComponent additionalComponent = new Module.AdditionalComponent();

        String classname = node.getAttributes().getNamedItem("class").getNodeValue();

        List<Module.Component> components =
                IntStream.range(0, node.getChildNodes().getLength())
                    .mapToObj(node.getChildNodes()::item)
                    .filter(this::isComponent)
                    .map(this::processComponent)
                    .toList();

        List<Module.RefComponent> refComponents =
                IntStream.range(0, node.getChildNodes().getLength())
                    .mapToObj(node.getChildNodes()::item)
                    .filter(this::isRefComponent)
                    .map(this::processRefComponent)
                    .toList();

        additionalComponent.setClassName(classname);
        additionalComponent.setComponents(components);
        additionalComponent.setRefComponents(refComponents);

        return additionalComponent;
    }

    private Module.RefComponent processRefComponent(Node item) {

        String ref = item.getAttributes().getNamedItem("ref").getNodeValue();

        Module.RefComponent refComponent = new Module.RefComponent();
        refComponent.setRef(ref);

        return refComponent;
    }

    @Override
    public Module provideModule() {
        try {
            return interpretXmlModule();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isRefComponent(Node item) {
        return item.getNodeName().equals("ref-component");
    }

    private boolean isComponent(Node item) {
        return item.getNodeName().equals("component");
    }

    private boolean isAddComponent(Node item) {
        return item.getNodeName().equals("add-component");
    }

    private boolean isArg(Node item) {
        return item.getNodeName().equals("arg");
    }

    private boolean isConstructorArgs(Node item) {
        return item.getNodeName().equals("constructor-args");
    }

}
