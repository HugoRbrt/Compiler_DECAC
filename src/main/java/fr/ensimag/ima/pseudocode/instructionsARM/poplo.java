package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMStackInstruction;

public class poplo extends ARMStackInstruction {
    public poplo(ARMRegister ... reg) {
        super(reg);
    }
    
}
