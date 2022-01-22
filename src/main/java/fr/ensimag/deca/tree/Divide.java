package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.QUO;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructionsARM.*;


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
        if(getType().isFloat()){
            compiler.addInstruction(new CMP(new ImmediateFloat(0.F), storedRegister));
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("Division by zero")));
            }
            compiler.addInstruction(new DIV(storedRegister, Reg1));
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
            }
        }
        else if(getType().isInt()){
            compiler.addInstruction(new CMP(new ImmediateInteger(0), storedRegister));
            if (!compiler.getCompilerOptions().getNoCheck()) {
                compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("Division by zero")));
            }
            compiler.addInstruction(new QUO(storedRegister, Reg1));
        }
        compiler.addInstruction(new LOAD(Reg1, storedRegister));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        
        if(getType().isInt()) {
            compiler.addInstruction(new cmp(storedRegister, 0));
            compiler.addInstruction(new beq("division_by_zero"));
            compiler.addInstruction(new mov(ARMRegister.r2, Reg1));
            compiler.addInstruction(new mov(ARMRegister.r3, storedRegister));
            compiler.addInstruction(new mov(ARMRegister.r0, ARMRegister.r2));
            compiler.addInstruction(new mov(ARMRegister.r1, ARMRegister.r3));
            compiler.addInstruction(new bl("divide"));
            compiler.addInstruction(new mov(ARMRegister.r0, ARMRegister.r2));
        }
        else {
            compiler.addInstruction(new cmp(storedRegister, 0));
            compiler.addInstruction(new beq("division_by_zero"));
            compiler.addInstruction(new vmov(ARMRegister.s0, Reg1));
            compiler.addInstruction(new vmov(ARMRegister.s1, storedRegister));
            compiler.addARMBlock("        vdiv.f32 s0, s0, s1");
            compiler.addInstruction(new vmov(storedRegister, ARMRegister.s0));
        }
    }

}
