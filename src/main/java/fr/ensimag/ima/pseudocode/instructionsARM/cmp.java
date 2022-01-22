package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class cmp extends BinaryInstruction {

    public cmp(ARMRegister op1, ARMGPRegister op2) {
        super(op1, op2);
    }

    public cmp(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

    public cmp(ARMRegister op1, int i) {super(op1, new ImmediateInteger(i));}

    public cmp(ARMGPRegister r, int i) {
        this(r, new ImmediateInteger(i));
    }

}