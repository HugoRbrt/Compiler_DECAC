package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclParam extends TreeList<AbstractDeclParam> {

    public Signature verifySignature(DecacCompiler compiler) throws ContextualError {
        Signature sig = new Signature();
        for (AbstractDeclParam p: this.getList()) {
            sig.add(p.verifySignature(compiler));
        }
        return sig;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    }
}
