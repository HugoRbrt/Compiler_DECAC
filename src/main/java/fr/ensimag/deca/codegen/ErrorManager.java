package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.ERROR;

/**
 * Initial version : 13/1/2022
 * @author gl49
 * Class used to generate all the code relative to Errors in IMA
 * programs' execution
 */

public class ErrorManager {
    /**
     * tstoArg: necessary stack size for the program
     */
    private int tstoArg = 0;
    /**
     * addspArg: number of variables declared in the program
     */
    private int addspArg = 0;


    /**
     * Labels for the different errors
     */
    private final Label stackOverflowLabel = new Label("stack_overflow_error");
    private final Label divByZeroLabel = new Label("division_by_zero_error");
    private final Label nullReferenceLabel =
            new Label("null_dereferencing_error");
    private final Label ioErrorLabel = new Label("input_output_error");
    private final Label heapOverflowLabel = new Label("heap_overflow_error");

    public void setTstoArg(int arg) {
        tstoArg = arg;
    }

    public void setAddspArg(int arg) {
        addspArg = arg;
    }

    public Label getStackOverflowLabel() {
        return stackOverflowLabel;
    }

    public Label getDivByZeroLabel() {
        return divByZeroLabel;
    }

    public Label getNullReferenceLabel() {
        return nullReferenceLabel;
    }

    public Label getIOErrorLabel() {
        return ioErrorLabel;
    }

    public Label getHeapOverflowLabel() {
        return heapOverflowLabel;
    }

    /**
     * ErrorManager generates codes for errors that are put there
     */
    public void genCodeErrorManager(DecacCompiler compiler) {
        genCodeError(stackOverflowLabel, "ERROR : stack overflow", compiler);
        genCodeError(divByZeroLabel, "ERROR: division by zero", compiler);
        genCodeError(nullReferenceLabel, "ERROR: dereferencing null", compiler);
        genCodeError(ioErrorLabel, "ERROR: input/output error", compiler);
        genCodeError(heapOverflowLabel, "ERROR: heap overflow", compiler);
    }

    /**
     * Generates the initial code for the stack overflow test
     * @param compiler : the compiler to write in
     */
    public void addTstoCheck(DecacCompiler compiler) {
        // Start point for the program
        compiler.addFirstInstruction(new ADDSP(tstoArg));
        compiler.addFirstInstruction(new BOV(stackOverflowLabel));
        compiler.addFirstInstruction(new TSTO(addspArg));
    }


    /**
     * @params label : a label to be attributed to the error
     * @params message : the message shown when error is encountered
     * @params compiler : the compiler to write in
     */
    private void genCodeError(Label label, String msg, DecacCompiler compiler) {
        compiler.addComment("---- Error message ----");
        compiler.addLabel(label);
        compiler.addInstruction(new WSTR(new ImmediateString(msg)));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
    }

}
