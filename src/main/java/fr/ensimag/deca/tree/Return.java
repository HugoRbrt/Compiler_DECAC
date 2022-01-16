package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class Return extends AbstractInst {
    private AbstractExpr returnExpr;

    public Return(AbstractExpr returnExpr) {
        this.returnExpr = returnExpr;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType) throws ContextualError {
        Type currentType = returnExpr.verifyExpr(compiler, localEnv, currentClass);
        returnExpr.setType(currentType);
        if (!returnType.sameType(currentType)) {
            throw new ContextualError(
                    "(RULE 3.24) Return expression does not fit method signature.",
                    returnExpr.getLocation());
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    }

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnExpr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnExpr.iter(f);
    }
}
