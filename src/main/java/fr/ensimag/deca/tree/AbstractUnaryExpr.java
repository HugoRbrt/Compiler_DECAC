package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;


import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Unary expression.
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    public AbstractExpr getOperand() {
        return operand;
    }
    private AbstractExpr operand;
    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }


    protected abstract String getOperatorName();
  
    
    protected void codeGenInst(DecacCompiler compiler){
        operand.codeGenInst(compiler);
        //GPRegister usedRegister = compiler.getListRegister().getRegister(compiler);
        //compiler.addInstruction(new LOAD(compiler.getListRegister().R0, usedRegister));
        this.codeGenOperations(compiler.getListRegister().R0, compiler);
        //compiler.getListRegister().freeRegister(usedRegister, compiler);
    }

    abstract void codeGenOperations(GPRegister storedRegister, DecacCompiler compiler);
    

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        s.print(getOperatorName());
        operand.decompile(s);
        s.print(")");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

}
