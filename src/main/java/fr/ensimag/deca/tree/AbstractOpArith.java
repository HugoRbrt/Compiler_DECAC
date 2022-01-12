package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;

/**
 * Arithmetic binary operations (+, -, /, ...)
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                ClassDefinition currentClass) throws ContextualError {
        Type t1 = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type t2 = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        Type resType = ContextTools.typeArithOp(compiler, t1, t2, getLocation());
        if (resType.isFloat()) {
            if (getLeftOperand().getType().isInt()) {
                setLeftOperand(new ConvFloat(getLeftOperand()));
                getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
            } else if (getRightOperand().getType().isInt()) {
                setRightOperand(new ConvFloat(getRightOperand()));
                getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            }
        }
        setType(resType);
        return resType;
    }

    protected void codeGenInst(DecacCompiler compiler){
        super.codeGenInst(compiler);
    }

    protected void codeGenPrint(DecacCompiler compiler){
        codeGenInst(compiler);
        super.codeGenPrint(compiler);
    }

    abstract void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler);
}
