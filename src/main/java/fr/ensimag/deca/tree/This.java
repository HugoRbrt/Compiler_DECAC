package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class This extends AbstractExpr {
    private final boolean addedByParse;

    public This(boolean addedByParse) {
        this.addedByParse = addedByParse;
    }

    public boolean getAddedByParse() { return addedByParse; }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if (currentClass == null && !addedByParse) {
            throw new ContextualError(
                    "(RULE 3.43) 'This' reference outside of class scope.", getLocation());
        }
        if (currentClass == null && addedByParse) {
            throw new ContextualError(
                    "(RULE 0.1) Method has not been declared.", getLocation());
        }
        setType(currentClass.getType());
        return currentClass.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    String prettyPrintNode() {
        return "This";
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
