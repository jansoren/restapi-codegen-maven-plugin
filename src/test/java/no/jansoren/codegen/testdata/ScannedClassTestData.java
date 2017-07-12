package no.jansoren.codegen.testdata;

import no.jansoren.codegen.Something;
import no.jansoren.codegen.TestResource;
import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.scanning.ScannedMethod;

import javax.ws.rs.HttpMethod;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ScannedClassTestData {

    public static List<ScannedClass> createScannedClasses() {
        List<ScannedClass> scannedClasses = new ArrayList<>();
        scannedClasses.add(createScannedClass());
        return scannedClasses;
    }

    private static List<ScannedMethod> createScannedMethods() {
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

    private static ScannedClass createScannedClass() {
        ScannedClass scannedClass = new ScannedClass();
        scannedClass.setName("MyResource");
        scannedClass.setPath("something");
        scannedClass.setScannedMethods(createScannedMethods());
        return scannedClass;
    }

    private static ScannedMethod createScannedMethodsGet() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("getSomething");
        scannedMethod.setHttpMethod(HttpMethod.GET);
        scannedMethod.setPath("get");
        scannedMethod.setClassToReturn(Something.class);
        return scannedMethod;
    }

    private static ScannedMethod createScannedMethodsPost() {
        String methodName = "addSomething";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.POST);
        scannedMethod.setPath("add");
        scannedMethod.setClassToReturn(Something.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private static ScannedMethod createScannedMethodsPut() {
        String methodName = "putSomething";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.PUT);
        scannedMethod.setPath("put");
        scannedMethod.setClassToReturn(Something.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private static ScannedMethod createScannedMethodsPutWithAnnotatedPathParam() {
        String methodName = "putSomething2";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.PUT);
        scannedMethod.setPath("put/{id}");
        scannedMethod.setClassToReturn(String.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private static ScannedMethod createScannedMethodsPutWithParamInt() {
        String methodName = "putSomething3";

        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName(methodName);
        scannedMethod.setHttpMethod(HttpMethod.PUT);
        scannedMethod.setPath("put/{id}");
        scannedMethod.setClassToReturn(Something.class);
        scannedMethod.setMethod(getMethod(methodName));
        return scannedMethod;
    }

    private static ScannedMethod createScannedMethodsDelete() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("deleteSomething");
        scannedMethod.setHttpMethod(HttpMethod.DELETE);
        scannedMethod.setPath("delete");
        scannedMethod.setClassToReturn(Void.class);
        return scannedMethod;
    }

    private static ScannedMethod createScannedMethodsHead() {
        ScannedMethod scannedMethod = new ScannedMethod();
        scannedMethod.setName("headSomething");
        scannedMethod.setHttpMethod(HttpMethod.HEAD);
        scannedMethod.setPath("head");
        scannedMethod.setClassToReturn(Void.class);
        return scannedMethod;
    }

    private static Method getMethod(String methodName) {
        Method[] methods = TestResource.class.getMethods();
        for(Method method : methods) {
            if(method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new RuntimeException("Could not find method name. See if the method exist in your class or in your test");
    }
}
