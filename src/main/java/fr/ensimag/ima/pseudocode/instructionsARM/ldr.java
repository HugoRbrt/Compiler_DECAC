package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.ARMBinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class ldr extends ARMBinaryInstructionStringToReg {

    public ldr(ARMGPRegister op2, String op1) {
        super(op2, op1);
    }

    public ldr(String s, ARMGPRegister r) {
        this(r,s);
    }

}
