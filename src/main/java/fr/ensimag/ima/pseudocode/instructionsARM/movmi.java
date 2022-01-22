package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMGPRegister;
import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

public class movmi extends mov{

    public movmi(ARMRegister op1, ARMGPRegister op2) {
        super(op1, op2);
    }

    public movmi(ARMRegister op1, DVal op2) {
        super(op1, op2);
    }

    public movmi(ARMGPRegister r, int i) {
        this(r, new ImmediateInteger(i));
    }
    
}
