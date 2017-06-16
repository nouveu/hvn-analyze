package pl.kurczewski.report;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import pl.kurczewski.factory.pojo.Artifact;
import pl.kurczewski.factory.pojo.Dependency;
import pl.kurczewski.factory.pojo.Pom;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class HtmlReportBuilder {

    private static final String TEMPLATE_DIR = "./src/main/resources/";
    private static final String UTF_8 = "utf-8";

    public HtmlReportBuilder() {
        Velocity.init();
    }

    public void writePom(Pom pom, List<Dependency> transitives, Writer writer) {
        VelocityContext context = new VelocityContext();
        context.put("pom", pom);
        context.put("transitives", transitives);

        merge(writer, context, "pom.vm");
    }

    public void writeIndex(List<Artifact> artifacts, Writer writer) {
        VelocityContext context = new VelocityContext();
        context.put("artifacts", artifacts);

        merge(writer, context, "index.vm");
    }

    private void merge(Writer writer, VelocityContext context, String templateName) {
        Velocity.mergeTemplate(TEMPLATE_DIR + templateName, UTF_8, context, writer);

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
