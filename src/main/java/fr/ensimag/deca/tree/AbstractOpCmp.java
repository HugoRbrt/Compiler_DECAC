package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t1 = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type t2 = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        Type resType = ContextTools.typeCmpOp(compiler, getOperatorName(), t1, t2, getLocation());
        if (t1.isInt()) {
            setLeftOperand(new ConvFloat(getLeftOperand()));
            getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        } else if (t2.isInt()) {
            setRightOperand(new ConvFloat(getRightOperand()));
            getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        }
        setType(resType);
        return resType;
    }

    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new CMP(Reg1, storedRegister));
    }
}
