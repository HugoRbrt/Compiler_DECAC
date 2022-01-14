package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public abstract class AbstractDeclField extends Tree {

    protected abstract void verifyField(DecacCompiler compiler, ClassDefinition currentClass, int counter)
            throws ContextualError;

    /**
     * Returns non-terminal AbstractInitialization
     * @return
     *      the concrete class's AbstractInitialization field
     */
    public abstract AbstractInitialization getInit();
}
