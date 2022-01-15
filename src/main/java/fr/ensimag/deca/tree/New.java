package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class New extends AbstractExpr {
    private AbstractIdentifier instantiation;

    public New(AbstractIdentifier instantiation) {
        this.instantiation = instantiation;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type currentType = instantiation.verifyType(compiler);
        if (!currentType.isClass()) {
            throw new ContextualError("(RULE 3.42) New instance must be of a class type.", getLocation());
        }
        instantiation.setDefinition(compiler.getEnvTypes().get(instantiation.getName(), instantiation.getLocation()));
        return currentType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        instantiation.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        instantiation.iter(f);
    }
}
