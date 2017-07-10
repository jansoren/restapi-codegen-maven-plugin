package no.jansoren.codegen.mappers;

import com.squareup.javapoet.AnnotationSpec;

import javax.ws.rs.PathParam;
import java.lang.annotation.Annotation;

public class AnnotationSpecMapper {

    public static AnnotationSpec map(Annotation annotation) {
        String annotationName = getAnnotationName(annotation);
        if(annotationName != null) {
            return AnnotationSpec
                    .builder(annotation.annotationType())
                    .addMember("test", "$S", annotationName)
                    .build();
        }
        return AnnotationSpec
                .builder(annotation.annotationType())
                .build();
    }

    private static String getAnnotationName(Annotation annotation) {
        if(annotation.annotationType() == PathParam.class) {
            return ((PathParam) annotation).value();
        }
        return null;
    }
}
