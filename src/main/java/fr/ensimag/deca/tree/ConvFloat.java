package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructionsARM.*;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;

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
    public void codeGenOperationsARM(ARMRegister storedRegister, DecacCompiler compiler){
        // Nothing to do
    }


    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(compiler.getListRegister().R0, compiler.getListRegister().R1));
        if(printHex){
            compiler.addInstruction(new WFLOATX());
        }else{
            compiler.addInstruction(new WFLOAT());
        }
    }

    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex){
        codeGenInstARM(compiler);
        compiler.addInstruction(new vmov(ARMRegister.s0, ARMRegister.r0));
        compiler.addARMBlock("        vcvt.f64.f32 d0, s0");
        compiler.addInstruction(new vmov(ARMRegister.r2, ARMRegister.r3, ARMRegister.d0));
        compiler.addInstruction(new ldr(ARMRegister.r0, "=flottant"));        
        compiler.addInstruction(new bl("printf"));
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

    public void codeGenInst(DecacCompiler compiler){
        super.getOperand().codeGenInst(compiler);
        compiler.addInstruction(new FLOAT(compiler.getListRegister().R0,compiler.getListRegister().R1));
        compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
        compiler.addInstruction(new LOAD(compiler.getListRegister().R1, compiler.getListRegister().R0));
    }

    public void codeGenInstARM(DecacCompiler compiler){
        super.getOperand().codeGenInstARM(compiler);
        compiler.addInstruction(new vmov(ARMRegister.s0, ARMRegister.r0));
        compiler.addARMBlock("        vcvt.f32.s32 s1, s0");
        compiler.addInstruction(new vmov(ARMRegister.r0, ARMRegister.s1));
    }

}
