package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class NEW extends BinaryInstructionDValToReg {

    public NEW(DVal size,Register destination) {
        super(size, destination);
    }

    public NEW(int size, Register op2) {
        super(new ImmediateInteger(size), op2);
    }

}
