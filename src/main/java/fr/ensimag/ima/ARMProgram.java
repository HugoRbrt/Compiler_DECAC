package fr.ensimag.ima.pseudocode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import fr.ensimag.ima.pseudocode.ARMLine;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ARMProgram extends GenericProgram {

    @Override
    public void addComment(String s) {
        String v = "/*" + s + "*/";
        lines.add(new ARMLine(v));
    }

    /**
     * Add a name block
     */
    public void addARMBlock(String s) {
        lines.add(new ARMLine(s));
    }
}
