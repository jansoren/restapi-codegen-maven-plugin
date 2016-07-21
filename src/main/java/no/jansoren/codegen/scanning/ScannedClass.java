package no.jansoren.codegen.scanning;

import java.util.ArrayList;
import java.util.List;

public class ScannedClass {

    private String name;
    private String path;
    private List<ScannedMethod> scannedMethods = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ScannedMethod> getScannedMethods() {
        return scannedMethods;
    }

    public void setScannedMethods(List<ScannedMethod> scannedMethods) {
        this.scannedMethods = scannedMethods;
    }

    @Override
    public String toString() {
        return "ScannedClass{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", scannedMethods=" + scannedMethods +
                '}';
    }
}
