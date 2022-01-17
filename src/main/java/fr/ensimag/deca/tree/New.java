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
            throw new ContextualError("(RULE 3.42) New instance must be of a class type.", getLocation());
        }
        instantiation.setDefinition(compiler.getEnvTypes().get(instantiation.getName(), instantiation.getLocation()));
        setType(currentType);
        return currentType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
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
        compiler.addInstruction(new NEW(instantiation.getClassDefinition().getNumberOfFields(), r));
        compiler.addInstruction(new BOV(compiler.getErrorManager().getErrorLabel("Stack overflow , a real one")));
        compiler.addInstruction(new LEA(compiler.getstackTable().get(instantiation.getName()), compiler.getListRegister().R0));
        //comment obtenir k ?
        compiler.addInstruction(new STORE(compiler.getListRegister().R0, new RegisterOffset(0, r)));
        compiler.addInstruction(new PUSH(r));
        compiler.addInstruction(new BSR(new Label("init."+instantiation.getName().getName())));
        compiler.addInstruction(new POP(compiler.getListRegister().R0));
        compiler.getListRegister().freeRegister(r, compiler);



    }
}
