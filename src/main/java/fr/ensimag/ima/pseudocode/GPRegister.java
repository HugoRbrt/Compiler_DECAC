package fr.ensimag.ima.pseudocode;

/**
 * General Purpose Register operand (R0, R1, ... R15).
 * 
 * @author Ensimag
 * @date 01/01/2022
 */
public class GPRegister extends Register {
    
    private int number;

    private boolean availability = true;

    /**
     * @return the number of the register, e.g. 12 for R12.
     */
    public int getNumber() {
        return number;
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
    public void liberate(){
        assert(!availability);
        availability=true;
    }

}
