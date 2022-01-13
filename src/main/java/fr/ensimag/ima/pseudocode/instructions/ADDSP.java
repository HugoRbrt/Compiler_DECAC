package fr.ensimag.ima.pseudocode.instructions;

import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;

/**
 * Add a value to stack pointer.
 * 
 * @author Ensimag
 * @date 01/01/2022
 */
public class ADDSP extends UnaryInstructionImmInt {
    // instead of using this in any declaration
    // use compiler.incrDeclVariables
    public ADDSP(ImmediateInteger operand) {
        super(operand);
    }

    public ADDSP(int i) {
        super(i);
    }

}
