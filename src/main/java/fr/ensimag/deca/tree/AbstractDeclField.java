package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public abstract class AbstractDeclField extends Tree {

    protected abstract void verifyField(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, int counter)
            throws ContextualError;

    protected abstract void verifyFieldInitialization(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Returns non-terminal AbstractInitialization
     * @return
     *      the concrete class's AbstractInitialization field
     */
    public abstract AbstractInitialization getInit();
}
