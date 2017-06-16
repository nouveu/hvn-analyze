package pl.kurczewski.factory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import pl.kurczewski.factory.pojo.*;

import java.nio.file.Paths;
import java.util.List;

public class PomFactoryIT {

    private XmlParser xmlParser = new XmlParser();

    @Test
    public void should_build_simple_pom() throws Exception {
        Document document = xmlParser.parseFile(Paths.get("./src/test/resources/simple-pom.xml").toFile());

        PomFactory pomFactory = new PomFactory();
        Pom pom = pomFactory.build(document);

        boolean result = pom.hasDependency(new ArtifactDefinition("org.json", "json"));
        Assert.assertTrue(result);

        String pomName = pom.getArtifact().toString();
        Assert.assertEquals("com.example:simple-pom", pomName);
    }

    @Test
    public void should_build_pom_with_parent() throws Exception {
        Document document = xmlParser.parseFile(Paths.get("./src/test/resources/children-pom.xml").toFile());

        PomFactory pomFactory = new PomFactory();
        Pom pom = pomFactory.build(document);

        Artifact artifact = pom.getParent();
        Assert.assertEquals("com.example:parent-pom", artifact.toString());

        String pomName = pom.getArtifact().toString();
        Assert.assertEquals("com.example:children-pom", pomName);
    }

    @Test
    public void should_build_full_pom() throws Exception {
        Document document = xmlParser.parseFile(Paths.get("./src/test/resources/full-pom.xml").toFile());

        PomFactory pomFactory = new PomFactory();
        Pom pom = pomFactory.build(document);

        Artifact parent = pom.getParent();
        Assert.assertEquals("com.example:parent", parent.toString());

        List<Dependency> dependencies = pom.getDependencies();
        String dependencyName = dependencies.get(0).toString();
        Assert.assertEquals("com.example:dependency", dependencyName);

        List<Dependency> managedDependencies = pom.getManagedDependencies();
        String managedDependencyName = managedDependencies.get(0).toString();
        Assert.assertEquals("com.example:managed-dependency", managedDependencyName);

        List<Plugin> plugins = pom.getPlugins();
        String pluginName = plugins.get(0).toString();
        Assert.assertEquals("com.example:plugin", pluginName);

        List<Plugin> managedPlugins = pom.getManagedPlugins();
        String managedPluginName = managedPlugins.get(0).toString();
        Assert.assertEquals("com.example:managed-plugin", managedPluginName);
    }
}