package fr.ensimag.ima.pseudocode;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.BOV;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;


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
    
    /**
     * @param d1 : needed stack size
     * @param d2 : number of declared variables
     * Adds the first instruction of TSTO check and ADDSP in the list of lines
     */
    public void addTstoCheck(int d1, int d2) {
        lines.addFirst(new Line(new ADDSP(d2)));
        lines.addFirst(new Line(new BOV(new Label("stack_overflow"))));
        lines.addFirst(new Line(new TSTO(d1)));
    }
    
    
}
