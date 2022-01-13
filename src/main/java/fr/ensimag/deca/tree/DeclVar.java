package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

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

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type currentType = type.verifyType(compiler);
        type.setDefinition(compiler.getEnvTypes().get(type.getName(), Location.BUILTIN));
        if (currentType.isNull()) {
            throw new ContextualError("(RULE 3.17) Variable cannot be void type.", type.getLocation());
        }
        initialization.verifyInitialization(compiler, currentType, localEnv, currentClass);
        try {
            localEnv.declare(varName.getName(), new VariableDefinition(currentType, varName.getLocation()));
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("(RULE 3.17) Variable has already been declared.", varName.getLocation());
        }
        varName.setDefinition(localEnv.get(varName.getName()));
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
    }

    protected void codeGenDeclVar(DecacCompiler compiler){
        compiler.getstackTable().put(varName.getName(), compiler.getListRegister().GB);
        initialization.codeGenDeclVar(compiler, varName);
        
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
