package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

public class New extends AbstractExpr {
    private AbstractIdentifier instantiation;

    public New(AbstractIdentifier instantiation) {
        this.instantiation = instantiation;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type currentType = instantiation.verifyType(compiler);
        if (!currentType.isClass()) {
            throw new ContextualError(
                    "(RULE 3.42) New instance must be of a class type: new \u001B[31m" +
                    instantiation.getName() + "\u001B[0m().", getLocation());
        }
        instantiation.setDefinition(compiler.getEnvTypes().get(instantiation.getName(), instantiation.getLocation()));
        setType(currentType);
        return currentType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        instantiation.decompile(s);
        s.print("()");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        instantiation.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        instantiation.iter(f);
    }

    protected void codeGenInst(DecacCompiler compiler){
        GPRegister r = compiler.getListRegister().getRegister(compiler);
        compiler.addInstruction(new NEW(instantiation.getClassDefinition().getNumberOfFields()+1, r));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Stack overflow , a real one")));
        }
        compiler.addInstruction(new LEA(compiler.getstackTable().getClass(instantiation.getName()), Register.R0));
        //comment obtenir k ?
        compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(0, r)));
        compiler.addInstruction(new PUSH(r));
        compiler.addInstruction(new BSR(new Label("init."+instantiation.getName().getName())));
        compiler.addInstruction(new POP(Register.R0));
        compiler.getListRegister().freeRegister(r, compiler);
    }
}
