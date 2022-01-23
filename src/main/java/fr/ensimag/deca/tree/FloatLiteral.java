package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructionsARM.*;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Single precision, floating-point literal
 *
 * @author gl49
 * @date 01/01/2022
 */
public class FloatLiteral extends AbstractExpr {

    public float getValue() {
        return value;
    }

    private float value;

    public FloatLiteral(float value) {
        Validate.isTrue(!Float.isInfinite(value),
                "literal values cannot be infinite");
        Validate.isTrue(!Float.isNaN(value),
                "literal values cannot be NaN");
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        setType(compiler.getEnvTypes().get(compiler.getSymbTable().create("float")).getType());
        return compiler.getEnvTypes().get(compiler.getSymbTable().create("float")).getType();
    }

    @Override
    protected void divideCheck() {
        if (value == 0.0) {
            Warning warning = new Warning(
                    "Division by zero.", getLocation());
            warning.emit();
        }
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LOAD(new ImmediateFloat(value), Register.R1));
        if(printHex){
            compiler.addInstruction(new WFLOATX());
        }
        else {
            compiler.addInstruction(new WFLOAT());
        }
    }

    @Override
    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex) {
        Label tmplabel = new Label();
        compiler.addARMBlock(".data");
        compiler.addARMBlock(tmplabel + ": " + ".float " + value);  //label with name of variable 
        compiler.addARMBlock(".text");
        compiler.addInstruction(new ldr(ARMRegister.r0, "=" + tmplabel));
        compiler.addInstruction(new vldr(ARMRegister.s0, "[r0]"));
        compiler.addARMBlock("        vcvt.f64.f32 d0, s0");
        compiler.addInstruction(new vmov(ARMRegister.r2, ARMRegister.r3, ARMRegister.d0));
        compiler.addInstruction(new ldr(ARMRegister.r0, "=flottant"));        
        compiler.addInstruction(new bl("printf"));
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(java.lang.Float.toHexString(value));
    }

    @Override
    String prettyPrintNode() {
        return "Float (" + getValue() + ")";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    public void codeGenInst(DecacCompiler compiler){
        compiler.addInstruction(new LOAD(new ImmediateFloat(value), compiler.getListRegister().R0));
    }

    public void codeGenInstARM(DecacCompiler compiler){
        Label tmplabel = new Label();
        compiler.addARMBlock(".data");
        compiler.addARMBlock(tmplabel + ": " + ".float " + value);  //label with name of variable 
        compiler.addARMBlock(".text");
        compiler.addInstruction(new ldr(ARMRegister.r0, "=" + tmplabel));
        compiler.addInstruction(new vldr(ARMRegister.s0, "[r0]"));
        compiler.addInstruction(new vmov(ARMRegister.r0, ARMRegister.s0));
    }
      

}
