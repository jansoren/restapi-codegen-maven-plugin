package no.jansoren.codegen.generating;

import no.jansoren.codegen.Something;
import no.jansoren.codegen.TestResource;
import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.scanning.ScannedMethod;
import org.junit.Test;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        scannedMethods.add(createScannedMethodsPutWithParamInt());
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
        scannedMethod.setHttpMethod(HttpMethod.GET);
        scannedMethod.setPath("get");
        scannedMethod.setClassToReturn(Something.class);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPost() {
        String methodName = "addSomething";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.POST);
        scannedMethod.setPath("add");
        scannedMethod.setClassToReturn(Something.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPut() {
        String methodName = "putSomething";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.PUT);
        scannedMethod.setPath("put");
        scannedMethod.setClassToReturn(Something.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPutWithAnnotatedPathParam() {
        String methodName = "putSomething2";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.PUT);
        scannedMethod.setPath("put/{id}");
        scannedMethod.setClassToReturn(String.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsPutWithParamInt() {
        String methodName = "putSomething3";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.PUT);
        scannedMethod.setPath("put");
        scannedMethod.setClassToReturn(Something.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsDelete() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("deleteSomething");
        scannedMethod.setHttpMethod(HttpMethod.DELETE);
        scannedMethod.setPath("delete");
        scannedMethod.setClassToReturn(Void.class);
        return scannedMethod;
    }

    private ScannedMethod createScannedMethodsHead() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("headSomething");
        scannedMethod.setHttpMethod(HttpMethod.HEAD);
        scannedMethod.setPath("head");
        scannedMethod.setClassToReturn(Void.class);
        return scannedMethod;
    }

    private Method getMethod(String methodName) {
        Method[] methods = TestResource.class.getMethods();
        for(Method method : methods) {
            if(method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new RuntimeException("Could not find method name. See if the method exist in your class or in your test");
    }

}
