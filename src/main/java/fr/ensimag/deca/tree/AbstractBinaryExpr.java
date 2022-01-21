package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMGPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructionsARM.*;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.deca.tree.FloatLiteral;
import fr.ensimag.deca.tree.IntLiteral;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;


/**
 * Binary expressions.
 *
 * @author gl49
 * @date 01/01/2022
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    protected void codeGenInst(DecacCompiler compiler){
        if(rightOperand instanceof StringLiteral){
            compiler.getIdentMap().setIdentString(((AbstractIdentifier)leftOperand).getName(), ((StringLiteral)rightOperand).getValue());
        }else{
            leftOperand.codeGenInst(compiler);
            GPRegister usedRegister = compiler.getListRegister().getRegister(compiler);
            compiler.addInstruction(new LOAD(compiler.getListRegister().R0, usedRegister));
            rightOperand.codeGenInst(compiler);
            this.codeGenOperations(usedRegister, compiler.getListRegister().R0, compiler);
            compiler.getListRegister().freeRegister(usedRegister, compiler);
        }
    }

    protected void codeGenInstARM(DecacCompiler compiler){
        leftOperand.codeGenInstARM(compiler);
        ARMGPRegister usedRegister = ARMRegister.getR(1);
        compiler.addInstruction(new mov(usedRegister, ARMRegister.getR(0)));
        rightOperand.codeGenInstARM(compiler);
        this.codeGenOperationsARM(usedRegister, ARMRegister.getR(0), compiler);
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        if(rightOperand instanceof StringLiteral){
            compiler.getIdentMap().setIdentString(((AbstractIdentifier)leftOperand).getName(), ((StringLiteral)rightOperand).getValue());
        }else{
            codeGenInst(compiler);
            compiler.addInstruction(new LOAD(compiler.getListRegister().R0, compiler.getListRegister().R1));
            if(getType().isInt()){
                compiler.addInstruction(new WINT());
            }
            else if(getType().isFloat()) {
                if (printHex) {
                    compiler.addInstruction(new WFLOATX());
                } else {
                    compiler.addInstruction(new WFLOAT());
                }
            }
        }
    }

    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex){
        if(rightOperand instanceof StringLiteral){
            compiler.getIdentMap().setIdentString(((AbstractIdentifier)leftOperand).getName(), ((StringLiteral)rightOperand).getValue());
        }else{
            codeGenInstARM(compiler);
            if(getType().isInt()) {
                compiler.addInstruction(new mov(ARMRegister.r1, ARMRegister.r0));
                compiler.addInstruction(new ldr(ARMRegister.r0, "=int"));  
            }
            else {
                compiler.addInstruction(new vmov(ARMRegister.s0, ARMRegister.r0));
                compiler.addARMBlock("        vcvt.f64.f32 d0, s0");
                compiler.addInstruction(new vmov(ARMRegister.r2, ARMRegister.r3, ARMRegister.d0));
                compiler.addInstruction(new ldr(ARMRegister.r0, "=flottant"));  
            }
            compiler.addInstruction(new bl("printf"));
        }
    }

    abstract void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler);

    abstract void codeGenOperationsARM(ARMRegister Reg1, ARMRegister storedRegister, DecacCompiler compiler);

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(getOperatorName());
        getRightOperand().decompile(s);
        s.print(")");
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }

}
