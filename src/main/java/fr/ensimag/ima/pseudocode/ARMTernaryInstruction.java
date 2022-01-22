package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Base class for instructions with 3 operands.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ARMTernaryInstruction extends Instruction {
    boolean ternaire = false;
    private Operand operand1, operand2, operand3;

    public Operand getOperand1() {
        return operand1;
    }

    public Operand getOperand2() {
        return operand2;
    }

    public Operand getOperand3() {
        return operand3;
    }

    @Override
    void displayOperands(PrintStream s) {
        s.print(" ");
        s.print(operand1);
        s.print(", ");
        s.print(operand2);
        if(ternaire) {
            s.print(", ");
            s.print(operand3);
        }
    }

    protected ARMTernaryInstruction(Operand op1, Operand op2, Operand op3) {
        this(op1, op2);
        Validate.notNull(op3);
        this.operand3 = op3;
        ternaire = true;
    }

    protected ARMTernaryInstruction(Operand op1, Operand op2) {
        Validate.notNull(op1);
        Validate.notNull(op2);
        this.operand1 = op1;
        this.operand2 = op2;
    }
}