package no.jansoren.codegen.generating;

import com.squareup.javapoet.ParameterSpec;
import no.jansoren.codegen.Something;
import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.scanning.ScannedMethod;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import javax.ws.rs.HttpMethod;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;

public class JavaCodeGeneratorTest {

    @Test
    public void testGenerateCode() {
        String generatedCodeFolder = "src/test/java";
        String generatedCodePackage = "com.example.helloworld";
        List<ScannedClass> scannedClasses = createScannedClasses();
        String rootHost = "https://localhost:1234";
        JavaCodeGenerator.generate(scannedClasses, generatedCodeFolder, generatedCodePackage, rootHost);
    }

    private List<ScannedClass> createScannedClasses() {
        List<ScannedClass> scannedClasses = new ArrayList<>();
        scannedClasses.add(createScannedClass());
        return scannedClasses;
    }

    private List<ScannedMethod> createScannedMethods() {
        List<ScannedMethod> scannedMethods = new ArrayList<>();
        scannedMethods.add(createScannedMethodsGet());
        scannedMethods.add(createScannedMethodsPost());
        scannedMethods.add(createScannedMethodsPut());
        scannedMethods.add(createScannedMethodsPutWithAnnotatedPathParam());
        scannedMethods.add(createScannedMethodsDelete());
        scannedMethods.add(createScannedMethodsHead());
        return scannedMethods;
    }

    private ScannedClass createScannedClass() {
        ScannedClass scannedClass = new ScannedClass();
        scannedClass.setName("MyResource");
        scannedClass.setPath("something");
        scannedClass.setScannedMethods(createScannedMethods());
        return scannedClass;
    }

    private ScannedMethod createScannedMethodsGet() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("getSomething");
        scannedMethod.setMethod(HttpMethod.GET);
        scannedMethod.setPath("get");
        scannedMethod.setClassToReturn(Something.class);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPost() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("addSomething");
        scannedMethod.setMethod(HttpMethod.POST);
        scannedMethod.setPath("add");
        scannedMethod.setClassToReturn(Something.class);
        List<ParameterSpec> specs = new ArrayList<>();
        specs.add(ParameterSpec.builder(Something.class, "dataToPost").build());
        scannedMethod.setParameterSpecs(specs);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPut() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("putSomething");
        scannedMethod.setMethod(HttpMethod.PUT);
        scannedMethod.setPath("put");
        scannedMethod.setClassToReturn(Something.class);
        List<ParameterSpec> specs = new ArrayList<>();
        specs.add(ParameterSpec.builder(Something.class, "dataToPut").build());
        scannedMethod.setParameterSpecs(specs);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPutWithAnnotatedPathParam() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("putSomething");
        scannedMethod.setMethod(HttpMethod.PUT);
        scannedMethod.setPath("put/{id}");
        scannedMethod.setClassToReturn(String.class);
        List<ParameterSpec> specs = new ArrayList<>();
        specs.add(ParameterSpec.builder(String.class, "id").build());
        specs.add(ParameterSpec.builder(Something.class, "dataToPut").build());
        scannedMethod.setParameterSpecs(specs);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsDelete() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("deleteSomething");
        scannedMethod.setMethod(HttpMethod.DELETE);
        scannedMethod.setPath("delete");
        scannedMethod.setClassToReturn(Void.class);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsHead() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("headSomething");
        scannedMethod.setMethod(HttpMethod.HEAD);
        scannedMethod.setPath("head");
        scannedMethod.setClassToReturn(Void.class);
        return scannedMethod;
    }
}
