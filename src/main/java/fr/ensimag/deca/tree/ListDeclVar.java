package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructionsARM.*;
import fr.ensimag.ima.pseudocode.ARMRegister; 
import org.apache.log4j.Logger;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl49
 * @date 01/01/2022
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {
    private static final Logger LOG = Logger.getLogger(DecacCompiler.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclVar decl: this.getList()) {
            decl.decompile(s);
            s.println(";");
        }
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        for (AbstractDeclVar decl : getList()) {
            decl.verifyDeclVar(compiler, localEnv, currentClass);
        }
    }

    public void codeGenListDeclVar(DecacCompiler compiler) {
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVar(compiler);
        }
    }

    public void codeGenListDeclVarARM(DecacCompiler compiler) {
        compiler.addARMBlock(".data");
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVarAllocARM(compiler);
        }

        compiler.addARMBlock(".text");

        compiler.addARMBlock("_varDeclAssign:");
        compiler.addInstruction(new push(ARMRegister.lr));
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVarARM(compiler);
        }
        compiler.addInstruction(new pop(ARMRegister.pc));

    }


}
