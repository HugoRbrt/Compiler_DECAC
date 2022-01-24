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
    private int nbPushOnRegister = 0;

    /**
     * @return the number of the register, e.g. 12 for R12.
     */
    public int getNumber() {
        return number;
    }
    
    public void incrNbPushOnRegister(int nbPush) {
        nbPushOnRegister += nbPush;
    }
    
    public void decrNbPushOnRegister(int nbPop) {
        nbPushOnRegister -= nbPop;
    }
    
    public int getNbPushOnRegister() {
        return nbPushOnRegister;
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
        this.availability = true;
    }

    /**
     * Makes the register unavailable for saves
     */
    public void use(){
        availability=false;
    }

    /**
     * Free an unavailable register to make it usable for saves
     */
    public void free(){
        availability=true;
    }

    public String debugDisplay(){
        String s;
        if (availability) {
            // available
            s = " [ ]";
        } else {        
            if (nbPushOnRegister < 0) {
                s = " [X]"; // abnormal state
            } else {
                // used with a certain number of PUSH on it
                s = " [" + Integer.toString(nbPushOnRegister) + "]";
            }
        }
        
        return s;
    }    

    // Try not to abuse this function and use the incr and decr methods
    // We only want to use this with 0
    public void setNbPushOnRegister(int number) {
        assert(number == 0);
        nbPushOnRegister = 0;
    }
}
