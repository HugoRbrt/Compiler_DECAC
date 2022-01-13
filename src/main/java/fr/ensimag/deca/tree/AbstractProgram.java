package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;

/**
 * Entry point for contextual verifications and code generation from outside the package.
 * 
 * @author gl49
 * @date 01/01/2022
 *
 */
public abstract class AbstractProgram extends Tree {
    public abstract void verifyProgram(DecacCompiler compiler) throws ContextualError;
    public abstract void codeGenProgram(DecacCompiler compiler) ;
    public abstract void codeGenProgramARM(DecacCompiler compiler) ;
    public abstract void addTstoCheck(int d1, int d2, DecacCompiler compiler) ;

}
