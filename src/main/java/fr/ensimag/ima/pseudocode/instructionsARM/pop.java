package fr.ensimag.ima.pseudocode.instructionsARM;

import fr.ensimag.ima.pseudocode.ARMRegister;
import fr.ensimag.ima.pseudocode.ARMStackInstruction;

public class pop extends ARMStackInstruction {
    public pop(ARMRegister ... reg) {
        super(reg);
    }
    
}

