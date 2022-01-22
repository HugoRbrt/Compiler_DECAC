package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Base class for instructions with 2 operands, the first being a
 * DAddr, and the second a Register.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ARMBinaryInstructionStringToReg extends Instruction {
    private Operand operand1;
    private String operand2;

    public Operand getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand1);
        s.print(", ");
        s.print(operand2);
    }

    public ARMBinaryInstructionStringToReg(ARMRegister op1, String op2) {
        Validate.notNull(op1);
        Validate.notNull(op2);
        this.operand1 = op1;
        this.operand2 = op2;
    }

}
