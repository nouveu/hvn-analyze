package pl.kurczewski.report;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.kurczewski.factory.pojo.Artifact;
import pl.kurczewski.factory.pojo.Dependency;
import pl.kurczewski.factory.pojo.Plugin;
import pl.kurczewski.factory.pojo.Pom;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class HtmlReportBuilderTest {

    private static final String TEMPLATE_VAR = "$";
    private HtmlReportBuilder htmlReportBuilder;

    @Before
    public void setUp() throws Exception {
        htmlReportBuilder = new HtmlReportBuilder();
    }

    @Test
    public void build_html_page_from_pom() throws IOException {
        // given
        Pom pom = new Pom(new Artifact("pom", "A1", "A2"), "A3");
        pom.addDependency(new Dependency("dependency", "B1", "B2", "B3"));
        pom.addManagedDependency(new Dependency("managed-dependency", "C1", "C2", "C3"));
        pom.addPlugin(new Plugin("plugin", "D1", "D2"));
        pom.addManagedPlugin(new Plugin("managed-plugin", "E1", "E2"));

        StringWriter writer = new StringWriter();

        // when
        htmlReportBuilder.writePom(pom, null, writer);

        // then
        Assert.assertFalse(writer.toString().contains(TEMPLATE_VAR));
    }

    @Test
    public void build_html_page_from_artifact_list() throws IOException {
        // given
        List<Artifact> artifacts = Arrays.asList(
                new Artifact("A1", "A2", "A3"),
                new Artifact("B1", "B2", "B3"),
                new Artifact("C1", "C2", "C3")
        );

        StringWriter writer = new StringWriter();

        // when
        htmlReportBuilder.writeIndex(artifacts, writer);

        // then
        Assert.assertFalse(writer.toString().contains(TEMPLATE_VAR));
    }
}