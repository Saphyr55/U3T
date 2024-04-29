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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
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
        XPathExpression uniqueComponentsExpr = xpath.compile("/module/unique-component");
        XPathExpression additionalComponentsExpr = xpath.compile("/module/add-component");
        XPathExpression stringExpr = xpath.compile("/module/string");
        XPathExpression integerExpr = xpath.compile("/module/integer");
        XPathExpression booleanExpr = xpath.compile("/module/boolean");
        XPathExpression floatExpr = xpath.compile("/module/float");
        XPathExpression doubleExpr = xpath.compile("/module/double");
        XPathExpression longExpr = xpath.compile("/module/long");
        XPathExpression byteExpr = xpath.compile("/module/byte");

        NodeList componentNodes = (NodeList) componentsExpr.evaluate(document, XPathConstants.NODESET);
        NodeList uniqueComponentsNodes = (NodeList) uniqueComponentsExpr.evaluate(document, XPathConstants.NODESET);
        NodeList additionalComponentNodes = (NodeList) additionalComponentsExpr.evaluate(document, XPathConstants.NODESET);
        NodeList stringNodes = (NodeList) stringExpr.evaluate(document, XPathConstants.NODESET);
        NodeList integerNodes = (NodeList) integerExpr.evaluate(document, XPathConstants.NODESET);
        NodeList booleanNodes = (NodeList) booleanExpr.evaluate(document, XPathConstants.NODESET);
        NodeList floatNodes = (NodeList) floatExpr.evaluate(document, XPathConstants.NODESET);
        NodeList doubleNodes = (NodeList) doubleExpr.evaluate(document, XPathConstants.NODESET);
        NodeList longNodes = (NodeList) longExpr.evaluate(document, XPathConstants.NODESET);
        NodeList byteNodes = (NodeList) byteExpr.evaluate(document, XPathConstants.NODESET);

        List<Module.Component> components = processComponents(componentNodes);
        List<Module.AdditionalComponent> additionalComponents = processAdditionalComponents(additionalComponentNodes);
        List<Module.UniqueComponent> uniqueComponents = processUniqueComponents(uniqueComponentsNodes);

        Module module = new Module();
        module.setComponents(components);
        module.setAdditionalComponents(additionalComponents);
        module.setUniqueComponents(uniqueComponents);
        module.setValues(new HashMap<>());
        module.getValues().put(Boolean.class, List.copyOf(processBoolean(booleanNodes)  ));
        module.getValues().put(Integer.class, List.copyOf(processIntegers(integerNodes) ));
        module.getValues().put(Double.class,  List.copyOf(processDoubles(doubleNodes)   ));
        module.getValues().put(Float.class,   List.copyOf(processFloats(floatNodes)     ));
        module.getValues().put(Long.class,    List.copyOf(processLongs(longNodes)       ));
        module.getValues().put(Byte.class,    List.copyOf(processBytes(byteNodes)       ));
        module.getValues().put(String.class,  List.copyOf(processStrings(stringNodes)   ));

        return module;
    }

    private List<Module.UniqueComponent> processUniqueComponents(NodeList nodes) {
        return IntStream.range(0, nodes.getLength())
                .mapToObj(nodes::item)
                .map(this::processUniqueComponent)
                .toList();
    }

    private Module.UniqueComponent processUniqueComponent(Node node) {

        String className = getAttributeValue(node, "class", () -> new IllegalStateException("Must to precise the class."));

        Module.UniqueComponent uniqueComponent = new Module.UniqueComponent();
        uniqueComponent.setClassName(className);

        Node item = IntStream.range(0, node.getChildNodes().getLength())
                .mapToObj(i -> node.getChildNodes().item(i))
                .filter(i -> isRefComponent(i) || isComponent(i))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        if (isComponent(item)) {
            Module.Component component = processComponent(item);
            uniqueComponent.setComponent(component);
        } else if (isRefComponent(item)) {
            Module.RefComponent refComponent = processRefComponent(item);
            uniqueComponent.setRefComponent(refComponent);
        }

        return uniqueComponent;
    }

    private List<Module.Component> processComponents(NodeList componentNodes) {
        return IntStream.range(0, componentNodes.getLength())
                .mapToObj(componentNodes::item)
                .map(this::processComponent)
                .toList();
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
        String name = getAttributeValue(item, "name", "");
        String type = getAttributeValue(item, "type", () -> new IllegalStateException("Must to precise the argument type."));
        String value = getAttributeValue(item, "value", () -> new IllegalStateException("Must to precise the value."));

        Module.Arg arg = new Module.Arg();
        arg.setName(name);
        arg.setType(type);
        arg.setValue(value);

        return arg;
    }

    private Module.Component processComponent(Node node) {

        String id = getAttributeValue(node, "id", () -> new IllegalStateException("Must to precise the id."));
        String classname = getAttributeValue(node, "class", () -> new IllegalStateException("Must to precise the class."));
        String derived = getAttributeValue(node, "derived", classname);
        if  (derived.isEmpty()) derived = classname;

        Optional<Node> itemOpt = IntStream.range(0, node.getChildNodes().getLength())
                .mapToObj(i -> node.getChildNodes().item(i))
                .filter(i -> isConstructorArgs(i) || isFactory(i))
                .findFirst();

        Module.Component component = new Module.Component();
        component.setClassName(classname);
        component.setId(id);
        component.setDerived(derived);

        if (itemOpt.isEmpty() || isConstructorArgs(itemOpt.get())) {
            Module.ConstructorArgs constructorArgs = processConstructorArgs(node);
            component.setConstructorArgs(constructorArgs);
        } else if (isFactory(itemOpt.get())) {
            Module.Factory factory = proccessFactory(itemOpt.get());
            component.setFactory(factory);
        } else {
            throw new IllegalStateException();
        }

        return component;
    }

    private Module.Factory proccessFactory(Node item) {
        Module.Factory factory = new Module.Factory();
        String value = getAttributeValue(item, "ref", () -> new IllegalStateException("Must to precise the value reference."));
        String methodName = getAttributeValue(item, "method", () -> new IllegalStateException("Must to precise the method name."));
        factory.setReference(value);
        factory.setMethodName(methodName);
        return factory;
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

        String classname = getAttributeValue(node, "class", () -> new IllegalStateException("Must to precise the class."));

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

    private <T> List<T> processNodeList(NodeList list, Function<Node, T> mapper) {
        return IntStream.range(0, list.getLength())
                .mapToObj(list::item)
                .map(mapper)
                .toList();
    }

    private <T> Module.Value<T> processValue(Node item, Supplier<Module.Value<T>> valueFactory, Function<String, T> mapper) {
        var mValue = valueFactory.get();
        String id = getAttributeValue(item, "id", () -> new IllegalStateException("Must have an id."));
        String valueStr = getAttributeValue(item, "value", () -> new IllegalStateException("Must have a value."));
        mValue.setId(id);
        mValue.setValue(mapper.apply(valueStr));
        return mValue;
    }

    private List<Module.Value<Boolean>> processBoolean(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<Boolean>::new, Boolean::parseBoolean));
    }

    private List<Module.Value<Double>> processDoubles(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<Double>::new, Double::parseDouble));
    }

    private List<Module.Value<Long>> processLongs(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<Long>::new, Long::parseLong));
    }

    private List<Module.Value<Byte>> processBytes(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<Byte>::new, Byte::parseByte));
    }

    private List<Module.Value<Integer>> processIntegers(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<Integer>::new, Integer::parseInt));
    }

    private List<Module.Value<Float>> processFloats(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<Float>::new, Float::parseFloat));
    }

    private List<Module.Value<String>> processStrings(NodeList list) {
        return processNodeList(list, item -> processValue(item, Module.Value<String>::new, Function.identity()));
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

    private String getAttributeValue(Node node, String name, String defaultValue) {
        var opt = Optional.ofNullable(node.getAttributes().getNamedItem(name));
        if (opt.isPresent()) {
            return opt.get().getNodeValue();
        } else {
            return defaultValue;
        }
    }

    private String getAttributeValue(Node node, String name, Supplier<? extends RuntimeException> orElseThrow) {
        return Optional.ofNullable(node.getAttributes().getNamedItem(name))
                .orElseThrow(orElseThrow)
                .getNodeValue();
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

    private boolean isDerived(Node item) {
        return item.getNodeName().equals("derived");
    }

    private boolean isFactory(Node item) {
        return item.getNodeName().equals("factory");
    }


}
