package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Variable declaration
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractDeclVar extends Tree {
    
    /**
     * Implements non-terminal "decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the synthetized attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     */    
    protected abstract void verifyDeclVar(DecacCompiler compiler,
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
    protected abstract void codeGenDeclVar(DecacCompiler compiler);

    /**
     * Generate assembly code for the ARM instruction.
     *
     * @param compiler
     */
    protected abstract void codeGenDeclVarARM(DecacCompiler compiler);

    /**
     * Generate assembly code for the ARM instruction in order to allocate place.
     *
     * @param compiler
     */
    protected abstract void codeGenDeclVarAllocARM(DecacCompiler compiler);
}
