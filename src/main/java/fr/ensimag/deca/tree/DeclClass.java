package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl49
 * @date 01/01/2022
 */
public class DeclClass extends AbstractDeclClass {

    final private AbstractIdentifier className;
    final private AbstractIdentifier superClass;
    final private ListDeclField fields;
    final private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier className, AbstractIdentifier superClass,
            ListDeclField fields, ListDeclMethod methods) {
        Validate.notNull(className);
        Validate.notNull(superClass);
        Validate.notNull(fields);
        Validate.notNull(methods);
        this.className = className;
        this.superClass = superClass;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    /**
     * Contextual class declaration check. First checks whether the superclass exists, then checks
     * whether the class hasn't already been declared.
     *
     * @param compiler contains the predefined EnvironmentType.
     * @throws ContextualError if the class has already been declared.
     */
    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        EnvironmentType envT = compiler.getEnvTypes();
        SymbolTable.Symbol nameSymb = className.getName();
        superClass.verifyType(compiler);
        ClassDefinition superCl = (ClassDefinition) envT.get(superClass.getName());
        ClassType cl = new ClassType(nameSymb, getLocation(), superCl);
        try {
            envT.declare(nameSymb, cl.getDefinition());
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError(
                    "(RULE 3.17) Class has already been declared.", className.getLocation());
        }
        className.setDefinition(envT.get(className.getName()));
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        ClassDefinition superCl = (ClassDefinition) compiler.getEnvTypes().get(superClass.getName());
        ClassDefinition cl = (ClassDefinition) compiler.getEnvTypes().get(className.getName());
        cl.setNumberOfFields(superCl.getNumberOfFields());
        for (AbstractDeclField f: fields.getList()) {
            cl.incNumberOfFields();
            f.verifyField(compiler, cl.getMembers(), cl, cl.getNumberOfFields());
        }
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        className.prettyPrint(s, prefix, false);
        superClass.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, false, false);
        methods.prettyPrint(s, prefix, true, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        className.iter(f);
        superClass.iter(f);
        fields.iter(f);
        methods.iter(f);
    }

}
