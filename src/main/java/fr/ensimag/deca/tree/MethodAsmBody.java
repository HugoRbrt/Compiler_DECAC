package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.InlinePortion;

import java.io.PrintStream;

public class MethodAsmBody extends AbstractMethodBody {
    private StringLiteral asmCode;

    public MethodAsmBody(StringLiteral asmCode) {
        this.asmCode = asmCode;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("asm (");
        s.indent();
        asmCode.decompile(s);
        s.println();
        s.unindent();
        s.println(");");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        asmCode.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        asmCode.iter(f);
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType) throws ContextualError {
        asmCode.setType(new StringType(compiler.getSymbTable().create("string")));
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        String[] asmInstructions = asmCode.getValue().split("\\n");
        for (String asmInst : asmInstructions) {
            compiler.add(new InlinePortion("\t" + asmInst));
        }
    }

    public int getNumberLocalVariables(){
        return 0;
    }
}
