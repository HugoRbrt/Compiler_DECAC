package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        if(getType().isFloat())
        compiler.addInstruction(new DIV(Reg1, storedRegister));
        else if(getType().isInt()){
            compiler.addInstruction(new QUO(Reg1, storedRegister));
        }
    }
}
