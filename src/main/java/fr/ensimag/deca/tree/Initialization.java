package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructionsARM.str;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl49
 * @date 01/01/2022
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        this.expression = expression.verifyRValue(compiler, localEnv, currentClass, t);
        expression.verifyExpr(compiler, localEnv, currentClass);
    }

    @Override
    protected void codeGenDeclVar(DecacCompiler compiler, AbstractIdentifier varName){
    //pas encore fonctionnel, codegen de abstractexpr doit etre entierement realisé avant
    //RegistreRetourExpr correspond au registre contenant la valeur calculé de expr
        expression.codeGenInst(compiler);
        RegisterOffset r = compiler.getstackTable().get(varName.getName());
        compiler.addInstruction(new STORE(compiler.getListRegister().R0, r));
        compiler.incrDeclaredVariables(1);
    }

    @Override
    protected void codeGenDeclVarARM(DecacCompiler compiler, AbstractIdentifier varName){
        // ARM Code for declaration
        // peut être faudra-t-il initialiser r0 ici
        expression.codeGenInstARM(compiler); // we admit that the result will be in register r0.  
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=" + varName.getName().getName()));
        compiler.addInstruction(new str(ARMRegister.getR(0), "[r1]"));
        compiler.incrDeclaredVariables(1);
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" = ");
        getExpression().decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
}
