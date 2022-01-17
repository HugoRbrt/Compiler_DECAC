package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.log4j.Logger;

/**
 *
 * @author gl49
 * @date 01/01/2022
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        for (AbstractDeclClass decl: getList()) {
            decl.verifyClass(compiler);
        }
        LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        for (AbstractDeclClass decl: getList()) {
            decl.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        for (AbstractDeclClass decl: getList()) {
            decl.verifyClassBody(compiler);
        }
    }

    protected void codeGenTable(DecacCompiler compiler) {
        compiler.addComment(" --------------------------------------------------");
        compiler.addComment("             Construction of Method Table");
        compiler.addComment(" --------------------------------------------------");


        compiler.addComment("construction of Method Table for Object");
        compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));

        Symbol objectSymbol = compiler.getSymbTable().get("Object");
        compiler.getstackTable().put(objectSymbol, Register.GB);
        compiler.addInstruction(new STORE(Register.R0, compiler.getstackTable().get(objectSymbol)));

        Symbol equalsObjectSymbol = compiler.getSymbTable().get("equals");
        compiler.getstackTable().put(equalsObjectSymbol, Register.GB);
        compiler.addInstruction(new LOAD(new Label("code.Object."+equalsObjectSymbol.getName()), Register.R0));
        compiler.addInstruction(new STORE(Register.R0,  compiler.getstackTable().get(equalsObjectSymbol)));


        for (AbstractDeclClass decl: getList()) {
            decl.codeGenTable(compiler);
        }
    }

    protected void codeGen(DecacCompiler compiler) {
        compiler.addComment(" --------------------------------------------------");
        compiler.addComment("             Object Class");
        compiler.addComment(" --------------------------------------------------");

        compiler.addComment(" ---------- equals Method");
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.R1), Register.R1));
        compiler.addInstruction(new LOAD(new RegisterOffset(-3, Register.R1), Register.R0));
        compiler.addInstruction(new CMP(Register.R1, Register.R0));
        compiler.addInstruction(new SEQ(Register.R0));
        compiler.addInstruction(new RTS());

        for (AbstractDeclClass decl: getList()) {
            decl.codeGen(compiler);
        }
    }

}
