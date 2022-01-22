package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class moveq extends BinaryInstruction {

    public moveq(ARMRegister op1, ARMGPRegister op2) {
        super(op1, op2);
    }

    public moveq(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

    public moveq(ARMGPRegister r, int i) {
        this(r, new ImmediateInteger(i));
    }

}
