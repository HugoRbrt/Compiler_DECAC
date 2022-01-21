package fr.ensimag.ima.pseudocode;

import org.apache.commons.lang.Validate;

/**
 * Representation of a label in IMA code. The same structure is used for label
 * declaration (e.g. foo: instruction) or use (e.g. BRA foo).
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class LabelARM extends Operand {
    private static int i=0;

    @Override
    public String toString() {
        return name;
    }

    public LabelARM(){
        this("label"+i);
        i++;
    }

    public LabelARM(String name) {
        super();
        Validate.isTrue(name.length() <= 1024, "Label name too long, not supported by IMA");
        Validate.isTrue(name.matches("^[a-zA-Z][a-zA-Z0-9_.]*$"), "Invalid label name " + name);
        this.name = name;
    }

    
    public LabelARM(String name, boolean arm) {
        super();
        Validate.isTrue(name.length() <= 1024, "Label name too long, not supported by ARM");
        Validate.isTrue(name.matches("^[_][a-zA-Z][a-zA-Z0-9_.]*$"), "Invalid label name " + name);
        this.name = name;
    }

    private String name;
}
