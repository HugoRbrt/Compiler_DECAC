package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SGE;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.movle;
import fr.ensimag.ima.pseudocode.instructionsARM.movlt;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class LowerOrEqual extends AbstractOpIneq {
    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<=";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        super.codeGenOperations(Reg1, storedRegister, compiler);
        compiler.addInstruction(new SGE(compiler.getListRegister().R0));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        super.codeGenOperationsARM(Reg1, storedRegister, compiler);
        compiler.addInstruction(new movle(ARMRegister.r0, 1));
    }
}
