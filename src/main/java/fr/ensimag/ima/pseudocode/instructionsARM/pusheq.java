package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMStackInstruction;

public class pusheq extends ARMStackInstruction {
    public pusheq(ARMRegister ... reg) {
        super(reg);
    }
    
}