package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class InstanceOf extends AbstractExpr {
    private AbstractExpr expr;
    private AbstractIdentifier comparedTo;

    public InstanceOf(AbstractExpr expr, AbstractIdentifier comparedTo) {
        this.expr = expr;
        this.comparedTo = comparedTo;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t1 = expr.verifyExpr(compiler, localEnv, currentClass);
        Type t2 = comparedTo.verifyExpr(compiler, localEnv, currentClass);
        Type resType = ContextTools.typeInstanceOf(compiler, t1, t2, getLocation());
        setType(resType);
        return resType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        comparedTo.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        comparedTo.iter(f);
    }
}
