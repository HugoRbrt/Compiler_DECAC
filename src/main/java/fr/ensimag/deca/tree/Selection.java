package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

public class Selection extends AbstractLValue {
    private AbstractExpr selectingClass;
    private AbstractIdentifier selectedField;

    public Selection(AbstractExpr selectingClass, AbstractIdentifier selectedField) {
        this.selectingClass = selectingClass;
        this.selectedField = selectedField;
    }

    public AbstractIdentifier getSelectedField() {
        return selectedField;
    }

    public AbstractExpr getSelectingClass() {
        return selectingClass;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        ClassType currentType = selectingClass.verifyExpr(compiler, localEnv, currentClass).asClassType(
                "(RULE 3.65) Field selection applied to expression of non-class type: (\u001B[31m" +
                selectingClass.getType().toString() + "\u001B[0m)." + selectedField.toString(),
                getLocation());
        Type fieldType = selectedField.verifyExpr(
                compiler, currentType.getDefinition().getMembers(), currentClass);
        setType(fieldType);
        if (selectedField.getDefinition().isField() &&
                selectedField.getFieldDefinition().getVisibility() == Visibility.PROTECTED) {
            ClassType classOfOrigin = selectedField.getFieldDefinition().getContainingClass().getType();
            if (currentClass == null) {
                throw new ContextualError(
                        "(RULE 3.66) Protected field is not visible in the current scope. Field '" +
                        selectedField.getName() + "' declared in class " + classOfOrigin.getName() +
                        ", current scope is main program.",  selectedField.getLocation());
            }
            if (!currentClass.getType().isSubClassOf(classOfOrigin)) {
                throw new ContextualError(
                        "(RULE 3.66) Protected field is not visible in the current scope. Field '" +
                        selectedField.getName() + "' declared in class " + classOfOrigin.getName() +
                        ", current scope is class " + currentClass.getType().getName() + ".",
                        selectedField.getLocation());
            }
            if (!currentType.isSubClassOf(currentClass.getType())) {
                    throw new ContextualError(
                            "(RULE 3.66) Selecting class is not a subclass of the current class. Selecting class is " +
                            selectingClass.getType().getName() +", current class is " + currentClass.getType().getName()
                            + ".", selectingClass.getLocation());
            }
        }
        return fieldType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        selectingClass.decompile(s);
        s.print(".");
        selectedField.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        selectingClass.prettyPrint(s, prefix, false);
        selectedField.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        selectingClass.iter(f);
        selectedField.iter(f);
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        //on recupère l'adresse de l'objet dans R0
        if(selectingClass instanceof Cast || selectingClass instanceof This || selectingClass instanceof Selection){
            selectingClass.codeGenInst(compiler);
        }else{
            RegisterOffset r = compiler.getstackTable().get(compiler.getSymbTable().get(((Identifier)selectingClass).getName().getName()));
            compiler.addInstruction(new LOAD(r, Register.R0));
        }
        compiler.addInstruction(new LOAD(new RegisterOffset( selectedField.getFieldDefinition().getIndex() , Register.R0), Register.R1));
        if(selectedField.getType().isInt()){
            compiler.addInstruction(new WINT());
        }
        else if(selectedField.getType().isFloat()){
            if(printHex){
                compiler.addInstruction(new WFLOATX());
            }
            else {
                compiler.addInstruction(new WFLOAT());
            }
        }
    }


    protected void codeGenInst(DecacCompiler compiler) {
        RegisterOffset r;
        RegisterOffset offset;
        if(selectingClass instanceof Cast || selectingClass instanceof This){
            selectingClass.codeGenInst(compiler);
            offset = new RegisterOffset(selectedField.getFieldDefinition().getIndex(), Register.R0);
        }else{
            r = compiler.getstackTable().get(compiler.getSymbTable().get(((Identifier)selectingClass).getName().getName()));
            compiler.addInstruction(new LOAD(r, Register.R1));
            offset = new RegisterOffset(selectedField.getFieldDefinition().getIndex(), Register.R1);
        }
        compiler.addInstruction(new LOAD(offset, Register.R0));
    }
}
