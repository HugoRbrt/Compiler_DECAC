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
    protected ARMRegister(String name) {
        super(name);
    }
    
    public ARMRegister() {
        this("NoName");
    }

    /**
     * Current Program State Register/Flags
     */
    public static final ARMRegister CPSR = new ARMRegister("CPSR");
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
     * Convenience shortcut for r[0]
     */
    public static final ARMGPRegister r0 = r[0];
    /**
     * Convenience shortcut for r[1]
     */
    public static final ARMGPRegister r1 = r[1];
    /**
     * Convenience shortcut for r[2]
     */
    public static final ARMGPRegister r2 = r[2];


    /**
     * Convenience shortcut for r[7]
     */
    public static final ARMGPRegister r7 = r[7];



    /**
     * Convenience shortcut for r[11] which is the Frame Pointer Register
     */
    public static final ARMGPRegister FP = r[11];
    /**
     * Convenience shortcut for r[12] which is Intra Procedural Call Register
     */
    public static final ARMGPRegister IP = r[12];
    /**
     * Convenience shortcut for r[13] which is the Stack pointer Register
     */
    public static final ARMGPRegister SP = r[13];
    /**
     * Convenience shortcut for r[14] which is the Link Register
     */
    public static final ARMGPRegister LR = r[14];
    /**
     * Convenience shortcut for r[15] which is the Program Counter  Register
     */
    public static final ARMGPRegister PC = r[15];

    static private ARMGPRegister[] initRegisters() {
        ARMGPRegister [] res = new ARMGPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new ARMGPRegister("r" + i, i);
        }
        return res;
    }

    public GPRegister UseFirstAvailableRegister(){//we need to complete implementation of this function
        for(int i=2;i<16;i++){
            if(R[i].available()){
                R[i].use();
                return R[i];
            }
        }
        throw new IllegalArgumentException("no Register Available");
    }
    public ARMGPRegister ARMUseSpecificRegister(int i){
        return r[i];
    }
}
