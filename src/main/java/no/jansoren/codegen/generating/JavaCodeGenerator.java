package no.jansoren.codegen.generating;

import com.squareup.javapoet.*;
import no.jansoren.codegen.mappers.ParameterSpecMapper;
import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.scanning.ScannedMethod;

import javax.lang.model.element.Modifier;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        if(HttpMethod.GET.equals(scannedMethod.getHttpMethod())) {
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target" +
                            getPath(scannedMethod) +
                            ".request($T.APPLICATION_JSON_TYPE)" +
                            ".get()", Response.class, MediaType.class)
                    .addStatement("return response.readEntity($T.class)", scannedMethod.getClassToReturn())
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else if(HttpMethod.POST.equals(scannedMethod.getHttpMethod())) {
            List<ParameterSpec> parameterSpecs = ParameterSpecMapper.map(scannedMethod.getMethod());
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .addParameters(parameterSpecs)
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target" +
                            getPath(scannedMethod) +
                            ".request($T.APPLICATION_JSON_TYPE)" +
                            ".post($T.entity(dataToPost, $T.APPLICATION_JSON_TYPE))", Response.class, MediaType.class, Entity.class, MediaType.class)
                    .addStatement("return response.readEntity($T.class)", scannedMethod.getClassToReturn())
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else if(HttpMethod.PUT.equals(scannedMethod.getHttpMethod())) {
            List<ParameterSpec> parameterSpecs = ParameterSpecMapper.map(scannedMethod.getMethod());
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .addParameters(parameterSpecs)
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target" +
                            getPath(scannedMethod) +
                            ".request($T.APPLICATION_JSON_TYPE)" +
                            ".put($T.entity(dataToPut, $T.APPLICATION_JSON_TYPE))", Response.class, MediaType.class, Entity.class, MediaType.class)
                    .addStatement("return response.readEntity($T.class)", scannedMethod.getClassToReturn())
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else if(HttpMethod.DELETE.equals(scannedMethod.getHttpMethod())) {
            return MethodSpec.methodBuilder(scannedMethod.getName())
                    .returns(scannedMethod.getClassToReturn())
                    .addStatement("$T response = target" +
                            getPath(scannedMethod) +
                            ".request($T.APPLICATION_JSON_TYPE)" +
                            ".delete()", Response.class, MediaType.class)
                    .addStatement("return response.readEntity($T.class)", Void.class)
                    .addModifiers(Modifier.PUBLIC)
                    .build();
        } else {
            return null;
        }
    }

    private static String getPath(ScannedMethod scannedMethod) {
        String path = scannedMethod.getPath()
                .replace("{", "\" + ")
                .replace("}", " + \"");
        return ".path(\"" + path + "\")";
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
