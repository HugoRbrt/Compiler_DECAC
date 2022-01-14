package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

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
}
