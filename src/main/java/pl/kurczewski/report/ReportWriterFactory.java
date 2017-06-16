package pl.kurczewski.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportWriterFactory {

    private final Path targetDir;

    public ReportWriterFactory(Path targetDir) {
        this.targetDir = targetDir;
    }

    public FileWriter buildWriter(Path relativePath) {
        try {
            Path path = targetDir.resolve(relativePath);
            Files.createDirectories(path.getParent());
            File file = Files.exists(path) ? path.toFile() : Files.createFile(path).toFile();

            return new FileWriter(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
