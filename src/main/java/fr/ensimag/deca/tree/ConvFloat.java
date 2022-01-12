package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.DecacCompiler;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl49
 * @date 01/01/2022
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        setType(compiler.getEnvTypes().get(compiler.getSymbTable().create("float")).getType());
        return compiler.getEnvTypes().get(compiler.getSymbTable().create("float")).getType();
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

    public void codeGenInst(DecacCompiler compiler){
        IntLiteral operandInt = (IntLiteral)super.getOperand();
        compiler.addInstruction(new FLOAT(new ImmediateInteger(operandInt.getValue()),compiler.getListRegister().R0));
    }

}
