package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;
import org.apache.commons.lang.Validate;

/**
 * Definition of a method
 *
 * @author gl49
 * @date 01/01/2022
 */
public class MethodDefinition extends ExpDefinition {
    private final Signature signature;
    private final int index;
    private EnvironmentExp parameters;
    private Label label;

    @Override
    public boolean isMethod() {
        return true;
    }

    public Label getLabel() {
        Validate.isTrue(label != null,
                "setLabel() should have been called before");
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public MethodDefinition asMethodDefinition(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }
    
    /**
     * 
     * @param type Return type of the method
     * @param location Location of the declaration of the method
     * @param signature List of arguments of the method
     * @param index Index of the method in the class. Starts from 0.
     */
    public MethodDefinition(Type type, Location location, Signature signature, int index, ClassDefinition currentClass) {
        super(type, location);
        this.signature = signature;
        this.index = index;
        EnvironmentExp parent;
        if (currentClass != null) {
            parent = currentClass.getMembers();
        } else {
            parent = null;
        }
        this.parameters = new EnvironmentExp(parent);
    }

    public Signature getSignature() {
        return signature;
    }

    public EnvironmentExp getParameters() {return parameters; }

    @Override
    public String getNature() {
        return "method";
    }

    @Override
    public boolean isExpression() {
        return false;
    }

}
