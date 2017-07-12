package no.jansoren.codegen.generating.react;

import no.jansoren.codegen.scanning.ScannedMethod;
import no.jansoren.codegen.utils.MethodUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReactBuilder {

    private List<String> lines;

    public ReactBuilder(){
        lines = new LinkedList<>();
    }

    public List<String> build(){
        return lines;
    }

    public ReactBuilder addEslintDisable(String s) {
        lines.add("/* eslint-disable " + s + "*/");
        return this;
    }

    public ReactBuilder addImport(String s1, String s2) {
        lines.add("import " + s1 + " from '" + s2 + "';");
        return this;
    }

    public ReactBuilder addNewLine() {
        lines.add("");
        return this;
    }

    public ReactBuilder addConst(String s1, String s2) {
        lines.add("const " + s1 + " = '" + s2 + "';");
        return this;
    }

    public ReactBuilder addConstructors(Map<String, Class<?>> constructors) {
        for (Map.Entry<String, Class<?>> entry : constructors.entrySet()) {
            Class<?> clazz = entry.getValue();
            String paramNames = getConstructorParamNames(clazz);
            lines.add("export const " + clazz.getSimpleName() + " = (" + paramNames + ") => ({ " + paramNames + " });");
        }
        return this;
    }

    public ReactBuilder addServices(List<ScannedMethod> scannedMethods) {
        for(ScannedMethod scannedMethod : scannedMethods) {
            String name = scannedMethod.getName();
            String params = getMethodParamNames(scannedMethod.getMethod());
            String path = getPath(scannedMethod);
            String httpMethod = scannedMethod.getHttpMethod().toLowerCase();
            String dataToPostName = getDataToPostName(scannedMethod);
            lines.add("export const " + name + " = " + params + " => axios." + httpMethod + "(`${hostname}/" + path + "`" + dataToPostName +");");
        }
        return this;
    }

    private String getPath(ScannedMethod scannedMethod) {
        return scannedMethod.getPath().replace("{", "${");
    }

    private String getMethodParamNames(Method method) {
        if(method != null) {
            if(method.getParameterCount() == 1) {
                return getParameterName(method.getParameterTypes()[0], method.getParameterAnnotations()[0]);
            } else {
                String params = "(";
                for(int i=0; i<method.getParameterCount(); i++) {
                    params += getParameterName(method.getParameterTypes()[i], method.getParameterAnnotations()[i]);
                    if(i != method.getParameterCount()-1) {
                        params += ", ";
                    }
                }
                params += ")";
                return params;
            }

        }
        return "()";
    }

    private String getConstructorParamNames(Class<?> clazz) {
        String paramNames = "";
        Field[] fields = clazz.getDeclaredFields();
        for(int i=0; i<fields.length; i++) {
            paramNames += fields[i].getName();
            if(i != fields.length-1) {
                paramNames += ", ";
            }
        }
        return paramNames;
    }

    private String getParameterName(Class<?> parameterType, Annotation[] annotations) {
        String annotationName = MethodUtil.getAnnotationName(annotations);
        if(annotationName != null) {
            return annotationName;
        }
        return MethodUtil.getParameterName(parameterType);
    }

    private String getDataToPostName(ScannedMethod scannedMethod) {
        String httpMethod = scannedMethod.getHttpMethod();
        Method method = scannedMethod.getMethod();
        if(httpMethod == "POST" || httpMethod == "PUT") {
            return ", " + method.getParameterTypes()[method.getParameterCount()-1].getSimpleName().toLowerCase();
        }
        return "";
    }
}
