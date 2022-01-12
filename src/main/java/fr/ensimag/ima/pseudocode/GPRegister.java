package fr.ensimag.ima.pseudocode;

/**
 * General Purpose Register operand (R0, R1, ... R15).
 * 
 * @author Ensimag
 * @date 01/01/2022
 */
public class GPRegister extends Register {
    
    private int number;

    /**
     * Available if not used for saving a result
     */
    private boolean availability = true;
    
    /**
     * Accessed by push if the register was used in a PUSH expression
     * With this, we can keep track of the future POP
     */
    private boolean needPush = false;

    /**
     * @return the number of the register, e.g. 12 for R12.
     */
    public int getNumber() {
        return number;
    }
    
    public void setNeedPush(boolean needPush) {
        this.needPush = needPush;
    }
    
    public boolean getNeedPush() {
        return needPush;
    }
    
    
    /**
     * @return true if the register is available for use, else false
     */
    public boolean available(){
        return availability;
    }

    
    GPRegister(String name, int number) {
        super(name);
        this.number = number;
    }

    /**
     * Makes the register unavailable for saves
     */
    public void use(){
        assert(availability);
        availability=false;
    }

    /**
     * Free an unavailable register to make it usable for saves
     */
    public void free(){
        assert(!availability);
        availability=true;
    }   
    

}
