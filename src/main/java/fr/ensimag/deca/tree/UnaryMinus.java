package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructionsARM.*;

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

    public void codeGenOperationsARM(ARMRegister storedRegister, DecacCompiler compiler){
        if (getType().isFloat()) {
            compiler.addInstruction(new vmov(ARMRegister.s0, storedRegister));
            compiler.addARMBlock("        vneg.f32 s0, s0");
            compiler.addInstruction(new vmov(storedRegister,ARMRegister.s0));
        } else {
            compiler.addInstruction(new neg(ARMRegister.r1, storedRegister));
            compiler.addInstruction(new mov(storedRegister, ARMRegister.r1));
        }
    }

    protected void codeGenInst(DecacCompiler compiler){
        super.codeGenInst(compiler);
    }

    protected void codeGenInstARM(DecacCompiler compiler){
        super.codeGenInstARM(compiler);
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(compiler.getListRegister().R0, compiler.getListRegister().R1));
        if (super.getType().isFloat()) {
            compiler.addInstruction(new WFLOAT());
        }
        else {
            compiler.addInstruction(new WINT());
        }
        
    }

    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex){
        codeGenInstARM(compiler);

        if (super.getType().isFloat()) {
            compiler.addInstruction(new vmov(ARMRegister.s0, ARMRegister.r0));
            compiler.addARMBlock("        vcvt.f64.f32 d0, s0");
            compiler.addInstruction(new vmov(ARMRegister.r2, ARMRegister.r3, ARMRegister.d0));
            compiler.addInstruction(new ldr(ARMRegister.r0, "=flottant"));
            compiler.addInstruction(new bl("printf"));
        }
        else {
            compiler.addInstruction(new mov(ARMRegister.r1, ARMRegister.r0));
            compiler.addInstruction(new ldr(ARMRegister.r0, "=int"));
            compiler.addInstruction(new bl("printf"));
        }

    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

}
