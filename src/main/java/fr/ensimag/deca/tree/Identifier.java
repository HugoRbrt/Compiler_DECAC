package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.ImmediateString;
import java.io.PrintStream;
import java.util.Objects;
import fr.ensimag.ima.pseudocode.instructionsARM.*;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * Deca Identifier
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Identifier extends AbstractIdentifier {
    
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    /**
     * Context check first, second and third pass. Checks whether the identifier
     * exists in the current environment (i.e. has been declared).
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return
     * @throws ContextualError
     *            Thrown if the identifier has not been declared.
     */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        ExpDefinition def = localEnv.get(name);
        if (def == null) {
            throw new ContextualError(
                    "(RULE 0.1) variable '" + name + "' has not been declared.", getLocation());
        }
        Type currentType = def.getType();
        setDefinition(localEnv.get(getName()));
        setType(currentType);
        return currentType;
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        return compiler.getEnvTypes().get(this.getName(), getLocation()).getType();
    }
    
    
    private Definition definition;


    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

    @Override
    public String toString() {
        return name.toString();
    }

    protected void codeGenPrint(DecacCompiler compiler, boolean printHex){
        if (getDefinition().isExpression()) {
            RegisterOffset R = compiler.getstackTable().get(this.getName());
            compiler.addInstruction(new LOAD(R, Register.R1));
        }
        if (definition.getType().isInt()) {
            compiler.addInstruction(new WINT());
        } else if (definition.getType().isString()) {
            String s = compiler.getSymbTable().get(getName().getName()).getName();
            compiler.addInstruction(new WSTR(new ImmediateString(s)));
        } else {
            if (printHex) {
                compiler.addInstruction(new WFLOATX());
            } else {
                compiler.addInstruction(new WFLOAT());
            }
        }
    }

    protected void codeGenPrintARM(DecacCompiler compiler, boolean printHex){
        /*
        if (getDefinition().isExpression()) {
            // Non testé
            compiler.addInstruction(new mov(ARMRegister.r0,1));
            compiler.addInstruction(new ldr(ARMRegister.r1, "=" + this.name.toString()));
        }
        */  
        // Ici on ne gère pas les expressions qui sont en fait déjà géré par ARM car les variables sont déclarés dans la section data
        // il suffit donc de récupérer le nom de la variable directement. Pas besoin de passer par la StackHashTable. 
        if (definition.getType().isInt()) {
            compiler.addInstruction(new ldr(ARMRegister.r0, "=int"));
            compiler.addInstruction(new ldr(ARMRegister.r1, "=" + this.name.toString()));
            compiler.addInstruction(new ldr(ARMRegister.r1, "[r1]"));
        } else {
            compiler.addInstruction(new ldr(ARMRegister.r1, "=" + this.name.toString()));
            compiler.addInstruction(new vldr(ARMRegister.s0, "[r1]"));
            compiler.addARMBlock("        vcvt.f64.f32 d0, s0");
            compiler.addInstruction(new vmov(ARMRegister.r2, ARMRegister.r3, ARMRegister.d0));
            compiler.addInstruction(new ldr(ARMRegister.r0, "=flottant"));        
        }
        compiler.addInstruction(new bl("printf"));
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        if(getDefinition().isExpression()){
            RegisterOffset R = compiler.getstackTable().get(this.getName());
            if(Objects.isNull(R.getRegister())){
                compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
                compiler.addInstruction(new LOAD(new RegisterOffset(R.getOffset(), Register.R1), Register.R0));
            }else{
                compiler.addInstruction(new LOAD(R, Register.R0));
            }
        }
    }

    @Override
    protected void codeGenInstARM(DecacCompiler compiler) {
        if(getDefinition().isExpression()){
            compiler.addInstruction(new ldr(ARMRegister.r0, "=" + this.name.toString()));
            compiler.addInstruction(new ldr(ARMRegister.r0, "[r0]"));
  
        }
    }
}
