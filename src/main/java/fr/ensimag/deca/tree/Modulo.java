package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructions.REM;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t1 = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type t2 = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        Type resType = ContextTools.typeArithModulo(compiler, t1, t2, getLocation());
        setType(resType);
        return resType;
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new CMP(new ImmediateInteger(0), storedRegister));
        compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("Division by zero")));
        compiler.addInstruction(new REM(storedRegister, Reg1));
        compiler.addInstruction(new LOAD(Reg1, storedRegister));
    }
}
