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
        if (returnType.isVoid()) {
            throw new ContextualError(
                    (currentClass == null ? "(RULE 3.24) 'return' statement inapplicable in main program." :
                    "(RULE 3.24) 'return' statement inapplicable in method of return type void."),
                    returnExpr.getLocation());
        }
        Type currentType = returnExpr.verifyExpr(compiler, localEnv, currentClass);
        returnExpr.setType(currentType);
        /* Not required by specification: print warning.
        */
        if (!returnType.sameType(currentType) && compiler.getEmitWarnings()) {
            StringBuilder sb = new StringBuilder(getLocation().toString());
            sb.deleteCharAt(0); sb.deleteCharAt(sb.length()-1);
            int i = sb.indexOf((", "));
            sb.setCharAt(i, ':'); sb.deleteCharAt(i+1);
            System.err.println(
                    "[\u001B[31mWARNING\u001B[0m]" + getLocation().getFilename() + " " +
                    sb + ":Type of return expression is inconsistent with method return type.");
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
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
