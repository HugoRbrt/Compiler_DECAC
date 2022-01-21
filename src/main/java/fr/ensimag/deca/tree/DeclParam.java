package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;

import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam {
    private final AbstractIdentifier type;
    private final AbstractIdentifier paramName;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier paramName) {
        this.type = type;
        this.paramName = paramName;
    }

    public SymbolTable.Symbol getName(){
        return paramName.getName();
    }

    /**
     * Context check second pass. Checks whether a method parameter's type
     * exists and is not void.
     *
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifySignature(DecacCompiler compiler) throws ContextualError {
        Type currentType = type.verifyType(compiler);
        if (currentType.isVoid()) {
            throw new ContextualError(
                    "(RULE 2.9) Parameter cannot be of type void: \u001B[31mvoid\u001B[0m " +
                    paramName.getName(), type.getLocation());
        }
        type.setDefinition(compiler.getEnvTypes().get(type.getName(), type.getLocation()));
        type.setType(currentType);
        return currentType;
    }

    /**
     * Context check third pass. The parameter is known to be correct (that
     * check was done on the second pass), but the compiler must also check
     * whether the parameter has already been declared within the argument
     * list.
     *
     * @param compiler
     * @param localEnv
     * @throws ContextualError
     */
    @Override
    protected void verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError {
        Type currentType = type.getType();
        try {
            localEnv.declare(
                    paramName.getName(), new ParamDefinition(currentType,
                    paramName.getLocation()));
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError(
                    "(RULE 3.17) Parameter has already been declared.",
                    paramName.getLocation());
        }
        paramName.setDefinition(localEnv.get(paramName.getName()));
        paramName.setType(currentType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        paramName.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        paramName.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        paramName.iter(f);
    }
}
