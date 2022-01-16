package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam {
    private final AbstractIdentifier type;
    private final AbstractIdentifier paramName;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier paramName) {
        this.type = type;
        this.paramName = paramName;
    }

    @Override
    public Type verifySignature(DecacCompiler compiler) throws ContextualError {
        Type currentType = type.verifyType(compiler);
        if (currentType.isVoid()) {
            throw new ContextualError(
                    "(RULE 2.5) Parameter cannot be void type.", type.getLocation());
        }
        type.setDefinition(compiler.getEnvTypes().get(type.getName(), type.getLocation()));
        type.setType(currentType);
        return currentType;
    }

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
