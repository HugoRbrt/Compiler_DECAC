package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;

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
                "(RULE 3.65) Field or method selection applied to expression of non-class type.",
                getLocation());
        Type fieldType = selectedField.verifyExpr(
                compiler, currentType.getDefinition().getMembers(), currentClass);
        setType(fieldType);
        if (selectedField.getDefinition().isField() && selectedField.getFieldDefinition().getVisibility() == Visibility.PROTECTED) {
            if (currentClass == null) {
                throw new ContextualError(
                        "(RULE 3.66) Protected field is not visible in the current scope.",
                        selectedField.getLocation());
            }
            if (!currentClass.getType().isSubClassOf(selectedField.getFieldDefinition().getContainingClass().getType())) {
                throw new ContextualError(
                        "(RULE 3.66) Protected field is not visible in the current scope.",
                        selectedField.getLocation());
            }
            if (!currentType.isSubClassOf(currentClass.getType())) {
                    throw new ContextualError(
                            "(RULE 3.66) Selecting class not a subclass of current class.",
                            selectingClass.getLocation());
            }
        }
        return fieldType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
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


        RegisterOffset r = compiler.getstackTable().get(compiler.getSymbTable().get(((Identifier)selectingClass).getName().getName()));
        compiler.addInstruction(new LOAD(r, Register.R0));
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
}
