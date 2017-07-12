package no.jansoren.codegen.mappers;

import com.squareup.javapoet.ParameterSpec;
import no.jansoren.codegen.utils.MethodUtil;

import java.lang.annotation.Annotation;

public class ParameterSpecMapper {

    private static final String PARAM_NAME_POSTFIX = "Param";

    private Class<?> parameterType;
    private Annotation[] annotations;

    public ParameterSpecMapper(Class<?> parameterType, Annotation[] annotations) {
        this.parameterType = parameterType;
        this.annotations = annotations;
    }

    public ParameterSpec map() {
        String parameterName = getParameterName();
        return ParameterSpec.builder(parameterType, parameterName)
                .build();
    }

    private String getParameterName() {
        String annotationName = MethodUtil.getAnnotationName(annotations);
        if(annotationName != null) {
            return annotationName;
        }
        return MethodUtil.getParameterName(parameterType) + PARAM_NAME_POSTFIX;
    }

}
