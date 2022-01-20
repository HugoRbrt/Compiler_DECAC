package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructionsARM.add;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new ADD(Reg1, storedRegister));
        if(super.getLeftOperand() instanceof FloatLiteral || super.getRightOperand() instanceof FloatLiteral){
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
        }
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler) {
        compiler.addInstruction(new add(storedRegister, Reg1, storedRegister));
    }

    @Override
    protected String getOperatorName() {
        return "+";
    }
}
