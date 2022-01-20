package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SLE;

/**
 * Operator "x >= y"
 * 
 * @author gl49
 * @date 01/01/2022
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">=";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new CMP(Reg1, storedRegister));
        compiler.addInstruction(new SLE(compiler.getListRegister().R0));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        // TODO
    }
}
