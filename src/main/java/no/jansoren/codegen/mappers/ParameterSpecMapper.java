package no.jansoren.codegen.mappers;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ParameterSpec;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ParameterSpecMapper {

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
        AnnotationSpec annotationSpec = mapAnnotationSpec(annotations);

        if(annotationSpec != null) {
            return ParameterSpec.builder(parameterType, parameterType.getSimpleName().toLowerCase())
                    .addAnnotation(annotationSpec)
                    .build();
        }
        return ParameterSpec.builder(parameterType, parameterType.getSimpleName().toLowerCase())
                .build();
    }

    private static AnnotationSpec mapAnnotationSpec(Annotation[] annotations) {
        AnnotationSpec annotationSpec = null;
        if(annotations.length > 0) {
            annotationSpec = AnnotationSpecMapper.map(annotations[0]);
        }
        return annotationSpec;
    }

}
