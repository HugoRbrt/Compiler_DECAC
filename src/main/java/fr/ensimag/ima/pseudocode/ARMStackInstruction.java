package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

public class ARMStackInstruction extends Instruction {
    private List<Operand> operandtable = new ArrayList<Operand>();

    public Operand getOperand(int i) {
        return operandtable.get(i);
    }

    @Override
    void displayOperands(PrintStream s) {
        s.print(" {");
        s.print(getOperand(0));
        for (int i = 1; i < operandtable.size(); i++) {
            s.print(", ");
            s.print(getOperand(i));
        }
        s.print("}");
    }

    protected ARMStackInstruction(Operand ... arguments) {
        for (Operand op : arguments) {
            Validate.notNull(op);
            operandtable.add(op);
        }
    }
    
}
