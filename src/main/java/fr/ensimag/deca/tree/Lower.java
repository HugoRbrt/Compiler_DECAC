package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SLT;


/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        super.codeGenOperations(Reg1, storedRegister, compiler);
        compiler.addInstruction(new SLT(compiler.getListRegister().R0));
    }

}
