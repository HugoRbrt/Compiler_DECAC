package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Instruction with a single operand.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public abstract class ARMUnaryInstructionString extends UnaryInstruction {

    protected ARMUnaryInstructionString(ImmediateString operand) {
        super(operand);
    }

    protected ARMUnaryInstructionString(String blockLabel) {
        super(new ImmediateString(blockLabel));
    }

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(super.operand.toString().replace("\"", ""));
    }
}