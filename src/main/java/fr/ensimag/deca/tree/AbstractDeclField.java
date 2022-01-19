package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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

    /**
     * Generate assembly code for the IMA instruction.
     *
     * @param compiler
     */
    abstract protected void codeGen(DecacCompiler compiler, int fieldCounter);
}
