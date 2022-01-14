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
        RemoveBackslash();
    }

    private void RemoveBackslash() {
        boolean found = false;
        String newstr = "";
        for (int i = 0; i < value.length(); i++) {
            if (!found && Character.compare(value.charAt(i), '\\') == 0) {
                found = true;
            }
            else {
                found = false;
                newstr += value.charAt(i);
            }
        }
        value = newstr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
            return new StringType(compiler.getSymbTable().create("string"));
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    @Override
    protected void codeGenPrintARM(DecacCompiler compiler) {
        String msgName = "msg"+i;
        String lenMsgName = "len"+i;
        i++;
        ARMRegister R = (ARMRegister) compiler.getListRegister();
        compiler.addInstruction(new mov(R.r0,1));
        compiler.addInstruction(new ldr(R.r1, "="+msgName));
        compiler.addInstruction(new ldr(R.r2, "="+lenMsgName));
        //we add instruction at the end of the file :
        LinkedList<AbstractLine> l = new LinkedList<AbstractLine>();
        compiler.add(new ARMLine(".data"));
        compiler.add(new ARMLine(msgName+":"));
        compiler.add(new ARMLine(".ascii " +"\"" +value + "\""));
        compiler.add(new ARMLine(lenMsgName+" = . - "+msgName));
        compiler.add(new ARMLine(".text"));
        compiler.addInstruction(new mov(R.r7,4));
        compiler.addInstruction(new svc(0));
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
