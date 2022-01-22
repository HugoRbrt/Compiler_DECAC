package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.*;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class neg extends BinaryInstruction {

    public neg(ARMRegister op1, ARMRegister op2) {
        super(op1, op2);
    }

    public neg(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

}
