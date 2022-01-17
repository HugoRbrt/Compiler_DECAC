package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Label;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclMethod extends AbstractDeclMethod {
    final private AbstractIdentifier returnType;
    final private AbstractIdentifier methodName;
    final private ListDeclParam declParameters;
    final private Main block;

    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methodName,
            ListDeclParam declParameters, Main block) {
        Validate.notNull(returnType);
        Validate.notNull(methodName);
        Validate.notNull(declParameters);
        this.returnType = returnType;
        this.methodName = methodName;
        this.declParameters = declParameters;
        this.block = block;
    }

    /**
     * Context check second pass. Checks return type, then checks if the method
     * name is already in use. If that is the case, checks whether the name is
     * used as a field name in any environment (illegal), or used as a method
     * name in the current environment (illegal). If the name is used as a method
     * name in a superclass environment, to successfully override the new method
     * must have the same signature and return type as the existing one.
     *
     * @param compiler
     * @param localEnv
     * @param currentClass
     * @param counter
     *          Counter to index the methods; counts from number of inherited
     * methods. If the method overrides a superclass method, the counter takes
     * the superclass method's index number, otherwise it is incremented by
     * one and this value provides the index number.
     * @throws ContextualError
     */
    @Override
    protected void verifyMethod(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, int counter) throws ContextualError {
        Type currentType = returnType.verifyType(compiler);
        returnType.setDefinition(compiler.getEnvTypes().get(returnType.getName(),
                returnType.getLocation()));
        returnType.setType(currentType);
        Signature sig = declParameters.verifySignature(compiler);
        SymbolTable.Symbol m = methodName.getName();
        ExpDefinition def = localEnv.get(m);
        if (def != null && !def.isMethod()) {
            throw new ContextualError(
                    "(RULE 2.7) Illegal override: field --> method.",
                    methodName.getLocation());
        }
        if (def != null && def.isMethod()) {
            MethodDefinition mdef = (MethodDefinition) def;
            if (!(mdef.getSignature().equals(sig) && ContextTools.subtype(
                    compiler.getEnvTypes(), currentType, mdef.getType()))) {
                throw new ContextualError(
                        "(RULE 2.7) Invalid method override.", methodName.getLocation());
            }
            counter = mdef.getIndex();
        } else {
            currentClass.incNumberOfMethods();
            counter++;
        }
        try {
            localEnv.declare(
                    m, new MethodDefinition(currentType, returnType.getLocation(), sig, counter));
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("(RULE 3.17) Method has already been declared.",
                    methodName.getLocation());
        }
        methodName.setDefinition(localEnv.get(methodName.getName()));
        methodName.setType(currentType);
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp classEnv, ClassDefinition currentClass)
            throws ContextualError {
        EnvironmentExp localEnv = declParameters.verifyListDeclParam(compiler, classEnv);
        block.verifyMain(compiler, localEnv, currentClass, returnType.getType());
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

    protected void codeGen(DecacCompiler compiler, String className){
        compiler.addLabel(new Label(className+"."+methodName.getName().getName()));
        //il faudra ajouter dans la pile une case mémoire correspondant à la méthde ajouté
        //TODO
    }
}
