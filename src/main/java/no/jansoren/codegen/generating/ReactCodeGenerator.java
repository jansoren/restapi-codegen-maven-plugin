package no.jansoren.codegen.generating;

import no.jansoren.codegen.generating.react.ReactBuilder;
import no.jansoren.codegen.scanning.ScannedClass;
import no.jansoren.codegen.scanning.ScannedMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ReactCodeGenerator {

    public static void generate(List<ScannedClass> scannedClasses, String generatedReactCodeFolder, String rootHost) {
        for (ScannedClass scannedClass : scannedClasses) {
            List<ScannedMethod> scannedMethods = scannedClass.getScannedMethods();
            List<String> lines = new ReactBuilder()
                    .addEslintDisable("max-len")
                    .addImport("axios", "axios")
                    .addNewLine()
                    .addConst("hostname", rootHost)
                    .addNewLine()
                    .addConstructors(getConstructors(scannedMethods))
                    .addNewLine()
                    .addServices(scannedMethods)
                    .build();
            Path file = Paths.get(generatedReactCodeFolder + "/" + getFilename(scannedClass));
            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Map<String, Class<?>> getConstructors(List<ScannedMethod> scannedMethods) {
        Map<String, Class<?>> classes = new HashMap<>();
        for(ScannedMethod scannedMethod : scannedMethods) {
            Method method = scannedMethod.getMethod();
            if(method != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                for(Class<?> clazz : parameterTypes) {
                    if(isComplexObject(clazz)) {
                       classes.put(clazz.getSimpleName(), clazz);
                    }
                }
            }
        }
        return classes;
    }

    private static boolean isComplexObject(Class<?> clazz) {
        return clazz != String.class
                && clazz != int.class
                && clazz != long.class
                && clazz != List.class;
    }

    private static String getFilename(ScannedClass scannedClass) {
        return scannedClass.getName().replaceAll("Resource", "").toLowerCase().concat(".service.js");
    }
}
