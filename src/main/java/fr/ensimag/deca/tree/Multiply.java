package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        throw new UnsupportedOperationException("not yet implemented");
    }

}
