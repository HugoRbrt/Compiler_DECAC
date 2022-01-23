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

import java.util.HashMap;

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
    private HashMap<String, Label> errorMap = new HashMap<String, Label>();


    public ErrorManager() {
        // adding all necessary errors to the error map
        Label stackOverflowLabel = new Label("stack overflow");
        errorMap.put("Stack overflow , a real one", stackOverflowLabel);
        Label divByZeroLabel = new Label("division by zero");
        errorMap.put("Division by zero", divByZeroLabel);
        Label nullReferenceLabel = new Label("null dereferencing");
        errorMap.put("null_dereferencing", nullReferenceLabel);
        Label ioErrorLabel = new Label("input output error");
        errorMap.put("input_output", ioErrorLabel);
        Label heapOverflowLabel = new Label("heap overflow");
        errorMap.put("heap_overflow", heapOverflowLabel);
        Label floatArithmeticOverflow = new Label("float arithmetic overflow");
        errorMap.put("Float arithmetic overflow", floatArithmeticOverflow);
        Label noReturnLabel = new Label("no return");
        errorMap.put("no_return", noReturnLabel);
        Label impossibleConversionLabel = new Label("illegal cast");
        errorMap.put("impossible_conversion", impossibleConversionLabel);
        Label noInitializationAccessLabel = new Label("not initialized variable access");
        errorMap.put("not_initialized_access", noInitializationAccessLabel);
        Label incorrectAssemblerLabel = new Label("incorrect assembler");
        errorMap.put("incorrect_assembler", incorrectAssemblerLabel);

    }


    public void setTstoArg(int arg) {
        tstoArg = arg;
    }

    public void setAddspArg(int arg) {
        addspArg = arg;
    }

    public Label getErrorLabel(String errorName) {
        return errorMap.get(errorName);
        // at this point, we do not manage errors
    }

    /**
     * ErrorManager generates codes for errors that are put there
     */
    public void genCodeErrorManager(DecacCompiler compiler) {
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addComment(" --------------------------------------------------");
            compiler.addComment("             Error messages");
            compiler.addComment(" --------------------------------------------------");
            errorMap.forEach((name, label)
                -> genCodeError(label, "ERROR: " + name, compiler));
        }
    }


    /**
     * Generates the initial code for the stack overflow test
     * @param compiler : the compiler to write in
     */
    public void addTstoCheck(DecacCompiler compiler) {
        // Start point for the program
        compiler.addFirstInstruction(new ADDSP(addspArg));
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addFirstInstruction(new BOV(errorMap.get("Stack overflow , a real one")));
            compiler.addFirstInstruction(new TSTO(tstoArg));
        }
    }


    /**
     * @params label : a label to be attributed to the error
     * @params message : the message shown when error is encountered
     * @params compiler : the compiler to write in
     */
    private void genCodeError(Label label, String msg, DecacCompiler compiler) {
        if (!compiler.getCompilerOptions().getNoCheck()) {
            compiler.addLabel(label);
            compiler.addInstruction(new WSTR(new ImmediateString(msg)));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

}
