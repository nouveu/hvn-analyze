package pl.kurczewski.factory;

import org.junit.Test;
import pl.kurczewski.factory.ParsingException;
import pl.kurczewski.factory.XmlParser;

import java.nio.file.Paths;

public class XmlParserTest {

    @Test(expected = ParsingException.class)
    public void should_throw_exception_when_invalid_file() throws Exception {
        XmlParser xmlParser = new XmlParser();
        xmlParser.parseFile(Paths.get("./src/test/resources/invalid-pom.xml").toFile());
    }

    @Test(expected = ParsingException.class)
    public void should_throw_exception_when_missing_file() throws Exception {
        XmlParser xmlParser = new XmlParser();
        xmlParser.parseFile(Paths.get("./src/test/resources/missing-pom.xml").toFile());
    }

}