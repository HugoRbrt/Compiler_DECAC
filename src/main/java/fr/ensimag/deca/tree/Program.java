package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

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
//        classes.verifyListClass(compiler);
//        classes.verifyListClassMembers(compiler);
//        classes.verifyListClassBody();
        main.verifyMain(compiler);
        //throw new UnsupportedOperationException("not yet implemented");
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
    
    /**
     * @param d1 : needed stack size
     * @param d2 : number of declared variables
     * @param compiler : the compiler to add the lines at
     * Adds the first instruction of TSTO check and ADDSP in the list of lines
     */
    @Override
    public void addTstoCheck(int d1, int d2, DecacCompiler compiler) {
        // This function is called with DecacCompiler
        compiler.addFirstInstruction(new ADDSP(d2));
        compiler.addFirstInstruction(new BOV(new Label("stack_overflow")));
        compiler.addFirstInstruction(new TSTO(d1));
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
