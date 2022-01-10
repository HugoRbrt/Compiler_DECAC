package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.svc;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMLine;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.AbstractLine;
import java.util.LinkedList;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * String literal
 *
 * @author gl49
 * @date 01/01/2022
 */
public class StringLiteral extends AbstractStringLiteral {
    private static int i;

    @Override
    public String getValue() {
        return value;
    }

    private String value;

    public StringLiteral(String value) {
        Validate.notNull(value);
        value = value.replaceAll("^\"|\"$", "");
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        setType(compiler.getEnvTypes().get(compiler.getSymbTable().create("string"), Location.BUILTIN).getType());
        return compiler.getEnvTypes().get(compiler.getSymbTable().create("string"), Location.BUILTIN).getType();
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    @Override
    protected void codeGenPrintARM(DecacCompiler compiler) {
        String msgName = "msg"+i;
        String lenMsgName = "len"+i;
        i++;
        ARMRegister R = (ARMRegister) compiler.getListRegister();
        compiler.addInstruction(new ldr(R.ARMUseFirstAvailableRegister(), "="+msgName));
        compiler.addInstruction(new ldr(R.ARMUseFirstAvailableRegister(), "="+lenMsgName));
        //we add instruction at the end of the file :
        LinkedList<AbstractLine> l = new LinkedList<AbstractLine>();
        l.add(new ARMLine(msgName+":"));
        l.add(new ARMLine(".ascii " +"\"" +value + "\""));
        l.add(new ARMLine(lenMsgName+" = . - "+msgName));
        compiler.addListInstruction(l);
        //end of the list at the end of the file
        compiler.addInstruction(new mov(R.ARMUseSpecificRegister(7),4));
        compiler.addInstruction(new svc(0));
        R.ARMreleaseRegister();
        R.ARMreleaseRegister();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("\"" + getValue() + "\"");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    
    @Override
    String prettyPrintNode() {
        return "StringLiteral (" + value + ")";
    }

    public String toString() {
        return prettyPrintNode();
    }

}
