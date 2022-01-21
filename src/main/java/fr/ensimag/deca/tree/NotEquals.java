package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.SNE;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructionsARM.moveq;
import fr.ensimag.ima.pseudocode.instructionsARM.movle;
import fr.ensimag.ima.pseudocode.instructionsARM.movne;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        super.codeGenOperations(Reg1, storedRegister, compiler);
        compiler.addInstruction(new SNE(storedRegister));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        super.codeGenOperationsARM(Reg1, storedRegister, compiler);
        compiler.addInstruction(new movne(ARMRegister.r0, 1));
    }
}
