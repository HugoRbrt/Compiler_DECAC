package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class POP extends UnaryInstructionToReg {

    public POP(Register op) {
        super(op);
    }

}
