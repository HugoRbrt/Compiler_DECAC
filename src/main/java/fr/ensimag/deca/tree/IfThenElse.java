package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.instructionsARM.b;
import fr.ensimag.ima.pseudocode.instructionsARM.bne;
import fr.ensimag.ima.pseudocode.instructionsARM.cmp;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl49
 * @date 01/01/2022
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        condition.verifyExpr(compiler, localEnv, currentClass);
        condition.verifyCondition(compiler, localEnv, currentClass);
        thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Label beginElse = new Label();
        Label endElse = new Label();

        condition.codeGenInst(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), compiler.getListRegister().R0));
        compiler.addInstruction(new BNE(beginElse));
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(endElse));
        compiler.addLabel(beginElse);
        elseBranch.codeGenListInst(compiler);
        compiler.addLabel(endElse);
    }

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
        Label beginElse = new Label();
        Label endElse = new Label();

        condition.codeGenInstARM(compiler);
        compiler.addInstruction(new cmp(ARMRegister.r0, 1));
        compiler.addInstruction(new bne(beginElse.toString()));
        thenBranch.codeGenListInstARM(compiler);
        compiler.addInstruction(new b(endElse.toString()));
        compiler.addLabel(beginElse);
        elseBranch.codeGenListInstARM(compiler);
        compiler.addLabel(endElse);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if ");
        condition.decompile(s);
        s.println(" {");
        s.indent();
        thenBranch.decompile(s);
        s.unindent();
        s.println("} else {");
        s.indent();
        elseBranch.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
