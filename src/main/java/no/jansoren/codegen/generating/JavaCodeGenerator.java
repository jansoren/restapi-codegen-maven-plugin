package no.jansoren.codegen.generating;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.scanning.ScannedMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JavaCodeGenerator {

    public static void generate(List<ScannedClass> scannedClasses, String generatedCodeFolder, String generatedCodePackage, String rootHost) {
        for (ScannedClass scannedClass : scannedClasses) {

            TypeSpec serviceClass = TypeSpec.classBuilder(scannedClass.getName().replaceAll("Resource", "").concat("Service"))
                    .addField(createWebTargetField())
                    .addMethod(createConstructor(rootHost, scannedClass.getPath()))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethods(createMethodSpecList(scannedClass.getScannedMethods()))
                    .build();

            JavaFile javaFile = JavaFile.builder(generatedCodePackage, serviceClass).build();

            try {
                javaFile.writeTo(new File(generatedCodeFolder));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static FieldSpec createWebTargetField() {
        return FieldSpec.builder(WebTarget.class, "target")
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    private static MethodSpec createConstructor(String rootHost, String path) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T client = $T.newClient()", Client.class, ClientBuilder.class)
                .addStatement("target = client.target($S).path($S)", rootHost, path)
                .build();
    }

    private static List<MethodSpec> createMethodSpecList(List<ScannedMethod> scannedMethods) {
        List<MethodSpec> methodSpecList = new ArrayList<>();
        for(ScannedMethod scannedMethod : scannedMethods) {
            MethodSpec methodSpec = createMethodSpec(scannedMethod);
            if(methodSpec != null) {
                methodSpecList.add(methodSpec);
            }
        }
        Collections.sort(methodSpecList, createMethodSpecComparator());
        return methodSpecList;
    }

    private static MethodSpec createMethodSpec(ScannedMethod scannedMethod) {
        if(HttpMethod.GET.equals(scannedMethod.getMethod())) {
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target.path($S).request($T.APPLICATION_JSON_TYPE).get()", Response.class, scannedMethod.getPath(), MediaType.class)
                    .addStatement("return response.readEntity($T)", scannedMethod.getClassToReturn())
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else if(HttpMethod.POST.equals(scannedMethod.getMethod())) {
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .addParameter(scannedMethod.getClassToPost(), "dataToPost")
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target.path($S).request($T.APPLICATION_JSON_TYPE).post($T.entity(dataToPost, $T.APPLICATION_JSON_TYPE))", Response.class, scannedMethod.getPath(), MediaType.class, Entity.class, MediaType.class)
                    .addStatement("return response.readEntity($T)", scannedMethod.getClassToReturn())
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else if(HttpMethod.PUT.equals(scannedMethod.getMethod())) {
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .addParameter(scannedMethod.getClassToPost(), "dataToPut")
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target.path($S).request($T.APPLICATION_JSON_TYPE).put($T.entity(dataToPut, $T.APPLICATION_JSON_TYPE))", Response.class, scannedMethod.getPath(), MediaType.class, Entity.class, MediaType.class)
                    .addStatement("return response.readEntity($T)", scannedMethod.getClassToReturn())
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else if(HttpMethod.DELETE.equals(scannedMethod.getMethod())) {
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target.path($S).request($T.APPLICATION_JSON_TYPE).delete()", Response.class, scannedMethod.getPath(), MediaType.class)
                    .addStatement("return response.readEntity($T)", Void.class)
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else {
            return null;
        }
    }

    private static Comparator<MethodSpec> createMethodSpecComparator() {
        return new Comparator<MethodSpec>() {
            @Override
            public int compare(MethodSpec o1, MethodSpec o2) {
                return o1.name.compareTo(o2.name);
            }
        };
    }

}
