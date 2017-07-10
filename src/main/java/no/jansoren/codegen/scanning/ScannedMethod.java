package no.jansoren.codegen.scanning;

import java.lang.reflect.Method;

public class ScannedMethod {

    private String name;
    private String httpMethod;
    private String path;
    private Class classToReturn;
    private Method method;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class getClassToReturn() {
        return classToReturn;
    }

    public void setClassToReturn(Class classToReturn) {
        this.classToReturn = classToReturn;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "ScannedMethod{" +
                "name='" + name + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", path='" + path + '\'' +
                ", classToReturn=" + classToReturn +
                ", method=" + method +
                '}';
    }
}
