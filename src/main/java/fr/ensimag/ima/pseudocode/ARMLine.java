package fr.ensimag.ima.pseudocode;

import java.io.PrintStream;

/**
 * Line of code in an IMA program.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ARMLine extends Line {
    public ARMLine(String comment) {
        super(comment);
    }

    @Override
    void display(PrintStream s) {
        boolean tab = false;
        if (label != null) {
            s.print(label);
            s.print(":");
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
            s.print(comment);//allows to add instructions with comment (every real comments must begin by a ';')
        }
        s.println();
    }
}



