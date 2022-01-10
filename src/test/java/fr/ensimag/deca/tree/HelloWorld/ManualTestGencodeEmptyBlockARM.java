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
public class ManualTestGencodeEmptyBlockARM {

    public static AbstractProgram initTestEmptyBlock() {
        ListInst linst = new ListInst();
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        new Main(new ListDeclVar(),linst));
        return source;
    }

    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(null,null);
        source.codeGenProgramARM(compiler);
        return compiler.displayProgram();
    }

    public static void testEmptyBlock() {
        AbstractProgram source = initTestEmptyBlock();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following ARM assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
        /*Validate.isTrue(result.equals(
                ".text\n" + 
                ".global_start\n" +
                "// Main ARM program\n" +
                "// Beginning of main ARM instructions:\n" +
                "_start:\n" +
                "   mov r0, #0\n" +
                "   mov r7, #1\n" +
                "   svc #0\n"));*/
    }



    public static void main(String args[]) {assert(false);
        testEmptyBlock();
    }
}
