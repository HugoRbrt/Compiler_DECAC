package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.RTS;

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
            Warning warning = new Warning(
                    "Type of return statement is inconsistent with method return type.",
                    getLocation());
            warning.emit();
        }
    }

    @Override
    public boolean checkLast() { return true; }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        returnExpr.codeGenInst(compiler);
        compiler.addInstruction(new BRA(compiler.getstackTable().getEnfOfCurrentMethod()));
    }

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
        // not treated in ARM
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        returnExpr.decompile(s);
        s.print(";");
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
