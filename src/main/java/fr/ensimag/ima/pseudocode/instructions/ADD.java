package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;

/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class ADD extends BinaryInstructionDValToReg {
    public ADD(DVal op1, Register op2) {
        super(op1, op2);
    }
}
