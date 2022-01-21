package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructionsARM.*;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new ADD(Reg1, storedRegister));
        compiler.addInstruction(new CMP(new ImmediateInteger(2), storedRegister));
        compiler.addInstruction(new SEQ(compiler.getListRegister().R0));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new mov(ARMRegister.r3, 0));
        compiler.addInstruction(new add(storedRegister, Reg1, storedRegister));
        compiler.addInstruction(new cmp(storedRegister, 2));
        compiler.addInstruction(new moveq(ARMRegister.r3, 1));
        compiler.addInstruction(new mov(ARMRegister.r0, ARMRegister.r3));
    }
}
