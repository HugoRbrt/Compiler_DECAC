package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.moveq;
import fr.ensimag.ima.pseudocode.instructionsARM.movle;


/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }    

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        super.codeGenOperations(Reg1, storedRegister, compiler);
        compiler.addInstruction(new SEQ(storedRegister));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        super.codeGenOperationsARM(Reg1, storedRegister, compiler);
        compiler.addInstruction(new moveq(ARMRegister.r0, 1));
    }
}
