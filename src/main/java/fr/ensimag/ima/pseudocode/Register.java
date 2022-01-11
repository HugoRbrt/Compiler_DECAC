package fr.ensimag.ima.pseudocode;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.POP;


/**
 * Register operand (including special registers like SP).
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class Register extends DVal {
    private String name;

    protected Register(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Global Base register
     */
    public static final Register GB = new Register("GB");
    /**
     * Local Base register
     */
    public static final Register LB = new Register("LB");
    /**
     * Stack Pointer
     */
    public static final Register SP = new Register("SP");
    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    protected static final GPRegister[] R = initRegisters();
    /**
     * General Purpose Registers
     */
    public static GPRegister getR(int i) {
        return R[i];
    }
    /**
     * Convenience shortcut for R[0]
     */
    public static final GPRegister R0 = R[0];
    /**
     * Convenience shortcut for R[1]
     */
    public static final GPRegister R1 = R[1];

    static private GPRegister[] initRegisters() {
        GPRegister [] res = new GPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new GPRegister("R" + i, i);
        }
        return res;
    }

    public GPRegister UseFirstAvailableRegister(){
        for(int i=2;i<16;i++){
            if(R[i].available()){
                R[i].use();
                return R[i];
            }
        }
        throw new IllegalArgumentException("no Register Available");
    }

    public boolean OneRegisterAvailable(){
        for(int i=2;i<16;i++){
            if(R[i].available()){
                return true;
            }
        }
        return false;
    }

    public GPRegister StoreRegister(int i,DecacCompiler compiler){
        if(i<2 || i>15){
            throw new IllegalArgumentException("You have to Store a GPRegister (between R2 and R15)");
        }
        if(R[i].available()){
            throw new IllegalArgumentException("You have to Store a GPRegister which is unavailable");
        }
        compiler.addInstruction(new PUSH(R[i]));
        return R[i];
    }

    public void PopOnRegister(GPRegister R,DecacCompiler compiler){
        compiler.addInstruction(new POP(R));
    }
}
