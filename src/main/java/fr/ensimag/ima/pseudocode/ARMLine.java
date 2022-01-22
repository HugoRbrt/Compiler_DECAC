package fr.ensimag.ima.pseudocode;

import fr.ensimag.ima.pseudocode.ARMProgram;
import fr.ensimag.ima.pseudocode.Line;

import java.io.PrintStream;


/**
 * Line of code in an IMA Program
 *
 * @author gl49
 * @date0 01/01/2022
 */
public class ARMLine extends Line {
    public ARMLine(String comment) {
        super(comment);}

    @Override
    void display(PrintStream s) {
        boolean tab = false;
        if (label != null) {
            s.print(label);
            s.println(":");
            tab = true;
        }
        if (instruction != null) {
            s.print("\t");
            instruction.display(s);
            tab = true;
        }
        if (comment != null) {
            if (tab) {
                s.print("\t");
            }
            s.println(comment); //allows to add instruction with comment
        }
        s.println();
    }
}
