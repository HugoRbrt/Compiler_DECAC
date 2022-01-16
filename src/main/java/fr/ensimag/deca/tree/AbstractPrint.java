package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMLine;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.svc;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

/**
 * Print statement (print, println, ...).
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractPrint extends AbstractInst {
    private static int printCounter=0;

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType) throws ContextualError {
        for (AbstractExpr expr : arguments.getList()) {
            Type currentType = expr.verifyExpr(compiler, localEnv, currentClass);
            if (!(currentType.isInt() || currentType.isFloat() || currentType.isString())) {
                throw new ContextualError(
                        "(RULE 3.31) Cannot print " + currentType.getName() + " type.", getLocation());
            }
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        for (AbstractExpr a : getArguments().getList()) {
            a.codeGenPrint(compiler, printHex);
        }
    }

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
        for (AbstractExpr a : getArguments().getList()) {
            String msgName = "msg"+printCounter;
            String lenMsgName = "len"+printCounter;
            printCounter++;
            ARMRegister R = (ARMRegister) compiler.getListRegister();
            compiler.addInstruction(new mov(R.r0,1));
            compiler.addInstruction(new ldr(R.r1, "="+msgName));
            compiler.addInstruction(new ldr(R.r2, "="+lenMsgName));
            compiler.add(new ARMLine(".data"));
            compiler.add(new ARMLine(msgName+":"));
            a.codeGenPrintARM(compiler);
            compiler.add(new ARMLine(lenMsgName+" = . - "+msgName));
            compiler.add(new ARMLine(".text"));
            compiler.addInstruction(new mov(R.r7,4));
            compiler.addInstruction(new svc(0));
        }
    }


    private boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("print" + getSuffix() + "(");
        for (Iterator<AbstractExpr> it = arguments.getList().iterator(); it.hasNext();) {
            it.next().decompile(s);
            if (it.hasNext()) {
                s.print(", ");
            }
        }
        s.print(");");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
