package no.jansoren.codegen.scanning;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.assertNotNull;

public class ResourcesScannerTest {

    @Test
    public void testScan() throws MojoExecutionException {
        ResourcesScanner scanner = new ResourcesScanner(null, null);

        List<ScannedClass> scannedClasses = scanner.scan();
        for(ScannedClass scannedClass : scannedClasses) {
            System.out.println(scannedClass.toString());
            assertNotNull(scannedClass.getName());
            for(ScannedMethod scannedMethod : scannedClass.getScannedMethods()) {
                assertNotNull(scannedMethod.getName());
                assertNotNull(scannedMethod.getHttpMethod());
                assertNotNull(scannedMethod.getClassToReturn());
            }
        }
    }
}
