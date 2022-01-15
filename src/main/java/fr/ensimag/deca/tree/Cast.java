package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

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
