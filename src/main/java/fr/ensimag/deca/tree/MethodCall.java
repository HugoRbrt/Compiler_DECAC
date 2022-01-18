package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class MethodCall extends AbstractExpr {
    private AbstractExpr callingClass;  //Can be This
    private AbstractIdentifier methodName;
    private ListExpr methodArgs;

    public MethodCall(AbstractExpr callingClass, AbstractIdentifier methodName, ListExpr methodArgs) {
        Validate.notNull(callingClass);
        Validate.notNull(methodName);
        Validate.notNull(methodArgs);
        this.callingClass = callingClass;
        this.methodName = methodName;
        this.methodArgs = methodArgs;
    }

    /**
     * Context check for a method call. Checks that the caller is a class,
     * either 'This' in a class scope or a class reference. Checks that the
     * method name exists in the class environment, then checks whether the
     * name is mapped to an actual method, as opposed to a field.
     * Finally, compares the signature to the method definition's signature.
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return
     * @throws ContextualError
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        ClassType currentType = callingClass.verifyExpr(compiler, localEnv, currentClass).asClassType(
                "(RULE 3.71) Field or method selection applied to expression of non-class type.",
                getLocation());
        EnvironmentExp classEnv = currentType.getDefinition().getMembers();
        Type returnType = methodName.verifyExpr(compiler, classEnv, currentClass);
        if (!classEnv.get(methodName.getName()).isMethod()) {
            throw new ContextualError(
                    "(RULE 3.41) Invalid method call.", getLocation());
        }
        Signature sig = new Signature();
        for (AbstractExpr arg: methodArgs.getList()) {
            sig.add(arg.verifyExpr(compiler, localEnv, currentClass));
        }
        MethodDefinition mdef = methodName.getMethodDefinition();
        if (!mdef.getSignature().equals(sig)) {
            throw new ContextualError("(RULE 3.72) Invalid parameter list.", getLocation());
        }
        setType(returnType);
        return returnType;
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
