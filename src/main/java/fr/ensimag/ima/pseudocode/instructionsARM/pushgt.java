package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMStackInstruction;

public class pushgt extends ARMStackInstruction {
    public pushgt(ARMRegister ... reg) {
        super(reg);
    }
    
}
