package pl.kurczewski.factory.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PomTest {

    private Pom pom;

    @Before
    public void setUp() throws Exception {
        pom = new Pom(new Artifact("com.example", "example-jar", "1.0"), "jar");
    }

    @Test
    public void return_true_if_pom_have_dependency() throws Exception {
        pom.addDependency(new Dependency("org.json", "json", "1.0", "test"));

        boolean sameScope = pom.hasDependency(new Dependency("org.json", "json", "1.0", "compile"));
        boolean differentScope = pom.hasDependency(new Dependency("org.json", "json", "1.0", "test"));
        boolean differentVersion = pom.hasDependency(new Artifact("org.json", "json", "1.1"));

        Assert.assertTrue(sameScope);
        Assert.assertTrue(differentScope);
        Assert.assertTrue(differentVersion);
    }

    @Test
    public void return_false_if_pom_have_not_dependency() throws Exception {
        pom.addDependency(new Dependency("org.json", "json", "1.0", "test"));

        boolean differentArtifact = pom.hasDependency(new Artifact("org.json", "json-xyz", "1.0"));

        Assert.assertFalse(differentArtifact);
    }
}