package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class mov extends ARMBinaryInstructionDValToReg {

    public mov(DVal op1, ARMGPRegister op2) {
        super(op2, op1);
    }

    public mov(ARMGPRegister r, int i) {
        this(new ImmediateInteger(i),r);
    }

}
