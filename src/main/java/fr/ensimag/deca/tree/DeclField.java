package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclField extends AbstractDeclField {
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    final private AbstractInitialization initialization;

    public DeclField(Visibility visibility, AbstractIdentifier type,
            AbstractIdentifier fieldName, AbstractInitialization initialization) {
        Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.visibility = visibility;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    public AbstractInitialization getInit() { return initialization; }

    /**
     * Context check second pass. Checks field type (cannot be void), then
     * checks if the field name is already in use. If that is the case, checks
     * whether the name is used in the current environment (illegal) or used
     * as a method name in any environment (illegal).
     *
     * NB: does not check field initialization, which is done on pass 3, after
     * method signatures have been checked.
     *
     * @param compiler
     * @param currentClass
     * @param counter
     *          Counter to index the fields; counts from number of inherited
     *          fields + 1.
     * @throws ContextualError
     */
    @Override
    protected void verifyField(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, int counter) throws ContextualError {
        Type currentType = type.verifyType(compiler);
        if (currentType.isVoid()) {
            throw new ContextualError(
                    "(RULE 2.5) Field cannot be void type.", type.getLocation());
        }
        type.setDefinition(compiler.getEnvTypes().get(type.getName(), type.getLocation()));
        type.setType(currentType);
        SymbolTable.Symbol f = fieldName.getName();
        ExpDefinition def = localEnv.get(f);
        if (def == null || def.isField()) {
            try {
                localEnv.declare(f, new FieldDefinition(
                        currentType, fieldName.getLocation(), visibility, currentClass, counter));
            } catch (EnvironmentExp.DoubleDefException e) {
                throw new ContextualError(
                        "(RULE 3.17) Field has already been declared.",
                        fieldName.getLocation());
            }
        } else {
            throw new ContextualError(
                    "(RULE 2.5) Illegal override: method --> field.",
                    fieldName.getLocation());
        }
        fieldName.setDefinition(localEnv.get(f));
    }

    @Override
    protected void verifyFieldInitialization(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        initialization.verifyInitialization(compiler, type.getType(), localEnv, currentClass);
    }

    @Override
    public void decompile(IndentPrintStream s) {}

    @Override
    String prettyPrintNode() {
        return "[visibility=" + visibility + "] " + getClass().getSimpleName();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false, false);
        fieldName.prettyPrint(s, prefix, false, false);
        initialization.prettyPrint(s, prefix, true, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        fieldName.iter(f);
        initialization.iter(f);
    }

    protected void codeGen(DecacCompiler compiler, int fieldCounter){
        compiler.addInstruction(new LOAD(new ImmediateInteger(0), compiler.getListRegister().R0));
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, compiler.getListRegister().LB), compiler.getListRegister().R1));
        compiler.addInstruction(new STORE(compiler.getListRegister().R0, new RegisterOffset(fieldCounter+1, compiler.getListRegister().R1)));
    }
}
