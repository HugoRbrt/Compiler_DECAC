package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclField extends TreeList<AbstractDeclField> {
    Visibility visibility;

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public void decompile(IndentPrintStream s) {}
}
