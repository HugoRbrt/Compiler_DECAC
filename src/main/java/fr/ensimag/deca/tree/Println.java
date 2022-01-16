package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ARMLine;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.svc;

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

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
        super.codeGenInstARM(compiler);
        ARMRegister R = (ARMRegister) compiler.getListRegister();
        compiler.addInstruction(new mov(R.r0,1));
        compiler.addInstruction(new ldr(R.r1, "="+"newline"));
        compiler.addInstruction(new ldr(R.r2, "="+"lennewline"));
        compiler.add(new ARMLine(".data"));
        compiler.add(new ARMLine("newline"+":"));
        compiler.add(new ARMLine(".byte " + "\'" + "\\n" + "\'"));
        compiler.add(new ARMLine("lennewline"+" = . - "+"newline"));
        compiler.add(new ARMLine(".text"));
        compiler.addInstruction(new mov(R.r7,4));
        compiler.addInstruction(new svc(0));
    }

    @Override
    String getSuffix() {
        return "ln";
    }
}
