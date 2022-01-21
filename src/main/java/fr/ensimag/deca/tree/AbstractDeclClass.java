package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable;

/**
 * Class declaration.
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractDeclClass extends Tree {

    /**
     * Pass 1 of [SyntaxeContextuelle]. Verify that the class declaration is OK
     * without looking at its content.
     */
    protected abstract void verifyClass(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 2 of [SyntaxeContextuelle]. Verify that the class members (fields and
     * methods) are OK, without looking at method body and field initialization.
     */
    protected abstract void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Pass 3 of [SyntaxeContextuelle]. Verify that instructions and expressions
     * contained in the class are OK.
     */
    protected abstract void verifyClassBody(DecacCompiler compiler)
            throws ContextualError;

    /**
     * Generate assembly code for the IMA instruction.
     *
     * @param compiler
     */
    protected abstract void codeGenTable(DecacCompiler compiler);

    /**
     * Generate assembly code for the IMA instruction.
     *
     * @param compiler
     */
    protected abstract void codeGen(DecacCompiler compiler);

    /**
     *
     * Pass 2 of [CodeGen] return the symbol associated to the className
     *
     * @return
     */
    abstract public SymbolTable.Symbol getClassName();
}