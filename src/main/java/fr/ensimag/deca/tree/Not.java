package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
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


    @Override
    protected String getOperatorName() {
        return "!";
    }
}
