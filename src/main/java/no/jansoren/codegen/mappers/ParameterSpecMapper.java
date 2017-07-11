package no.jansoren.codegen.mappers;

import com.squareup.javapoet.ParameterSpec;

import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ParameterSpecMapper {

    private static final String PARAM_NAME_POSTFIX = "Param";

    public static List<ParameterSpec> map(Method method) {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();
        if(method != null) {
            for (int i = 0; i < method.getParameterCount(); i++) {
                Class<?> parameterType = method.getParameterTypes()[i];
                Annotation[] annotations = method.getParameterAnnotations()[i];
                parameterSpecs.add(map(parameterType, annotations));
            }
        }
        return parameterSpecs;
    }

    private static ParameterSpec map(Class<?> parameterType, Annotation[] annotations) {
        String parameterName = getParameterName(parameterType, annotations);
        return ParameterSpec.builder(parameterType, parameterName)
                .build();
    }

    private static String getParameterName(Class<?> parameterType, Annotation[] annotations) {
        String annotationName = getAnnotationName(annotations);
        if(annotationName != null) {
            return getAnnotationName(annotations);
        }
        return parameterType.getSimpleName().toLowerCase() + PARAM_NAME_POSTFIX;
    }

    private static String getAnnotationName(Annotation[] annotations) {
        if(hasAnnotation(annotations)) {
            if (isPathParam(annotations[0])) {
                return ((PathParam) annotations[0]).value();
            }
        }
        return null;
    }

    private static boolean hasAnnotation(Annotation[] annotations) {
        return annotations != null && annotations.length > 0;
    }

    private static boolean isPathParam(Annotation annotation) {
        return annotation.annotationType() == PathParam.class;
    }

}
