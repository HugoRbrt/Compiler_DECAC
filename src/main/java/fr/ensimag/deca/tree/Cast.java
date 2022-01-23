package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.instructionsARM.*;

import java.io.PrintStream;

public class Cast extends AbstractExpr {
    private AbstractIdentifier type;
    private AbstractExpr expression;

    public Cast(AbstractIdentifier type, AbstractExpr expression) {
        this.type = type;
        this.expression = expression;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t1 = type.verifyType(compiler);
        type.setDefinition(compiler.getEnvTypes().get(type.getName()));
        Type t2 = expression.verifyExpr(compiler, localEnv, currentClass);
        expression.setType(t2);
        if (!ContextTools.castCompatible(t2, t1)) {
            throw new ContextualError("(RULE 3.39) Illegal cast.", getLocation());
        }
        setType(t1);
        return t1;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        type.decompile(s);
        s.print(") ");
        s.print("(");
        expression.decompile(s);
        s.print(")");
    }

    protected void codeGenInst(DecacCompiler compiler){
        expression.codeGenInst(compiler);
        // we only add the instruction if the cast is really needed
        if (!type.getDefinition().getType().sameType(expression.getType())) {
            if(type.getDefinition().getType().isInt()){
                compiler.addInstruction(new INT(Register.R0, Register.R1));
                if (!compiler.getCompilerOptions().getNoCheck()) {
                    compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
                }     
            }
            else if(type.getDefinition().getType().isClass()){
                Label beginElse = new Label();
                Label endElse = new Label();

                //condition instructions
                InstanceOf instance = new InstanceOf(expression, type);
                instance.codeGenInst(compiler);
                compiler.addInstruction(new CMP(new ImmediateInteger(1), Register.R0));
                compiler.addInstruction(new BNE(beginElse));
                //then instructions§
                if(expression instanceof Identifier){
                    compiler.addInstruction(new LOAD(compiler.getstackTable().get(((Identifier)expression).getName()), Register.R1));
                }else{//if expression is not an identifier, the result of expression is store in R0
                    expression.codeGenInst(compiler);
                    compiler.addInstruction(new LOAD(Register.R0, Register.R1));
                }
                compiler.addInstruction(new BRA(endElse));
                compiler.addLabel(beginElse);
                //else instructions
                if (!compiler.getCompilerOptions().getNoCheck()) {
                    compiler.addInstruction(new BRA(compiler.getErrorManager().getErrorLabel("impossible_conversion")));
                }
                compiler.addLabel(endElse);
            }
            else if(type.getDefinition().getType().isFloat()){
                compiler.addInstruction(new FLOAT(Register.R0, Register.R1));
                if (!compiler.getCompilerOptions().getNoCheck()) {
                    compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
                }
            }
            compiler.addInstruction(new LOAD(Register.R1, Register.R0));
        }
    }

    protected void codeGenInstARM(DecacCompiler compiler){
        expression.codeGenInstARM(compiler);
        // we only add the instruction if the cast is really needed
        if (!type.getDefinition().getType().sameType(expression.getType())) {
            compiler.addInstruction(new vmov(ARMRegister.s0, ARMRegister.r0));
            if(type.getDefinition().getType().isInt()){
                compiler.addARMBlock("        vcvt.s32.f32 s1, s0");
                if (!compiler.getCompilerOptions().getNoCheck()) {
                    compiler.addInstruction(new bvs("float_arithmetic_overflow"));
                }
            }
            else if(type.getDefinition().getType().isFloat()){
                compiler.addARMBlock("        vcvt.f32.s32 s1, s0");
                if (!compiler.getCompilerOptions().getNoCheck()) {
                   compiler.addInstruction(new bvs("float_arithmetic_overflow"));
                }
            }
            compiler.addInstruction(new vmov(ARMRegister.r0, ARMRegister.s1));
        }
    }

    public void codeGenOperations(GPRegister storedRegister, DecacCompiler compiler){
        // Nothing to do
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(Register.R0, Register.R1));
        if(type.getDefinition().getType().isInt()){
            compiler.addInstruction(new WINT());
        }
        else if(type.getDefinition().getType().isFloat()){
            if(printHex){
                compiler.addInstruction(new WFLOATX());
            }else{
                compiler.addInstruction(new WFLOAT());
            }
        }
        else{
            compiler.addInstruction(new BRA(compiler.getErrorManager().getErrorLabel("impossible_conversion")));
        }
    }

    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex){
        codeGenInstARM(compiler);
        compiler.addInstruction(new LOAD(Register.R0, Register.R1));
        if(type.getDefinition().getType().isInt()){
            compiler.addInstruction(new mov(ARMRegister.r1, ARMRegister.r0));
            compiler.addInstruction(new ldr(ARMRegister.r0, "=int"));
            compiler.addInstruction(new bl("printf"));
        }
        else if(type.getDefinition().getType().isFloat()){
            compiler.addInstruction(new vmov(ARMRegister.s0, ARMRegister.r0));
            compiler.addARMBlock("        vcvt.f64.f32 d0, s0");
            compiler.addInstruction(new vmov(ARMRegister.r2, ARMRegister.r3, ARMRegister.d0));
            compiler.addInstruction(new ldr(ARMRegister.r0, "=flottant"));
            compiler.addInstruction(new bl("printf"));
        }
        else{
            compiler.addInstruction(new b("impossible_conversion"));
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        expression.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        expression.iter(f);
    }
}

