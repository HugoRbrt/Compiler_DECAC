package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class movge extends BinaryInstruction {

    public movge(ARMRegister op1, ARMGPRegister op2) {
        super(op1, op2);
    }

    public movge(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

    public movge(ARMGPRegister r, int i) {
        this(r, new ImmediateInteger(i));
    }

}
