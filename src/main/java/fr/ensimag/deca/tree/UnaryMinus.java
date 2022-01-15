package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.WINT;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t1 = getOperand().verifyExpr(compiler, localEnv, currentClass);
        Type resType = ContextTools.typeUnaryMinus(compiler, t1, getLocation());
        setType(resType);
        return resType;
    }

    public void codeGenOperations(GPRegister storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new OPP(storedRegister, compiler.getListRegister().R1));
        compiler.addInstruction(new LOAD(compiler.getListRegister().R1, storedRegister));
    }

    protected void codeGenInst(DecacCompiler compiler){
        super.codeGenInst(compiler);
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(compiler.getListRegister().R0, compiler.getListRegister().R1));
        compiler.addInstruction(new WINT());
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

}
