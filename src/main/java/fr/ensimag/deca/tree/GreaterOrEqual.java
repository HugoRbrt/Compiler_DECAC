package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;

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
        throw new UnsupportedOperationException("not yet implemented");
    }
}
