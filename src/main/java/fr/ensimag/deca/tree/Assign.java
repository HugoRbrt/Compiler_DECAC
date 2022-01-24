package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.instructionsARM.str;

import java.util.Objects;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type currentType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        setType(currentType);
        setRightOperand(
                getRightOperand().verifyRValue(compiler, localEnv, currentClass, currentType));
        getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        return currentType;
    }

    /**
     * The AbstractBinaryExpr class's decompile method must be overloaded in order
     * to add a space on each side of the operator.
     *
     * @param s The stream to which the method writes.
     */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        s.print(")");
    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

    protected void codeGenInst(DecacCompiler compiler){
        RegisterOffset targetField;
        GPRegister targetObject = null;
        GPRegister storePossibleObject = null;
        //on recupere a quelle adresse est stocké l'element de gauche :
        if(super.getLeftOperand() instanceof Selection){
            targetObject = compiler.getListRegister().getRegister(compiler);
            AbstractExpr selectingClass = ((Selection) super.getLeftOperand()).getSelectingClass();
            //il manque la cas This et Cast
            if(selectingClass instanceof This || selectingClass instanceof Cast || selectingClass instanceof Selection){
                selectingClass.codeGenInst(compiler);
                compiler.addInstruction(new LOAD(Register.R0, targetObject));
            }else{
                RegisterOffset r = compiler.getstackTable().get(compiler.getSymbTable().get(((Identifier)selectingClass).getName().getName()));
                compiler.addInstruction(new LOAD(r, targetObject));
            }
            targetField = new RegisterOffset(((Selection) super.getLeftOperand()).getSelectedField().getFieldDefinition().getIndex(), targetObject);
        }
        else{
            targetField = compiler.getstackTable().get(
                    ((Identifier) super.getLeftOperand()).getName());
            if(Objects.isNull(targetField.getRegister())){
                storePossibleObject = compiler.getListRegister().getRegister(compiler);
                compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), storePossibleObject));
                targetField = new RegisterOffset(targetField.getOffset(), storePossibleObject);
            }
        }
        //we store right operand in R0
        super.getRightOperand().codeGenInst(compiler);
        //on stock right result into the left operand
        compiler.addInstruction(new STORE(Register.R0, targetField));
        if(super.getLeftOperand() instanceof Selection){
            assert targetObject != null;//defensive programming
            compiler.getListRegister().freeRegister(targetObject, compiler);
        }
        if(storePossibleObject != null){
            compiler.getListRegister().freeRegister(storePossibleObject, compiler);
        }
    }

    //codegen(left : R2, right : R0)
    public void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler){
        RegisterOffset offset = compiler.getstackTable().get(
            ((Identifier) super.getLeftOperand()).getName());
        compiler.addInstruction(new STORE(storedRegister, offset));
    }

    public void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler){
        compiler.addInstruction(new ldr(ARMRegister.r2, "="+((Identifier)getLeftOperand()).getName().getName()));
        compiler.addInstruction(new str(storedRegister, "[r2]"));
    }
}
