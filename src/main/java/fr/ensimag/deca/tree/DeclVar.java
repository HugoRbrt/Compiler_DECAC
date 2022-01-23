package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.deca.tree.Initialization;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.instructions.PUSH;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    public AbstractInitialization getInit() { return initialization; }

    /**
     * Context check third pass: checking variable declarations in method
     * bodies and the main program. Checks the type of the declaration,
     * whether the initialization is compatible with the variable being
     * declared, and whether the variable has already been declared.
     *
     * @param compiler contains "env_types" attribute
     * @param localEnv
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to
     *      the synthetized attribute
     * @param currentClass
     * @throws ContextualError
     */
    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type currentType = type.verifyType(compiler);
        type.setDefinition(compiler.getEnvTypes().get(type.getName(), type.getLocation()));
        type.setType(currentType);
        if (currentType.isVoid()) {
            throw new ContextualError("(RULE 3.17) Variable cannot be of type void: \u001B[31mvoid\u001B[0m " +
                    varName.getName(), type.getLocation());
        }
        initialization.verifyInitialization(compiler, currentType, localEnv, currentClass);
        try {
            localEnv.declare(varName.getName(), new VariableDefinition(currentType, varName.getLocation()));
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("(RULE 3.17) Variable has already been declared.", varName.getLocation());
        }
        varName.setDefinition(localEnv.get(varName.getName()));
        varName.setType(currentType);
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
    }

    protected void codeGenDeclVar(DecacCompiler compiler){
        compiler.getstackTable().putDeclVar(varName.getName(), Register.GB);
        initialization.codeGenDeclVar(compiler, varName);
    }

    protected void codeGenDeclLocalVar(DecacCompiler compiler, int indexOfDeclVar){
        compiler.getstackTable().putLocalDeclVar(varName.getName(), new RegisterOffset(indexOfDeclVar, Register.LB));
        initialization.codeGenDeclVar(compiler, varName);
    }

    protected void codeGenDeclVarARM(DecacCompiler compiler){
        initialization.codeGenDeclVarARM(compiler, varName);
    }
    
    /*
     * Allocate place in ARM memory
     */
    protected void codeGenDeclVarAllocARM(DecacCompiler compiler){
        if(!varName.getDefinition().getType().isString()){//si on veut declarer un string, il faut juste creer le symbol en java
            if (varName.getDefinition().getType().isBoolean()) {
                compiler.addARMBlock(varName.getName().getName() + ": " + ".int 0");  //label with name of variable   
            }
            if (varName.getDefinition().getType().isInt()) {
                compiler.addARMBlock(varName.getName().getName() + ": " + ".int 0");  //label with name of variable   
            }
            if (varName.getDefinition().getType().isFloat()) {
                compiler.addARMBlock(varName.getName().getName() + ": " + ".float 0");  //label with name of variable   
            }
        }    
    }



    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
