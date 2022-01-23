package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DVal;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class neglt extends BinaryInstruction {

    public neglt(ARMRegister op1, ARMRegister op2) {
        super(op1, op2);
    }

    public neglt(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

}
