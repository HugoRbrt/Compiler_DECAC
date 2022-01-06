/* A manual test for the initial sketch of code generation included in
 * students skeleton.
 *
 * It is not intended to still work when code generation has been updated.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import org.apache.commons.lang.Validate;
/**
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ManualTestGencodeEmptyProgram {

    public static AbstractProgram initTestEmptyProgram() {
        ListInst linst = new ListInst();
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        null);
        return source;
    }

    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(null,null);
        source.codeGenProgram(compiler);
        return compiler.displayIMAProgram();
    }

    public static void testEmptyProgram() {
        AbstractProgram source = initTestEmptyProgram();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
        Validate.isTrue(result.equals(
                ""));
    }



    public static void main(String args[]) {assert(false);
        testEmptyProgram();
    }
}
