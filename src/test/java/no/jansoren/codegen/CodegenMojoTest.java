package no.jansoren.codegen;

import org.junit.Test;

public class CodegenMojoTest {

    @Test
    public void testCodegenMojo() {
        CodegenMojo codegenMojo = new CodegenMojo();
        try {
            codegenMojo.execute();
        } catch (Exception e) {
        }
    }
}
