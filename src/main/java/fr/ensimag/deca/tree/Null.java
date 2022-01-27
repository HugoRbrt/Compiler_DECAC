package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

public class Null extends AbstractExpr {
    public Null() {
        super();
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        NullType currentType = new NullType(compiler.getSymbTable().create("null"));
        setType(currentType);
        return currentType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
    }

    @Override
    String prettyPrintNode() {
        return "Null";
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // Leaf node
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // Leaf node
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
    }
}
