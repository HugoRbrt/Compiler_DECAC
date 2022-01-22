package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructionsARM.*;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new WNL());
    }

    private static boolean armAlreadyLabel = false;

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
        super.codeGenInstARM(compiler);
        compiler.addInstruction(new ldr(ARMRegister.r0, "="+"newline"));
        compiler.addInstruction(new bl("printf"));
        
    }

    @Override
    String getSuffix() {
        return "ln";
    }
}
