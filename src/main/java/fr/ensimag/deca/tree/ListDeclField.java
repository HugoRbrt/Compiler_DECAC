package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.RTS;

import java.io.PrintStream;

public class ListDeclField extends TreeList<AbstractDeclField> {
    @Override
    public void decompile(IndentPrintStream s) {}

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        int count = getList().size();
        for (AbstractDeclField f : getList()) {
            f.prettyPrint(s, prefix, count == 1, true);
            count--;
        }
    }

    protected void codeGen(DecacCompiler compiler, String className) {
        compiler.addLabel(new Label("init."+className));
        int fieldCounter=0;
        for (AbstractDeclField field: getList()) {
            field.codeGen(compiler, fieldCounter);
            fieldCounter++;
        }
        compiler.addInstruction(new RTS());
    }
}
