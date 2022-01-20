package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructionsARM.sub;


/**
 * @author gl49
 * @date 01/01/2022
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        //to do 'a-b' with :
        //a : Reg1
        //b : storedRegister
        //return storedRegister
        compiler.addInstruction(new SUB(Reg1, storedRegister));
        compiler.addInstruction(new OPP(storedRegister, storedRegister));
        if(super.getLeftOperand() instanceof FloatLiteral || super.getRightOperand() instanceof FloatLiteral){
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
        }
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new sub(storedRegister, Reg1, storedRegister));
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }
}
