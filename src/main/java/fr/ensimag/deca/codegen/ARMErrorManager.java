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
import fr.ensimag.ima.pseudocode.instructionsARM.*;
import fr.ensimag.ima.pseudocode.ARMRegister;




import java.util.HashMap;

/**
 * Initial version : 13/1/2022
 * @author gl49
 * Class used to generate all the code relative to Errors in ARM
 * programs' execution
 */

public class ARMErrorManager {

    /**
     * Labels for the different errors
     */
    private HashMap<String, Label> errorMap = new HashMap<String, Label>();


    public ARMErrorManager() {
        // adding all necessary errors to the error map
        Label divByZeroLabel = new Label("division_by_zero");
        errorMap.put("Division by zero", divByZeroLabel);
        //Label nullReferenceLabel = new Label("null_dereferencing");
        //errorMap.put("null_dereferencing", nullReferenceLabel);
        Label ioErrorLabel = new Label("input_output_error");
        errorMap.put("input_output", ioErrorLabel);
        //Label heapOverflowLabel = new Label("heap_overflow");
        //errorMap.put("heap_overflow", heapOverflowLabel);
        Label floatArithmeticOverflow = new Label("float_arithmetic_overflow");
        errorMap.put("Float arithmetic overflow", floatArithmeticOverflow);
        //Label noReturnLabel = new Label("no_return");
        //errorMap.put("no_return", noReturnLabel);
        Label impossibleConversionLabel = new Label("impossible_conversion");
        errorMap.put("impossible_conversion", impossibleConversionLabel);
        Label noInitializationAccessLabel = new Label("not_initialized_variable_access");
        errorMap.put("not_initialized_access", noInitializationAccessLabel);
        Label incorrectAssemblerLabel = new Label("incorrect_assembler");
        errorMap.put("incorrect_assembler", incorrectAssemblerLabel);

    }


    public Label getErrorLabelARM(String errorName) {
        return errorMap.get(errorName);
        // at this point, we do not manage errors
    }

    /**
     * ErrorManager generates codes for errors that are put there
     */
    public void genCodeErrorManagerARM(DecacCompiler compiler) {
        errorMap.forEach((name, label)
                -> genCodeErrorARM(label, "ERROR: " + name, compiler));
    }


    /**
     * @params label : a label to be attributed to the error
     * @params message : the message shown when error is encountered
     * @params compiler : the compiler to write in
     */
    private void genCodeErrorARM(Label label, String msg, DecacCompiler compiler) {
        compiler.addARMBlock(".data");
        compiler.addARMComment("- Error message --");
        compiler.addARMBlock(label + "_msg" + ": " + ".asciz " + "\"" + msg + "\\n\"");  //label with name of variable
        compiler.addARMBlock(".text");
        compiler.addARMBlock(label + ": ");  //label with name of variable
        compiler.addInstruction(new push(ARMRegister.lr));
        compiler.addInstruction(new ldr(ARMRegister.r0, "=" + label + "_msg"));
        compiler.addInstruction(new bl("printf"));
        compiler.addInstruction(new b("end"));
    }

}