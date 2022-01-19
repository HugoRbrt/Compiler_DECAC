package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;

public class ListDeclMethod extends TreeList<AbstractDeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod decl: this.getList()) {
            decl.decompile(s);
        }
    }

    protected void codeGenTable(DecacCompiler compiler, SymbolTable.Symbol classSymbol) {
        //on ajoute la classe de object :
        for (AbstractDeclMethod method: getList()) {
            method.codeGenTable(compiler, classSymbol);
        }
    }

    protected void codeGen(DecacCompiler compiler, String className) {
        //il faut ajouter à la pile GB une case memoire pour chaque methode en plus de des méthodes de la superclasse
        for (AbstractDeclMethod method: getList()) {
            method.codeGen(compiler, className);
        }
    }
}
