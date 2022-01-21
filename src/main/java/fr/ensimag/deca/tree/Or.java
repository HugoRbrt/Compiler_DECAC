package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SNE;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructionsARM.add;
import fr.ensimag.ima.pseudocode.instructionsARM.cmp;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.movgt;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new ADD(Reg1, storedRegister));
        compiler.addInstruction(new CMP(new ImmediateInteger(0), storedRegister));
        compiler.addInstruction(new SNE(compiler.getListRegister().R0));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new mov(ARMRegister.r3, 0));
        compiler.addInstruction(new add(storedRegister, Reg1, storedRegister));
        compiler.addInstruction(new cmp(storedRegister, 0));
        compiler.addInstruction(new movgt(ARMRegister.r3, 1));
        compiler.addInstruction(new mov(ARMRegister.r0, ARMRegister.r3));

    }

}
