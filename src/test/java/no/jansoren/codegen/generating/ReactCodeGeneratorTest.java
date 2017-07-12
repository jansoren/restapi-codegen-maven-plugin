package no.jansoren.codegen.generating;

import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.testdata.ScannedClassTestData;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ReactCodeGeneratorTest {

    @Test
    public void testGenerateReactCode() throws IOException {
        String generatedReactCodeFolder = "src/test/java/com/example/helloworld";
        List<ScannedClass> scannedClasses = ScannedClassTestData.createScannedClasses();
        String rootHost = "https://localhost:1234";
        ReactCodeGenerator.generate(scannedClasses, generatedReactCodeFolder, rootHost);
    }
}
