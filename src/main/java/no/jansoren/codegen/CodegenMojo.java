package no.jansoren.codegen;

import no.jansoren.codegen.generating.JavaCodeGenerator;
import no.jansoren.codegen.generating.ReactCodeGenerator;
import no.jansoren.codegen.scanning.ResourcesScanner;
import no.jansoren.codegen.scanning.ScannedClass;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;

@Mojo( name = "codegen")
public class CodegenMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject mavenProject;

    @Parameter( property = "generatedJavaCodeFolder", defaultValue = "generated-java-code" )
    private String generatedJavaCodeFolder;

    @Parameter( property = "generatedJavaCodePackage", defaultValue = "com.example.services" )
    private String generatedJavaCodePackage;

    @Parameter( property = "generatedReactCodeFolder", defaultValue = "generated-react-code" )
    private String generatedReactCodeFolder;

    @Parameter(property = "rootHost", defaultValue = "http://localhost:8080/")
    private String rootHost;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating code - " + generatedJavaCodeFolder);

        ResourcesScanner scanner = new ResourcesScanner(getLog(), mavenProject);

        List<ScannedClass> scannedClasses = scanner.scan();
        for(ScannedClass scannedClass : scannedClasses) {
            getLog().info("Class that is scanned: " + scannedClass);
        }

        getLog().info("Generated java code folder" + new File(generatedJavaCodeFolder).getAbsolutePath());
        JavaCodeGenerator.generate(scannedClasses, generatedJavaCodeFolder, generatedJavaCodePackage, rootHost);

        getLog().info("Generated react code folder" + new File(generatedReactCodeFolder).getAbsolutePath());
        ReactCodeGenerator.generate(scannedClasses, generatedReactCodeFolder, rootHost);

    }

}
