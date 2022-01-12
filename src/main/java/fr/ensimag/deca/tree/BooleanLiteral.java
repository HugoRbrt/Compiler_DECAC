package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.deca.tree.StringLiteral;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import java.io.PrintStream;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        setType(compiler.getEnvTypes().get(compiler.getSymbTable().create("boolean")).getType());
        return compiler.getEnvTypes().get(compiler.getSymbTable().create("boolean")).getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        StringLiteral s;
        if(value){
            s = new StringLiteral("true");
        }else{
            s = new StringLiteral("false");
        }
        s.codeGenPrint(compiler, printHex);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

}
