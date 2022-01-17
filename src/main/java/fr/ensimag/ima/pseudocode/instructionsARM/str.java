package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.ARMGPRegister;


/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class str extends ARMBinaryInstructionStringToReg {

    public str(ARMGPRegister op1, String op2) {
        super(op1, op2);
    }
}