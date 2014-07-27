package halleck.webserver;

import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ResourceLoaderTest {

    @Test
    public void canGetAResourceFileFromTheJar() {
        ResourceLoader loader = new ResourceLoader();
        Optional<String> content = loader.getContent("resource://testfile.txt");
        assertEquals("Hello World", content.get());
    }

    @Test
    public void willReturnEmptyForUnknownFile() {
        ResourceLoader loader = new ResourceLoader();
        assertEquals(false, loader.getContent("resource://does-not-exist.txt").isPresent());
    }

    @Test
    public void willGetContentForExternalFile() {
        ResourceLoader loader = new ResourceLoader();
        String content = loader.getContent("src/test/resources/testfile.txt").get();
        assertEquals("Hello World", content);
    }

    @Test
    public void willReturnEmptyForUnknownExternalFile() {
        ResourceLoader loader = new ResourceLoader();
        assertEquals(false, loader.getContent("does-not-exist.txt").isPresent());
    }
}