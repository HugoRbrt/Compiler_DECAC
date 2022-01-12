package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    protected void codeGenInst(DecacCompiler compiler){
        super.codeGenInst(compiler);
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new ADD(Reg1, storedRegister));
    }

    protected void codeGenPrint(DecacCompiler compiler){
        codeGenInst(compiler);
        super.codeGenPrint(compiler);
    }

    @Override
    protected String getOperatorName() {
        return "+";
    }
}
