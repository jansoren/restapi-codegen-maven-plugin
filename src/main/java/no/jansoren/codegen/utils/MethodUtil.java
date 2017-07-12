package no.jansoren.codegen.utils;


import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;

public class MethodUtil {

    public static String getParameterName(Class<?> parameterType) {
        return parameterType.getSimpleName().toLowerCase();
    }

    public static String getAnnotationName(Annotation[] annotations) {
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
