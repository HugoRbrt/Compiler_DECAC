package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclMethod extends AbstractDeclMethod {
    final private AbstractIdentifier returnType;
    final private AbstractIdentifier methodName;
    final private ListDeclParam declParameters;
    final private AbstractMethodBody block;

    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methodName,
            ListDeclParam declParameters, AbstractMethodBody block) {
        Validate.notNull(returnType);
        Validate.notNull(methodName);
        Validate.notNull(declParameters);
        this.returnType = returnType;
        this.methodName = methodName;
        this.declParameters = declParameters;
        this.block = block;
    }

    /**
     * Context check second pass. Checks return type, then checks whether the method
     * name is already in use in the current environment (illegal). If not, checks
     * whether the name is used in superclass environments. If that is the case,
     * checks whether the name is used as a field name in any environment (illegal).
     * If the name is used as a method name in a superclass environment, to
     * successfully override the new method must have the same signature and return
     * type as the existing one.
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
        ExpDefinition def = localEnv.getCurrent(m);
        if (def != null) {
            throw new ContextualError(
                    "(RULE 2.6) Method or field '" + m + "' has already been declared.",
                    methodName.getLocation());
        }
        def = localEnv.get(m);
        if (def != null && !def.isMethod()) {
            throw new ContextualError(
                    "(RULE 2.7) Illegal override of '" + m + "': field --> method.",
                    methodName.getLocation());
        }
        if (def != null && def.isMethod()) {
            MethodDefinition mdef = (MethodDefinition) def;
            Signature sigToOverride = mdef.getSignature();
            if (!(sigToOverride.equals(sig) && ContextTools.subtype(
                    currentType, mdef.getType()))) {
                throw new ContextualError(
                        "(RULE 2.7) Invalid method override. Signature of '"
                        + m + "' is (" + sigToOverride +"), return type is " +
                        mdef.getType().getName() + ".", methodName.getLocation());
            }
            counter = mdef.getIndex();
        } else {
            currentClass.incNumberOfMethods();
            counter++;
        }
        try {
            localEnv.declare(
                    m, new MethodDefinition(currentType, returnType.getLocation(), sig, counter));
        } catch (EnvironmentExp.DoubleDefException ignored) {
            //Cannot occur
        }
        methodName.setDefinition(localEnv.get(methodName.getName()));
        methodName.setType(currentType);
    }

    /**
     * Context check third pass. Creates the method body's environment by
     * combining the classEnv environment with the environment created during
     * the argument list check, and then passes the new environment to the
     * method body check.
     *
     * @param compiler
     * @param classEnv
     * @param currentClass
     * @throws ContextualError
     */
    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp classEnv, ClassDefinition currentClass)
            throws ContextualError {
        EnvironmentExp localEnv = declParameters.verifyListDeclParam(compiler, classEnv);
        block.verifyMethodBody(compiler, localEnv, currentClass, returnType.getType());
    }

    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        s.print(" ");
        methodName.decompile(s);
        declParameters.decompile(s);
        s.print(" ");
        block.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnType.prettyPrint(s, prefix, false);
        methodName.prettyPrint(s, prefix, false);
        declParameters.prettyPrint(s, prefix, false, false);
        block.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnType.iter(f);
        methodName.iter(f);
        declParameters.iter(f);
        block.iterChildren(f);
    }

    protected void codeGenTable(DecacCompiler compiler, SymbolTable.Symbol classSymbol){
        compiler.getstackTable().put(methodName.getName(), Register.GB);
        compiler.addInstruction(new LOAD(new LabelOperand(new Label(methodName.getName().getName())), Register.R0));
        compiler.addInstruction(new STORE(Register.R0, compiler.getstackTable().get(classSymbol)));
    }

    protected void codeGen(DecacCompiler compiler, String className){
        compiler.addLabel(new Label("code."+className+"."+methodName.getName().getName()));
        Label endOfMethod = new Label("fin."+className+"."+methodName.getName().getName());
        compiler.getstackTable().setEnfOfCurrentMethod(endOfMethod);
        compiler.getListRegister().useAllRegisters();
        Line tstoline;
        compiler.resetCodeAnalyzer();
        tstoline = new Line(new TSTO(0)); // creation de la ligne

        compiler.add(tstoline);
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Stack overflow")));
        }
        int counter = -3;
        for(AbstractDeclParam p : declParameters.getList()){
            compiler.getstackTable().put(p.getName() , new RegisterOffset(counter, Register.LB));
            counter--;
        }
        block.codeGenMethodBody(compiler);
        if(!returnType.getType().isVoid()){
            compiler.addInstruction(new WSTR(new ImmediateString("Error : end of the method " +className+"."+methodName.getName().getName() + " without return")));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
        compiler.addLabel(endOfMethod);
        compiler.addInstruction(new SUBSP(block.getNumberLocalVariables()));
        compiler.addInstruction(new RTS());
        tstoline.setInstruction(new TSTO(compiler.getCodeAnalyzer().getNeededStackSize())); // on recupere de codeAnalyzer le vrai nombre et on le set
        for(AbstractDeclParam p : declParameters.getList()){
            compiler.getstackTable().remove(p.getName());
        }
        //we delete every local variables which are in the method
        for( SymbolTable.Symbol s: compiler.getstackTable().getListLocalDeclVar()){
            compiler.getstackTable().remove(s);
        }
        compiler.getstackTable().ClearListLocalDeclVar();
    }
}
