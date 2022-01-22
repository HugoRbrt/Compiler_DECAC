package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMTernaryInstruction;
import fr.ensimag.ima.pseudocode.Operand;

public class vadd extends ARMTernaryInstruction {

    public vadd(Operand op1, Operand op2, Operand op3){
        super(op1, op2, op3);
    }
}
