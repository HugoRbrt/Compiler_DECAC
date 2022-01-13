package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class NullLiteral extends AbstractExpr {
    public NullLiteral() {
        super();
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    String prettyPrintNode() {
        return "Null";
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // Leaf node
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // Leaf node
    }
}
