package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.log4j.Logger;

import java.io.PrintStream;

public class ListDeclField extends TreeList<AbstractDeclField> {
    private static final Logger LOG = Logger.getLogger(DecacCompiler.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField decl: this.getList()) {
            decl.decompile(s);
            s.println(";");
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        int count = getList().size();
        for (AbstractDeclField f : getList()) {
            f.prettyPrint(s, prefix, count == 1, true);
            count--;
        }
    }

    protected void codeGen(DecacCompiler compiler, AbstractIdentifier className,AbstractIdentifier superClass) {
        compiler.addLabel(new Label("init."+className.getName().getName()));
        compiler.addInstruction(new TSTO(className.getClassDefinition().getNumberOfFields()));
        compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Stack overflow , a real one")));
        //on initialise tout a 0
        for (AbstractDeclField field: getList()) {
            field.codeGen(compiler, ((DeclField)field).getFieldName().getFieldDefinition().getIndex());
        }
        // on appel la methode init de la superclasse
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
        compiler.addInstruction(new PUSH(Register.R1));
        compiler.addInstruction(new BSR(new Label("init."+superClass)));
        compiler.addInstruction(new SUBSP(1));
        //on reinitialize uniquement les champ qui ont une initialization
        for (AbstractDeclField field: getList()) {
            if(field.getInit() instanceof Initialization){
                field.codeGen(compiler, ((DeclField)field).getFieldName().getFieldDefinition().getIndex());
            }
        }
        compiler.addInstruction(new RTS());
    }
}
