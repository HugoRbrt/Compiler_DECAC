package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class Selection extends AbstractLValue {
    private AbstractExpr selectingClass;
    private AbstractIdentifier selectedField;

    public Selection(AbstractExpr selectingClass, AbstractIdentifier selectedField) {
        this.selectingClass = selectingClass;
        this.selectedField = selectedField;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        selectingClass.prettyPrint(s, prefix, false);
        selectedField.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        selectingClass.iter(f);
        selectedField.iter(f);
    }
}
