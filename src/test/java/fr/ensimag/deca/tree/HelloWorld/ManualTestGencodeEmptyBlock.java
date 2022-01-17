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
public class ManualTestGencodeEmptyBlock {
    
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
        source.codeGenProgram(compiler);
        return compiler.displayProgram();
    }

    public static void testEmptyBlock() {
        AbstractProgram source = initTestEmptyBlock();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");        
        String result = gencodeSource(source);
        System.out.println(result);
        Validate.isTrue(result.equals(
                "; Main program\n" +
                "; Beginning of main instructions:\n" +
                "	HALT\n"));
    }


        
    public static void main(String args[]) {assert(false);
        testEmptyBlock();
    }
}
