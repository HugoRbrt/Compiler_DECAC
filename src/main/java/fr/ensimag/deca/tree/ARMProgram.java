package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.instructionsARM.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.deca.context.*;
import fr.ensimag.ima.pseudocode.*;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl49
 * @date 01/01/2022
 */
public class ARMProgram extends Program {

    public ARMProgram(ListDeclClass classes, AbstractMain main) {
        super(classes,main);
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
    // A FAIRE: compléter ce squelette très rudimentaire de code
    compiler.addARMBlock(".text");
    compiler.addARMBlock(".global _start");
    compiler.addARMComment("ARM program");
    //creation of ARM Register
    compiler.setListRegister(new ARMRegister("List of ARMRegister"));
    main.codeGenMainARM(compiler);
}
}
