package fr.ensimag.ima.pseudocode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class IMAProgram {
    protected final LinkedList<LinkedList<AbstractLine>> lines = new LinkedList<LinkedList<AbstractLine>>();

    protected void initLines() {
        if(lines.isEmpty()){
            lines.add(new LinkedList<AbstractLine>());
        }
    }

    public void add(AbstractLine line) {
        initLines();
        lines.get(0).add(line);
    }

    public void addComment(String s) {
        initLines();
        lines.get(0).add(new Line(s));
    }

    public void addARMComment(String s) {
        String v = "/*" + s + "*/";
        initLines();
        lines.get(0).add(new Line(v));
    }

    public void addLabel(Label l) {
        initLines();
        lines.get(0).add(new Line(l));
    }

    public void addInstruction(Instruction i) {
        initLines();
        lines.get(0).add(new Line(i));
    }

    public void addInstruction(Instruction i, String s) {
        initLines();
        lines.get(0).add(new Line(null, i, s));
    }

    /**
     * Append the content of program p to the current program. The new program
     * and p may or may not share content with this program, so p should not be
     * used anymore after calling this function.
     */
    public void append(IMAProgram p) {
        LinkedList<AbstractLine> result = new LinkedList<AbstractLine>();
        p.lines.forEach(result::addAll);
        initLines();
        lines.get(0).addAll(result);
    }

    /**
     * Add a line at the front of the program.
     */
    public void addFirst(Line l) {
        initLines();
        lines.get(0).addFirst(l);
    }

    /**
     * Add a line at the end of the program.
     */
    public void addOther(String s) {
        initLines();
        lines.get(0).add(new ARMLine(s));
    }

    /**
     * Add a line at the end of the program.
     */
    public void addListInstruction(LinkedList<AbstractLine> l) {
        lines.addLast(l);
    }


    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    public void display(PrintStream s) {
        for(LinkedList<AbstractLine> listline : lines){
            for (AbstractLine l: listline) {
                l.display(s);
            }
        }
    }

    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    public void ARMdisplay(PrintStream s) {
        for(LinkedList<AbstractLine> listline : lines){
            for (AbstractLine l: listline) {
                l.ARMdisplay(s);
            }
        }
    }

    /**
     * Return the program in a textual form readable by IMA as a String.
     */
    public String display() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(out);
        display(s);
        return out.toString();
    }

    /**
     * Return the program in a textual form readable by ARM as a String.
     */
    public String ARMdisplay() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(out);
        ARMdisplay(s);
        return out.toString();
    }

    public void addFirst(Instruction i) {
        addFirst(new Line(i));
    }
}
