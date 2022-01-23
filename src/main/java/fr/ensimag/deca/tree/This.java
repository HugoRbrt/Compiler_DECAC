package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

public class This extends AbstractExpr {
    private final boolean addedByParse;

    public This(boolean addedByParse) {
        this.addedByParse = addedByParse;
    }

    @Override
    public boolean getAddedByParse() { return addedByParse; }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if (currentClass == null && !addedByParse) {
            throw new ContextualError(
                    "(RULE 3.43) 'this' reference used outside of class scope.", getLocation());
        }
        if (currentClass == null && addedByParse) {
            throw new ContextualError(
                    "(RULE 0.1) Method has not been declared.", getLocation());
        }
        setType(currentClass.getType());
        return currentClass.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (!addedByParse) { s.print("this"); }
    }

    @Override
    String prettyPrintNode() {
        return "This";
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // Leaf node
    }
    protected void codeGenPrint(DecacCompiler compiler, boolean printHex) {
        this.codeGenInst(compiler);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R0));
        compiler.addInstruction(new CMP(new NullOperand(), Register.R0));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BEQ(compiler.getErrorManager().getErrorLabel("Null dereferencing")));
        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // Leaf node
    }
}
