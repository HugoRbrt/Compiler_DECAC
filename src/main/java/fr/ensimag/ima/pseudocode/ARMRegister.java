package fr.ensimag.ima.pseudocode;

import fr.ensimag.deca.DecacCompiler;
//import fr.ensimag.ima.pseudocode.instructionsARM.pop;
//import fr.ensimag.ima.pseudocode.instructionsARM.push;


/**
 * ARM Register operand (including special registers like SP).
 *
 * @author gl49
 * @date 15/01/2022
 */

public class ARMRegister extends DVal {
    private String name; 
    /**
     * number of given registers (12 by default in ARM32bit)
     */
    private static int maxIndex = 12;
    
    /**
     * current index for a supposedly available register
    */
    private int currentIndex = 4;
    
    
    /**
    * public constructor to access them more easily
    */
    public ARMRegister() {
        this(12);
    }
    
    /**
     * @param maxIndex : total number of registers
     */
    public ARMRegister(int maxIndex) {
        this("ARM Register Bench", maxIndex);
    }
    
    protected ARMRegister(String name) {
        this(name, 12);
    }
    
    protected ARMRegister(String name, int maxIndex) {
        this.name = name;
        this.maxIndex = maxIndex;
    }

    public String debugDisplay() {
        String s = name + "[";
        for (int k = 0; k < maxIndex-1; k++) {
            s += "r" + Integer.toString(k) + ":" + r[k].debugDisplay() + " | ";
        }
        s += "r" + Integer.toString(maxIndex-1) + ":" + 
                r[maxIndex-1].debugDisplay() + "]";
        
        return s;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Frame Pointer register
     */
    public static final ARMRegister fp = new ARMRegister("fp");
    /**
     * Intra Procedural register
     */
    public static final ARMRegister ip = new ARMRegister("lb");
    /**
     * Stack Pointer
     */
    public static final ARMRegister sp = new ARMRegister("sp");

    /**
     * Link Register
     */
    public static final ARMRegister lr = new ARMRegister("lr");

    /**
     * Program Counter
     */
    public static final ARMRegister pc = new ARMRegister("pc");

    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    protected static final ARMGPRegister[] r = initRegisters();
    /**
     * General Purpose Registers
     */
    public static ARMGPRegister getR(int i) {
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
    public static final ARMGPRegister r2 = r[1];

    /**
     * Convenience shortcut for r[3]
     */
    public static final ARMGPRegister r3 = r[1];


    /**
     * Convenience shortcut for r[7]
     */
    public static final ARMGPRegister r7 = r[7];


    static private ARMGPRegister[] initRegisters() {
        ARMGPRegister [] res = new ARMGPRegister[maxIndex];
        for (int i = 0; i < maxIndex; i++) {
            res[i] = new ARMGPRegister("r" + i, i);
        }
        return res;
    }

    
    /**
     * @return a register. This register is taken as the first available
     * register. If no such register is found, we return the last one
     * and indicate that it will need to be pushed
     */
    public ARMGPRegister getRegister(DecacCompiler compiler){
        
        for (int k = currentIndex; k < maxIndex; k++) {
            // if the register is available
            if (r[k].available()) {
                // and different from r7 (for simplication)
                if (k != 7) {
                    // we make it unavailable and say that we do not need to push it
                    r[k].use();
                    // we update the index for a supposedly free register
                    currentIndex = k+1;
                    return r[k];
		}
            }
        }
        
        // if we arrive here, no available register was found
        // in this case, we take the last register and push it
        // before using it
        ARMGPRegister pushedRegister = r[maxIndex-1]; // for now
        assert !(pushedRegister.available());
        pushedRegister.incrNbPushOnRegister(1);
        
        //compiler.addInstruction(new push(pushedRegister));
        compiler.incrPushCount(1);
        
        return r[maxIndex-1];
    }
    
    /**
     * free register given in argument and set its needPush field to false
     */
    public void freeRegister(ARMGPRegister usedRegister, DecacCompiler compiler) {
        // if register is free, do nothing
        // else
        if (!usedRegister.available()) {
            // if values were pushed onto it
            if (usedRegister.getNbPushOnRegister() > 0) {
                // we Pop the register and decrease the number of Push on it
                // compiler.addInstruction(new pop(usedRegister));
                compiler.incrPopCount(1);
                usedRegister.decrNbPushOnRegister(1);
            
            } else {
                // we do not need to pop and just make it free
                usedRegister.free();
                // we update the index of an available register
                int regNb = usedRegister.getNumber();
            
                if (regNb < currentIndex) {
                    currentIndex = regNb;
                }
            }
        }
        
    }

    /**
     * The two tests below only have a debugging purpose. 
     */
    /**
     * @return a register. This register is taken as the first available
     * register. If no such register is found, we return the last one
     * and indicate that it will need to be pushed
     */
    public ARMGPRegister getRegisterWithoutCompiler(){
        for (int k = currentIndex; k < maxIndex; k++) {
            if (r[k].available()) {
                if (k != 7) {
                    r[k].use();
                    currentIndex = k+1;
		    return r[k];
		}
            }
        }
        ARMGPRegister pushedRegister = r[maxIndex-1]; // for now
        assert !(pushedRegister.available());
        pushedRegister.incrNbPushOnRegister(1);
        return r[maxIndex-1];
    }
    
    /**
     * free register given in argument and set its needPush field to false
     */
    public void freeRegisterWithoutCompiler(ARMGPRegister usedRegister) {
        if (!usedRegister.available()) {
            if (usedRegister.getNbPushOnRegister() > 0) {
                usedRegister.decrNbPushOnRegister(1);
            } else {
                usedRegister.free();
                int regNb = usedRegister.getNumber();
                if (regNb < currentIndex) {
                    currentIndex = regNb;
                }
            }
        }
        
    }



}
