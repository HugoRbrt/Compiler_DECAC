package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.INT;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;

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
        if (!ContextTools.castCompatible(compiler.getEnvTypes(), t1, t2)) {
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
        if(type.getDefinition().getType().isInt()){
            compiler.addInstruction(new INT(compiler.getListRegister().R0,compiler.getListRegister().R1));
        }
        else if(type.getDefinition().getType().isFloat()){
            compiler.addInstruction(new FLOAT(compiler.getListRegister().R0,compiler.getListRegister().R1));
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Float arithmetic overflow")));
        }
        else{
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("impossible_conversion")));
        }
        compiler.addInstruction(new LOAD(compiler.getListRegister().R1, compiler.getListRegister().R0));
    }

    public void codeGenOperations(GPRegister storedRegister, DecacCompiler compiler){
        // Nothing to do
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(compiler.getListRegister().R0, compiler.getListRegister().R1));
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
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("impossible_conversion")));
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

