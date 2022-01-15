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

    public StringLiteral(String value, boolean armtype) {
        if (armtype) {
            Validate.notNull(value);
            value = value.replaceAll("^\"|\"$", "");
            this.value = value;
        }
        else {
            Validate.notNull(value);
            value = value.replaceAll("^\"|\"$", "");
            this.value = value;
            RemoveBackslash();
        }
        
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
        StringType currentType = new StringType(compiler.getSymbTable().create("string"));
        setType(currentType);
        return currentType;
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        compiler.addInstruction(new WSTR(new ImmediateString(value)));
    }

    @Override
    protected void codeGenPrintARM(DecacCompiler compiler) {
        compiler.add(new ARMLine(".ascii " +"\"" +value + "\""));
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
