package fr.ensimag.ima;

import fr.ensimag.ima.pseudocode.Line;




/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class IMAProgram  extends GenericProgram{

    @Override
    public void addComment(String s) {
        lines.add(new Line(s));
    }
    
}
