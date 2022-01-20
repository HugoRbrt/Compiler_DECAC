package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;
import java.util.Iterator;

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
        SymbolTable.Symbol m = methodName.getName();
        ClassType currentType = callingClass.verifyExpr(compiler, localEnv, currentClass).asClassType(
                "(RULE 3.71) Method selection applied to expression of non-class type: (\u001B[31m" +
                callingClass.getType().toString() + "\u001B[0m)." + m,  getLocation());
        EnvironmentExp classEnv = currentType.getDefinition().getMembers();
        Type returnType = methodName.verifyExpr(compiler, classEnv, currentClass);
        if (!classEnv.get(m).isMethod()) {
            throw new ContextualError(
                    "(RULE 3.41) Invalid method call.", getLocation());
        }
        Signature sig = new Signature();
        for (AbstractExpr arg: methodArgs.getList()) {
            sig.add(arg.verifyExpr(compiler, localEnv, currentClass));
        }
        MethodDefinition mdef = methodName.getMethodDefinition();
        if (!mdef.getSignature().equals(sig)) {
            throw new ContextualError(
                    "(RULE 3.72) Invalid parameter list: signature of '" +
                    m + "' is (" + mdef.getSignature() +").", getLocation());
        }
        setType(returnType);
        return returnType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        callingClass.decompile(s);
        if (!callingClass.getAddedByParse()) {
            s.print(".");
        }
        methodName.decompile(s);
        s.print('(');
        for(Iterator<AbstractExpr> it = methodArgs.iterator(); it.hasNext();) {
            AbstractExpr decl = it.next();
            decl.decompile(s);
            if (it.hasNext()) {
                s.print(", ");
            }
        }
        s.print(")");
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

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        codeGenInst(compiler);
        compiler.addInstruction(new LOAD(Register.R0, Register.R1));

        if(methodName.getType().isInt()){
            compiler.addInstruction(new WINT());
        }
        else if(methodName.getType().isFloat()){
            if(printHex){
                compiler.addInstruction(new WFLOATX());
            }
            else {
                compiler.addInstruction(new WFLOAT());
            }
        }
    }


    protected void codeGenInst(DecacCompiler compiler) {
        //we add the calling class in the stack
        Register usedRegister = compiler.getListRegister().getRegister(compiler);
        compiler.addInstruction(new ADDSP(methodArgs.size()));
        compiler.addInstruction(new LOAD(compiler.getstackTable().get(((Identifier)callingClass).getName()), usedRegister));
        compiler.addInstruction(new STORE(usedRegister, new RegisterOffset(0, Register.SP)));
        //we add arguments one by one
        int counter = 1;
        for( AbstractExpr expr : methodArgs.getList()){
            expr.codeGenInst(compiler);
            compiler.addInstruction(new LOAD(Register.R0, usedRegister));
            compiler.addInstruction(new STORE(usedRegister, new RegisterOffset(-counter, Register.SP)));
            counter++;
        }
        //we verify that the calling class is not null
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), usedRegister));
        compiler.addInstruction((new CMP(new NullOperand(), usedRegister)));
        compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("null_dereferencing")));
        //we get the list of methods for the corresponding class
        compiler.addInstruction(new LOAD(new RegisterOffset(0, usedRegister), usedRegister));
        //we call the method with the right index
        compiler.addInstruction(new BSR(new RegisterOffset(methodName.getMethodDefinition().getIndex(), usedRegister)));
        //we remove arguments from the stack
        compiler.addInstruction(new SUBSP(methodArgs.size()));
    }
}
