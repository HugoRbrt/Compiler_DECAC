package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ARMLine;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ARMRegister;

import java.io.PrintStream;

/**
 * Integer literal
 *
 * @author gl49
 * @date 01/01/2022
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        setType(compiler.getEnvTypes().get(compiler.getSymbTable().create("int"), Location.BUILTIN).getType());
        return compiler.getEnvTypes().get(compiler.getSymbTable().create("int"), Location.BUILTIN).getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new LOAD(new ImmediateInteger(value),compiler.getListRegister().R1));
        compiler.addInstruction(new WINT());
    }


    @Override
    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex) {
        compiler.add(new ARMLine(".ascii " +"\"" + value + "\""));
    }

    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
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
        compiler.addInstruction(new LOAD(new ImmediateInteger(value), compiler.getListRegister().R0));
    }

    public void codeGenInstARM(DecacCompiler compiler){
        compiler.getListRegisterARM();
        compiler.addInstruction(new mov(ARMRegister.getR(0), value));
    }

}
