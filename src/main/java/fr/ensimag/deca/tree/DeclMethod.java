package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclMethod extends AbstractDeclMethod {
    final private AbstractIdentifier returnType;
    final private AbstractIdentifier methodName;
    final private ListDeclParam declParameters;
    final private Main block;

    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methodName, ListDeclParam declParameters, Main block) {
        Validate.notNull(returnType);
        Validate.notNull(methodName);
        Validate.notNull(declParameters);
        this.returnType = returnType;
        this.methodName = methodName;
        this.declParameters = declParameters;
        this.block = block;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        methodName.prettyPrint(s, prefix, false);
        declParameters.prettyPrint(s, prefix, false, false);
        block.prettyPrintChildren(s, prefix);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        methodName.iter(f);
        declParameters.iter(f);
        block.iterChildren(f);
    }
}
