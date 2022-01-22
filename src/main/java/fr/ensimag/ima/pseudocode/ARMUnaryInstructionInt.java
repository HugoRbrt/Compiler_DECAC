package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Instruction with a single operand.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ARMUnaryInstructionInt extends UnaryInstruction {
    
    protected ARMUnaryInstructionInt(ImmediateInteger operand) {
        super(operand);
    }

    protected ARMUnaryInstructionInt(int i) {
        this(new ImmediateInteger(i));
    }

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(super.operand.toString().replace("\"", ""));
    }
}
