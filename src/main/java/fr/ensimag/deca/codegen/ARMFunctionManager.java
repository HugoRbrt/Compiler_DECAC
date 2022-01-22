package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructionsARM.*;




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
        genCodeDivideProgram(compiler);
        genCodeEndProgram(compiler);
    }

    public void genCodeEndProgram(DecacCompiler compiler) {
        compiler.addARMBlock(".data");
        compiler.addARMBlock("flottant: .asciz \"%f\"");
        compiler.addARMBlock("int: .asciz \"%i\"");
        compiler.addARMBlock("newline: .asciz \"\\n\"");
        compiler.addARMBlock("tmpint" + ": " + ".int 0");  //temporary label to have an address to load the result of readint
        compiler.addARMBlock("tmpfloat" + ": " + ".float 0");  //temporary label to have an address to load the result of readfloat
        compiler.addARMBlock(".text");
        compiler.addARMBlock("end:");
        compiler.addInstruction(new pop(ARMRegister.ip, ARMRegister.pc));
    }

    public void genCodeDivideProgram(DecacCompiler compiler) {
        compiler.addARMBlock(".text");
        Label divide = new Label("divide");
        compiler.addLabel(divide);
        compiler.addInstruction(new push(ARMRegister.lr));
        compiler.addInstruction(new mov(ARMRegister.r2, 0));
        Label divide_iter = new Label("divide_iter");
        compiler.addLabel(divide_iter);
        compiler.addInstruction(new cmp(ARMRegister.r0, ARMRegister.r1));
        compiler.addInstruction(new poplo(ARMRegister.pc));
        compiler.addInstruction(new sub(ARMRegister.r0, ARMRegister.r0, ARMRegister.r1));
        compiler.addInstruction(new add(ARMRegister.r2, ARMRegister.r2, 1));
        compiler.addInstruction(new bl("divide_iter"));
    }

}