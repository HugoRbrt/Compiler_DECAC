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

    protected void codeGen(DecacCompiler compiler, String className, List<AbstractDeclField> fieldsList,  List<AbstractDeclClass> list) {
        compiler.getstackTable().clear();
        //il faut aussi ajouter toutes les methodes et toutes les classes :

        for (AbstractDeclClass decl: list) {
            //we add every class symbol
            compiler.getstackTable().put(decl.getClassName().getName(), Register.GB);
            //we add every method symbol of every class
            for(AbstractDeclMethod method : ((DeclClass)decl).getMethods().getList()){

                SymbolTable.Symbol[] symbolList = new SymbolTable.Symbol[decl.getClassName().getClassDefinition().getNumberOfMethods()];
                decl.getClassName().getClassDefinition().getMembers().getSymbolMethod(new SymbolTable(), symbolList, decl.getClassName().getClassDefinition());

                for(SymbolTable.Symbol symbol : symbolList){
                    symbol.setName(symbol.getName());
                    compiler.getstackTable().put(symbol, Register.GB);
                }
            }
        }


        for (AbstractDeclMethod method: getList()) {
            int counter = 1;
            for (AbstractDeclField f : fieldsList){
                compiler.getstackTable().put(((DeclField)f).getFieldName().getName(), new RegisterOffset(counter, null));
                counter++;
            }
            method.codeGen(compiler, className);
        }
    }
}
