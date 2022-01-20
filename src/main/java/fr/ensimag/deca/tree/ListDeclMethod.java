package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

import java.util.List;

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

    protected void codeGen(DecacCompiler compiler, String className, List<AbstractDeclField> fieldsList) {
        for (AbstractDeclMethod method: getList()) {
            compiler.getstackTable().clear();
            int counter = 1;
            for (AbstractDeclField f : fieldsList){
                compiler.getstackTable().put(((DeclField)f).getFieldName().getName(), new RegisterOffset(counter, null));
                counter++;
            }
            method.codeGen(compiler, className);
        }
    }
}
