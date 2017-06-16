package pl.kurczewski.report;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReportWriterFactoryTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void build_file_writer() throws IOException {
        // given
        Path targetDir = temporaryFolder.newFolder("report-writer-test").toPath();
        String targetFile = "temp.html";

        // when
        ReportWriterFactory writerFactory = new ReportWriterFactory(targetDir);
        FileWriter writer = writerFactory.buildWriter(Paths.get(targetFile));

        // then
        String expected = "test writer";
        writer.write(expected);
        writer.close();

        List<String> lines = Files.readAllLines(targetDir.resolve(targetFile));
        Assert.assertEquals(expected, lines.get(0));
    }

}