package fr.ensimag.ima.pseudocode;

/**
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class UnaryInstructionToReg extends UnaryInstruction {

    public UnaryInstructionToReg(Register op) {
        super(op);
    }

    public UnaryInstructionToReg(ARMGPRegister op) {
        super(op);
    }

}
