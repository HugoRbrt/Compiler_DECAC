package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;



/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);

    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    protected ListDeclClass classes;
    protected AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        // initializing builtin types, including Object class
        SymbolTable symbT = compiler.getSymbTable();
        EnvironmentType envT = compiler.getEnvTypes();
        envT.put(symbT.create("void"),
                new TypeDefinition(new VoidType(symbT.create("void")), Location.BUILTIN));
        envT.put(symbT.create("boolean"),
                new TypeDefinition(new BooleanType(symbT.create("boolean")), Location.BUILTIN));
        envT.put(symbT.create("float"),
                new TypeDefinition(new FloatType(symbT.create("float")), Location.BUILTIN));
        envT.put(symbT.create("int"),
                new TypeDefinition(new IntType(symbT.create("int")), Location.BUILTIN));
        envT.put(symbT.create("string"),
                new TypeDefinition(new StringType(symbT.create("string")), Location.BUILTIN));
        envT.put(symbT.create("null"),
                new TypeDefinition(new NullType(symbT.create("null")), Location.BUILTIN));
        /*  Defining Deca types uses ClassDefinition extension of TypeDefinition; the ClassType
            instance will create a ClassDefinition with its own EnvironmentExp that includes a
            pointer to the superclass's environment. */
        envT.put(symbT.create("Object"),
                new ClassDefinition(new ClassType(symbT.create("Object"), Location.BUILTIN, null),
                        Location.BUILTIN, null));
        classes.verifyListClass(compiler);
//        classes.verifyListClassMembers(compiler);
//        classes.verifyListClassBody();
        main.verifyMain(compiler);
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code

        //creation of Register
        compiler.setListRegister(new Register());
        compiler.addComment("Main program");
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
    }

    public void codeGenProgramARM(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code
        compiler.addARMBlock(".text");
        compiler.addARMBlock(".global _start");
        compiler.addARMComment("ARM program");
        //creation of ARM Register
        compiler.setListRegister(new ARMRegister());
        main.codeGenMainARM(compiler);
    }
    

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
