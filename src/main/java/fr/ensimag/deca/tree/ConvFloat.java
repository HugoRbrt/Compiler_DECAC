package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;

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

    public void codeGenOperations(GPRegister storedRegister, DecacCompiler compiler){
        // Nothing to do
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

    public void codeGenInst(DecacCompiler compiler){
        super.getOperand().codeGenInst(compiler);
        compiler.addInstruction(new FLOAT(compiler.getListRegister().R0,compiler.getListRegister().R1));
        compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("float_arithmetic")));
        compiler.addInstruction(new LOAD(compiler.getListRegister().R1, compiler.getListRegister().R0));
    }

}
