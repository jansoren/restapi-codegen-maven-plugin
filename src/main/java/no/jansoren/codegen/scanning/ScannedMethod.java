package no.jansoren.codegen.scanning;

public class ScannedMethod {

    private String name;
    private String method;
    private String path;
    private Class classToReturn;
    private Class classToPost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public Class getClassToPost() {
        return classToPost;
    }

    public void setClassToPost(Class classToPost) {
        this.classToPost = classToPost;
    }

    @Override
    public String toString() {
        return "ScannedMethod{" +
                "name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", classToReturn=" + classToReturn +
                ", classToPost=" + classToPost +
                '}';
    }
}
