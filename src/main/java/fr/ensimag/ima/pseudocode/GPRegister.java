package fr.ensimag.ima.pseudocode;

/**
 * General Purpose Register operand (R0, R1, ... R15).
 * 
 * @author Ensimag
 * @date 01/01/2022
 */
public class GPRegister extends Register {
    /**
     * @return the number of the register, e.g. 12 for R12.
     */
    public int getNumber() {
        return number;
    }

    private int number;

    private boolean availability = true;

    GPRegister(String name, int number) {
        super(name);
        this.number = number;
    }

    public boolean available(){
        return availability;
    }

    public void use(){
        assert(availability);
        availability=false;
    }

    public void liberate(){
        assert(!availability);
        availability=true;
    }

}
