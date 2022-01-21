package fr.ensimag.ima.pseudocode.instructionsARM;
import java.io.PrintStream;
import fr.ensimag.ima.pseudocode.*;
import org.apache.commons.lang.Validate;


/**
 * @author Ensimag
 * @date 01/01/2022
 */
public class vcvt extends ARMTernaryInstruction {

    void displayOperands(PrintStream s) {
        s.print(".f64.s32 ");
        s.print(getOperand1());
        s.print(", ");
        s.print(getOperand2());
    }

    

    public vcvt(Operand op1, Operand op2) {
        super(op1, op2);
    }

}

