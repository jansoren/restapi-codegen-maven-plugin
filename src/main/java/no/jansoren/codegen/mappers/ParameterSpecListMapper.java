package no.jansoren.codegen.mappers;

import com.squareup.javapoet.ParameterSpec;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ParameterSpecListMapper {

    public static List<ParameterSpec> map(Method method) {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();
        if(method != null) {
            for (int i = 0; i < method.getParameterCount(); i++) {
                ParameterSpec parameterSpec = createParameterSpecMapper(method, i).map();
                parameterSpecs.add(parameterSpec);
            }
        }
        return parameterSpecs;
    }

    private static ParameterSpecMapper createParameterSpecMapper(Method method, int i) {
        Class<?> parameterType = method.getParameterTypes()[i];
        Annotation[] annotations = method.getParameterAnnotations()[i];
        return new ParameterSpecMapper(parameterType, annotations);
    }
}
