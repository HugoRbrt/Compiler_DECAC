package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class svc extends UnaryInstructionImmInt {

    public svc(int op) {
        super(op);
    }

    public svc(ImmediateInteger operand) {
        super(operand);
    }

}
