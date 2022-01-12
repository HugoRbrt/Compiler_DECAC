package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;

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
        boolean oneRegisterAvailable = compiler.getListRegister().OneRegisterAvailable();
        GPRegister usedRegister;
        //calcul de l'operand gauche
        leftOperand.codeGenInst(compiler);
        //on recupère un registre dont on aura besoin plus tard
        if(!oneRegisterAvailable){
            //si aucun registre dispo au debut: on recupere R2 avec push(R2)
            usedRegister = compiler.getListRegister().StoreRegister(2, compiler);
        }else{
            usedRegister = compiler.getListRegister().UseFirstAvailableRegister();
        }
        //on sauvegarde le resultat dans le registre disponible alloué
        compiler.addInstruction(new LOAD(compiler.getListRegister().R0, usedRegister));
        rightOperand.codeGenInst(compiler);
        //il ne manque plus qu'a faire l'operation entre les deux et liberer usedRegister
        this.codeGenOperations(usedRegister, compiler.getListRegister().R0, compiler);
        //on libère usedRegister
        if(!oneRegisterAvailable){
            compiler.addInstruction(new POP(usedRegister));
        }else{
            usedRegister.liberate();
        }
        compiler.addInstruction(new LOAD(compiler.getListRegister().R0, compiler.getListRegister().R1));
    }

    abstract void codeGenOperations(Register Reg1, Register storedRegister, DecacCompiler compiler);


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
