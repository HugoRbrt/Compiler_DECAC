package fr.ensimag.ima.pseudocode.instructionsARM;
import java.io.PrintStream;

import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import org.apache.commons.lang.Validate;


/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class vmov extends ARMTernaryInstruction {

    public vmov(Operand op1, Operand op2, Operand op3) {
        super(op1, op2, op3);
    }

    public vmov(Operand op1, Operand op2) {
        super(op1, op2);
    }
}