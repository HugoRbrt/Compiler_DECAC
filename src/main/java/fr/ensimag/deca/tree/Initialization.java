package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

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
        expression.verifyRValue(compiler, localEnv, currentClass, t);
    }

    protected void codeGenDeclVar(DecacCompiler compiler){/*
    //pas encore fonctionnel, codegen de abstractexpr doit etre entierement realisé avant
    //RegistreRetourExpr correspond au registre contenant la valeur calculé de expr
        GPRegister R = compiler.getListRegister().UseFirstAvailableRegister();
        expression.codeGenInst(compiler);
        RegistreRetourExpr =
        compiler.addInstruction(new LOAD(RegistreRetourExpr, R));
        compiler.addInstruction(new PUSH(R));*/
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
