package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.NullType;
import fr.ensimag.deca.context.VoidType;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");
        // A FAIRE: Appeler méthodes "verify*" de ListDeclVarSet et ListInst.
        // Vous avez le droit de changer le profil fourni pour ces méthodes
        // (mais ce n'est à priori pas nécessaire).
        declVariables.verifyListDeclVariable(compiler, compiler.getEnvExp(), null);
        insts.verifyListInst(compiler, compiler.getEnvExp(), null,
                compiler.getEnvTypes().get(compiler.getSymbTable().create("void")).getType());
        LOG.debug("verify Main: end");
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        /* Paul, le 6/1 : cette partie du code est à décommenter pour plus tard 
        compiler.addComment("Beginning of variables declaration");
        declVariables.codeListDeclVar(compiler); */
        compiler.addComment("Beginning of main instructions:");
        insts.codeGenListInst(compiler);
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
