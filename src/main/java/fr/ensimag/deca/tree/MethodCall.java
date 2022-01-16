package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr {
    private AbstractExpr callingClass;  // Can be null
    private AbstractIdentifier methodName;
    private ListExpr methodArgs;

    public MethodCall(AbstractExpr callingClass, AbstractIdentifier methodName, ListExpr methodArgs) {
        Validate.notNull(methodName);
        Validate.notNull(methodArgs);
        this.callingClass = callingClass;
        this.methodName = methodName;
        this.methodArgs = methodArgs;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        return null;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        callingClass.prettyPrint(s, prefix, false);
        methodName.prettyPrint(s, prefix, false);
        methodArgs.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        callingClass.iter(f);
        methodName.iter(f);
        methodArgs.iter(f);
    }
}
