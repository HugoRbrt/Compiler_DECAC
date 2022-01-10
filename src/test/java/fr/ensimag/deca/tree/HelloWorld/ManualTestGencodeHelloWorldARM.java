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
public class ManualTestGencodeHelloWorldARM {
    
    public static AbstractProgram initTestHelloWorld() {
        ListInst linst = new ListInst();
        AbstractProgram source =
            new Program(
                new ListDeclClass(),
                new Main(new ListDeclVar(),linst));
        ListExpr lexp1 = new ListExpr();
        linst.add(new Print(false,lexp1));
        lexp1.add(new StringLiteral("HelloWorld"));
        return source;
    }
    
    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(null,null);
        source.codeGenProgramARM(compiler);
        return compiler.displayProgram();
    }

    public static void testHelloWorld() {
        AbstractProgram source = initTestHelloWorld();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");        
        String result = gencodeSource(source);
        System.out.println(result);
        /*Validate.isTrue(result.equals(
                "// Main ARM program\n" +
                "// Beginning of main ARM instructions:\n" +
                ".text\n" +
                ".global_start\n" +
                "_start:\n" +
                "   mov r0, #1\n" +
                "   ldr r1, =msg0\n" +
                "   ldr r2, =len0:\n" +
                        
                ".data\n" +
                "msg0:\n" +
                "-ascii \"HelloWorld\"\n"+
                "-len0 = . - msg0\n" +
                        
                ".text\n" +
                "   mov r7, #4\n" +
                "   svc #0\n" +
                "   mov r0, #0\n" +
                "   mov r7, #1\n" +
                "   svc #0\n"));*/
    }


        
    public static void main(String args[]) {assert(false);
        testHelloWorld();
    }
}
