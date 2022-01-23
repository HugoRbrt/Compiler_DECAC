package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.instructionsARM.*;


/**
 * @author gl49
 * @date 01/01/2022
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new MUL(Reg1, storedRegister));
        if (getType().isFloat() && !compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
        }
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        if(getType().isInt()) {
            compiler.addInstruction(new mul(storedRegister, Reg1, storedRegister));
        } else {
            compiler.addInstruction(new vmov(ARMRegister.s0, Reg1));
            compiler.addInstruction(new vmov(ARMRegister.s1, storedRegister));
            compiler.addARMBlock("        vmul.f32 s0, s0, s1");
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new bvs("float_arithmetic_overflow"));
            }
            compiler.addInstruction(new vmov(storedRegister, ARMRegister.s0));
        }
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
