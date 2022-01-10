package fr.ensimag.ima.pseudocode;

import fr.ensimag.ima.pseudocode.ARMGPRegister;
import fr.ensimag.ima.pseudocode.Register;

/**
 * ARMRegister operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2022
 */
public class ARMRegister extends Register {
    public ARMRegister(String name) {
        super(name);
    }

    /**
     * Global Base register
     */
    public static final ARMRegister SP = new ARMRegister("SP");
    /**
     * Local Base register
     */
    public static final ARMRegister LR = new ARMRegister("LR");
    /**
     * Stack Pointer
     */
    public static final ARMRegister PC = new ARMRegister("PC");
    /**
     * Application Program Status Register
     */
    public static final ARMRegister APSR = new ARMRegister("APSR");
    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    private static final ARMGPRegister[] r = initRegisters();
    /**
     * General Purpose Registers
     */
    public static ARMGPRegister getr(int i) {
        return r[i];
    }
    /**
     * Convenience shortcut for x[0]
     */
    public static final ARMGPRegister r0 = r[0];
    /**
     * Convenience shortcut for x[1]
     */
    public static final ARMGPRegister r1 = r[1];
    static private ARMGPRegister[] initRegisters() {
        ARMGPRegister [] res = new ARMGPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new ARMGPRegister("r" + i, i);
        }
        return res;
    }
    public ARMGPRegister ARMUseFirstAvailableRegister(){
        super.occupiedRegister++;
        return r[occupiedRegister-1];
    }
    public void ARMreleaseRegister(){
        super.releaseRegister();
    }
    public ARMGPRegister ARMUseSpecificRegister(int i){
        return r[i];
    }
}
