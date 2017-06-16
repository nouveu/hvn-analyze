package pl.kurczewski.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.kurczewski.factory.pojo.Artifact;
import pl.kurczewski.factory.pojo.Dependency;
import pl.kurczewski.factory.pojo.Plugin;
import pl.kurczewski.factory.pojo.Pom;

import javax.xml.xpath.*;
import java.util.HashMap;
import java.util.Map;

public class PomFactory {

    public static final String DEFAULT_SCOPE = "compile";
    public static final String DEFAULT_PACKAGING = "jar";
    public static final String DEFAULT_VERSION = "-";

    private static final String GROUP_ID = "groupId";
    private static final String ARTIFACT_ID = "artifactId";
    private static final String SCOPE = "scope";
    private static final String VERSION = "version";

    private final XPathExpression artifactNameExpr;
    private final XPathExpression parentExpr;
    private final XPathExpression packagingExpr;
    private final XPathExpression dependenciesExpr;
    private final XPathExpression dependencyManagementExpr;
    private final XPathExpression pluginsExpr;
    private final XPathExpression pluginsManagementExpr;

    public PomFactory() {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            artifactNameExpr = xpath.compile("//project/groupId | //project/artifactId | //project/version");
            parentExpr = xpath.compile("//parent/groupId | //parent/artifactId | //parent/version");
            packagingExpr = xpath.compile("//project/packaging");
            dependenciesExpr = xpath.compile("//project/dependencies/dependency");
            dependencyManagementExpr = xpath.compile("//dependencyManagement/dependencies/dependency");
            pluginsExpr = xpath.compile("//build/plugins/plugin");
            pluginsManagementExpr = xpath.compile("//build/pluginManagement/plugins/plugin");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public Pom build(Document pomDocument) {
        try {
            Pom pom = buildPom(pomDocument);

            NodeList dependenciesNode = (NodeList) dependenciesExpr.evaluate(pomDocument, XPathConstants.NODESET);
            for (int i = 0; i < dependenciesNode.getLength(); i++) {
                Node dependency = dependenciesNode.item(i);
                pom.addDependency(extractDependency(dependency.getChildNodes()));
            }

            NodeList dependencyManagementNode = (NodeList) dependencyManagementExpr.evaluate(pomDocument, XPathConstants.NODESET);
            for (int i = 0; i < dependencyManagementNode.getLength(); i++) {
                Node item = dependencyManagementNode.item(i);
                pom.addManagedDependency(extractDependency(item.getChildNodes()));
            }

            NodeList pluginsNode = (NodeList) pluginsExpr.evaluate(pomDocument, XPathConstants.NODESET);
            for (int i = 0; i < pluginsNode.getLength(); i++) {
                Node plugin = pluginsNode.item(i);
                pom.addPlugin(extractPlugin(plugin.getChildNodes()));
            }

            NodeList pluginManagementNode = (NodeList) pluginsManagementExpr.evaluate(pomDocument, XPathConstants.NODESET);
            for (int i = 0; i < pluginManagementNode.getLength(); i++) {
                Node plugin = pluginManagementNode.item(i);
                pom.addManagedPlugin(extractPlugin(plugin.getChildNodes()));
            }

            return pom;

        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    private Pom buildPom(Object pomDocument) throws XPathExpressionException {
        NodeList nameNode = (NodeList) artifactNameExpr.evaluate(pomDocument, XPathConstants.NODESET);
        Map<String, String> artifactValues = extractValues(nameNode);

        NodeList parentNode = (NodeList) parentExpr.evaluate(pomDocument, XPathConstants.NODESET);
        Map<String, String> parentValues = extractValues(parentNode);

        Artifact artifact = new Artifact(
                artifactValues.getOrDefault(GROUP_ID, parentValues.get(GROUP_ID)),
                artifactValues.get(ARTIFACT_ID),
                artifactValues.getOrDefault(VERSION, parentValues.get(VERSION))
        );

        String packagingType = (String) packagingExpr.evaluate(pomDocument, XPathConstants.STRING);
        Pom pom = new Pom(artifact, (packagingType.isEmpty() ? DEFAULT_PACKAGING : packagingType));

        if (!parentValues.isEmpty()) {
            Artifact parent = new Artifact(
                    parentValues.get(GROUP_ID),
                    parentValues.get(ARTIFACT_ID),
                    parentValues.get(VERSION)
            );
            pom.setParent(parent);
        }
        return pom;
    }

    private Plugin extractPlugin(NodeList nodes) {
        if (nodes.getLength() == 0) return null;

        Map<String, String> map = extractValues(nodes);
        return new Plugin(
                map.get(GROUP_ID),
                map.get(ARTIFACT_ID),
                map.getOrDefault(VERSION, DEFAULT_VERSION)
        );
    }

    private Dependency extractDependency(NodeList nodes) {
        if (nodes.getLength() == 0) return null;

        Map<String, String> map = extractValues(nodes);
        return new Dependency(
                map.get(GROUP_ID),
                map.get(ARTIFACT_ID),
                map.getOrDefault(VERSION, DEFAULT_VERSION),
                map.getOrDefault(SCOPE, DEFAULT_SCOPE)
        );
    }

    private Map<String, String> extractValues(NodeList nodes) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node file = nodes.item(i);
            map.put(file.getNodeName(), file.getTextContent());
        }
        return map;
    }

}
