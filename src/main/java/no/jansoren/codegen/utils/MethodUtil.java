package no.jansoren.codegen.utils;


import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodUtil {

    public static String getParameterName(Class<?> parameterType) {
        return parameterType.getSimpleName().toLowerCase();
    }

    public static String getAnnotationName(Annotation[] annotations) {
        if(isPathParam(annotations)) {
            return ((PathParam) annotations[0]).value();
        }
        return null;
    }

    public static boolean isPathParam(Annotation[] annotations) {
        if(hasAnnotation(annotations)) {
            return annotations[0].annotationType() == PathParam.class;
        }
        return false;
    }

    private static boolean hasAnnotation(Annotation[] annotations) {
        return annotations != null && annotations.length > 0;
    }
}
