package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.QUO;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;

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
        compiler.addInstruction(new LOAD(storedRegister, compiler.getListRegister().R1));
        compiler.addInstruction(new LOAD(Reg1, storedRegister));
        if(getType().isFloat()){
            compiler.addInstruction(new CMP(new ImmediateFloat(0.F), compiler.getListRegister().R1));
            compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("division_by_zero")));
            compiler.addInstruction(new DIV(Reg1, storedRegister));
        }
        else if(getType().isInt()){
            compiler.addInstruction(new CMP(new ImmediateInteger(0), compiler.getListRegister().R1));
            compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("division_by_zero")));
            compiler.addInstruction(new QUO(Reg1, storedRegister));
        }
    }
}
