package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.svc;
import fr.ensimag.ima.pseudocode.instructionsARM.push;
import fr.ensimag.ima.pseudocode.instructionsARM.pop;
import fr.ensimag.ima.pseudocode.instructionsARM.cmp;
import fr.ensimag.ima.pseudocode.instructionsARM.movmi;
import fr.ensimag.ima.pseudocode.instructionsARM.submi;
import fr.ensimag.ima.pseudocode.instructionsARM.blmi;
import fr.ensimag.ima.pseudocode.instructionsARM.blpl;
import fr.ensimag.ima.pseudocode.instructionsARM.poplo;
import fr.ensimag.ima.pseudocode.instructionsARM.movlo;
import fr.ensimag.ima.pseudocode.instructionsARM.sub;
import fr.ensimag.ima.pseudocode.instructionsARM.add;
import fr.ensimag.ima.pseudocode.instructionsARM.bl;
import fr.ensimag.ima.pseudocode.instructionsARM.bllo;
import fr.ensimag.ima.pseudocode.instructionsARM.movgt;
import fr.ensimag.ima.pseudocode.instructionsARM.moveq;
import fr.ensimag.ima.pseudocode.instructionsARM.addeq;
import fr.ensimag.ima.pseudocode.instructionsARM.pushgt;
import fr.ensimag.ima.pseudocode.instructionsARM.pusheq;
import fr.ensimag.ima.pseudocode.instructionsARM.blgt;
import fr.ensimag.ima.pseudocode.instructionsARM.bleq;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.instructionsARM.addgt;



import fr.ensimag.ima.pseudocode.ARMRegister;


import java.util.HashMap;

/**
 * Initial version : 19/1/2022
 * @author gl49
 * Class used to generate all the code relative to functions in ARM
 * programs' execution
 */

public class ARMFunctionManager {


    public ARMFunctionManager() {
        super();
    }

    /**
     * FunctionManager generates codes for functions that are put there
     */
    public void genCodeFunctionManager(DecacCompiler compiler) {
        genCodeEndProgram(compiler);
    }

    public void genCodeEndProgram(DecacCompiler compiler) {
        compiler.addARMBlock(".data");
        compiler.addARMBlock("flottant: .asciz \"%f\"");
        compiler.addARMBlock("int: .asciz \"%i\"");
        compiler.addARMBlock("newline: .asciz \"\\n\"");
    }

}