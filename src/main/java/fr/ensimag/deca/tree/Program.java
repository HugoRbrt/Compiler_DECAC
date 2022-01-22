package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructionsARM.b;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.Register;
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
        classes.verifyListClass(compiler);
        classes.verifyListClassMembers(compiler);
        classes.verifyListClassBody(compiler);
        main.verifyMain(compiler, compiler.getEnvExp(), null,
                compiler.getEnvTypes().get(compiler.getSymbTable().create("void")).getType());
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        //creation of the register bench with a given number of registers
        compiler.setListRegister(new Register(compiler.getCompilerOptions().getRegisters()));
        classes.codeGenTable(compiler);
        main.codeGenMain(compiler);

        // after analysis of the program, we generate the TSTO instruction
        int d1 = compiler.getCodeAnalyzer().getNeededStackSize();
        int d2 = compiler.getCodeAnalyzer().getNbDeclaredVariables();
        if (!compiler.getCompilerOptions().getArmBool()) {
            compiler.getErrorManager().setTstoArg(d1);
            compiler.getErrorManager().setAddspArg(d2);

            compiler.getErrorManager().addTstoCheck(compiler);
            compiler.getErrorManager().genCodeErrorManager(compiler);
        }
        classes.codeGen(compiler);
    }
    public void codeGenProgramARM(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code
        compiler.addARMBlock(".text");
        compiler.addARMBlock(".global main");
        compiler.addARMBlock(".extern printf");
        compiler.addARMComment("ARM program");
        //creation of ARM Register
        compiler.setListRegisterARM(new ARMRegister());
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
