package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.util.Iterator;

public class ListDeclParam extends TreeList<AbstractDeclParam> {

    public Signature verifySignature(DecacCompiler compiler) throws ContextualError {
        Signature sig = new Signature();
        for (AbstractDeclParam p: this.getList()) {
            sig.add(p.verifySignature(compiler));
        }
        return sig;
    }

    public EnvironmentExp verifyListDeclParam(DecacCompiler compiler, EnvironmentExp classEnv)
            throws ContextualError {
        EnvironmentExp localEnv = new EnvironmentExp(classEnv);
        for (AbstractDeclParam p: this.getList()) {
            p.verifyDeclParam(compiler, localEnv);
        }
        return localEnv;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        for(Iterator<AbstractDeclParam> it = this.iterator(); it.hasNext();) {
            AbstractDeclParam decl = it.next();
            decl.decompile(s);
            if (it.hasNext()) {
                s.print(", ");
            }
        }
        s.print(")");
    }
}
