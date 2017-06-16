package pl.kurczewski;

import org.w3c.dom.Document;
import pl.kurczewski.factory.ParsingException;
import pl.kurczewski.factory.PomFactory;
import pl.kurczewski.factory.PomTreeBuilder;
import pl.kurczewski.factory.XmlParser;
import pl.kurczewski.factory.pojo.Artifact;
import pl.kurczewski.factory.pojo.Dependency;
import pl.kurczewski.factory.pojo.Pom;
import pl.kurczewski.report.HtmlReportBuilder;
import pl.kurczewski.report.ReportWriterFactory;
import pl.kurczewski.tree.Node;
import pl.kurczewski.tree.Tree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String POMS_DIRECTORY = "C:\\Users\\Krzysiek\\IdeaProjects\\PomGraph\\src\\test\\resources\\out";

    public static void main(String[] args) throws Exception {
        List<Pom> poms = getPoms()
                .stream()
                .filter(pom -> "jar".equals(pom.getPackaging()) || pom.getParent() == null)
                .collect(Collectors.toList());

        Tree<Pom> tree = new PomTreeBuilder().build(
                poms.get(poms.size() - 1),
                poms.subList(0, poms.size() - 1)
        );

        Map<String, Node<Pom>> nodes = tree
                .getNodes()
                .stream()
                .collect(Collectors.toMap(
                        n -> n.getValue().getArtifact().toString(),
                        Function.identity()
                ));

        Map<String, List<Dependency>> dependencies = nodes
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> nodes.get(e.getKey()).getValue().getDependencies()
                ));

        Map<String, List<Dependency>> transitiveDependencies = nodes
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new Tree<>(e.getValue())
                                .getUpwardNodes()
                                .stream()
                                .skip(1)
                                .map(Node::getValue)
                                .map(Pom::getDependencies)
                                .flatMap(Collection::stream)
                                .distinct()
                                .sorted()
                                .collect(Collectors.toList())
                ));

        Map<String, List<Dependency>> conflicts = dependencies.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .filter(first -> first.getScope().equals("compile"))
                                .filter(first -> transitiveDependencies
                                        .get(e.getKey())
                                        .stream()
                                        .filter(second -> second.getScope().equals("compile"))
                                        .anyMatch(first::equals))
                                .distinct()
                                .sorted()
                                .collect(Collectors.toList())
                ));

        HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
        ReportWriterFactory writerFactory = new ReportWriterFactory(Paths.get("./target/hvn-report/"));

        for (Pom pom : poms) {
            Path relativePath = Paths.get(
                    pom.getArtifact().getGroupId(), pom.getArtifact().getArtifactId() + ".html"
            );
            FileWriter writer = writerFactory.buildWriter(relativePath);
            htmlReportBuilder.writePom(pom, transitiveDependencies.get(pom.getArtifact().toString()), writer);
        }

        Path relativePath = Paths.get("index.html");
        FileWriter writer = writerFactory.buildWriter(relativePath);
        List<Artifact> artifacts = poms.stream()
                .map(Pom::getArtifact)
                .sorted()
                .collect(Collectors.toList());

        htmlReportBuilder.writeIndex(artifacts, writer);

    }

    private static List<Pom> getPoms() throws IOException {
        List<Pom> poms;
        try (Stream<Path> paths = Files.walk(Paths.get(POMS_DIRECTORY))) {
            poms = paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> file.getName().contains("pom") && file.getName().endsWith(".xml"))
                    .map(Main::parseFile)
                    .collect(Collectors.toList());
        }
        return poms;
    }

    private static Pom parseFile(File file) {
        Document document;
        try {
            document = new XmlParser().parseFile(file);
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        }
        return new PomFactory().build(document);
    }

}
