package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.ARMTernaryInstruction;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class sub extends ARMTernaryInstruction {

    public sub(ARMGPRegister op1, ARMRegister op2, ARMRegister op3) {
        super(op1, op2, op3);
    }

    public sub(ARMRegister op1, ARMRegister op2, DVal op3) {
        super(op1, op2, op3);
    }

    public sub(ARMRegister op1, DVal op2, DVal op3) {
        super(op1, op2, op3);
    }

    public sub(ARMGPRegister op1, ARMRegister op2, int i) {
        this(op1, op2, new ImmediateInteger(i));
    }

    public sub(ARMGPRegister op1, int i, int j) {
        this(op1, new ImmediateInteger(i), new ImmediateInteger(j));
    }

}