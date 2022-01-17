package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.deca.DecacCompiler;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t1 = getOperand().verifyExpr(compiler, localEnv, currentClass);
        Type resType = ContextTools.typeUnaryNot(compiler, t1, getLocation());
        setType(resType);
        return resType;
    }

    public void codeGenOperations(GPRegister storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new CMP(0, storedRegister));
        compiler.addInstruction(new SEQ(storedRegister));
    }

    @Override
    protected String getOperatorName() {
        return "!";
    }
}
