package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class movne extends BinaryInstruction {

    public movne(ARMRegister op1, ARMGPRegister op2) {
        super(op1, op2);
    }

    public movne(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

    public movne(ARMGPRegister r, int i) {
        this(r, new ImmediateInteger(i));
    }

}
