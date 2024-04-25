package utours.ultimate.core.internal;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utours.ultimate.core.ClassPathResource;
import utours.ultimate.core.ModuleAnalyzer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class ClassPathXmlModuleAnalyser implements ModuleAnalyzer {

    public static final String MODULE_XML_FILENAME = "module.xml";
    public static final String MODULE_XML_FILEPATH = "/" + MODULE_XML_FILENAME;

    public ClassPathXmlModuleAnalyser() { }

    private void analyseDocument() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.newDocumentBuilder();
        Document document = factory.newDocumentBuilder().parse(ClassPathResource.getResourceAsStream(MODULE_XML_FILEPATH));

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        XPathExpression componentsExpr = xpath.compile("/module/component");
        XPathExpression additionalComponentsExpr = xpath.compile("/module/add-component");

        NodeList components = (NodeList) componentsExpr.evaluate(document, XPathConstants.NODESET);
        NodeList additionalComponents = (NodeList) additionalComponentsExpr.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < additionalComponents.getLength(); i++) {
            System.out.println(additionalComponents.item(i).getTextContent());
        }

        for (int i = 0; i < components.getLength(); i++) {
            System.out.println(components.item(i).getTextContent());
        }

    }

    @Override
    public void analyse() {
        try {
            analyseDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
