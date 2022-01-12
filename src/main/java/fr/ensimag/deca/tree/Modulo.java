package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;

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
        throw new UnsupportedOperationException("not yet implemented");
    }
}
