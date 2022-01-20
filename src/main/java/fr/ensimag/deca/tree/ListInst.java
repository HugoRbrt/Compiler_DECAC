package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Instruction;
import fr.ensimag.ima.pseudocode.Label;
import org.apache.log4j.Logger;

import java.util.Iterator;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class ListInst extends TreeList<AbstractInst> {

    /**
     * Implements non-terminal "list_inst" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler     contains "env_types" attribute
     * @param localEnv     corresponds to "env_exp" attribute
     * @param currentClass corresponds to "class" attribute (null in the main bloc).
     * @param returnType   corresponds to "return" attribute (void in the main bloc).
     */
    public void verifyListInst(DecacCompiler compiler, EnvironmentExp localEnv,
                    ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        if (compiler.getEmitWarnings() && currentClass != null) {
            for (Iterator<AbstractInst> it = this.iterator(); it.hasNext();) {
                AbstractInst inst = it.next();
                inst.verifyInst(compiler, localEnv, currentClass, returnType);
                if (!it.hasNext() && !inst.checkLast()) {
                    Warning warning = new Warning(
                            "Last instruction of non-void method is not a return statement.",
                            inst.getLocation());
                    warning.emit();
                }
            }
        } else {
            for (AbstractInst inst : this.getList()) {
                inst.verifyInst(compiler, localEnv, currentClass, returnType);
            }
        }
    }

    public void codeGenListInst(DecacCompiler compiler) {
        for (AbstractInst i : getList()) {
            i.codeGenInst(compiler);
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractInst i : getList()) {
            i.decompileInst(s);
            s.println();
        }
    }
}
