package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMStackInstruction;

public class popgt extends ARMStackInstruction {
    public popgt(ARMRegister ... reg) {
        super(reg);
    }
    
}
