package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructionsARM.*;


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
        if(getLeftOperand().getType().isFloat() || getRightOperand().getType().isFloat()){
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
        }
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        if(getType().isInt()) {
            compiler.addInstruction(new sub(storedRegister, Reg1, storedRegister));
        } else {
            compiler.addInstruction(new vmov(ARMRegister.s0, Reg1));
            compiler.addInstruction(new vmov(ARMRegister.s1, storedRegister));
            compiler.addARMBlock("        vsub.f32 s0, s0, s1");
            compiler.addInstruction(new vmov(storedRegister, ARMRegister.s0));
        }
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }
}
