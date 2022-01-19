package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructionsARM.mov;
import fr.ensimag.ima.pseudocode.instructionsARM.svc;
import fr.ensimag.ima.pseudocode.instructionsARM.push;
import fr.ensimag.ima.pseudocode.instructionsARM.pop;
import fr.ensimag.ima.pseudocode.instructionsARM.cmp;
import fr.ensimag.ima.pseudocode.instructionsARM.movmi;
import fr.ensimag.ima.pseudocode.instructionsARM.submi;
import fr.ensimag.ima.pseudocode.instructionsARM.blmi;
import fr.ensimag.ima.pseudocode.instructionsARM.blpl;
import fr.ensimag.ima.pseudocode.instructionsARM.poplo;
import fr.ensimag.ima.pseudocode.instructionsARM.movlo;
import fr.ensimag.ima.pseudocode.instructionsARM.sub;
import fr.ensimag.ima.pseudocode.instructionsARM.add;
import fr.ensimag.ima.pseudocode.instructionsARM.bl;
import fr.ensimag.ima.pseudocode.instructionsARM.bllo;
import fr.ensimag.ima.pseudocode.instructionsARM.movgt;
import fr.ensimag.ima.pseudocode.instructionsARM.moveq;
import fr.ensimag.ima.pseudocode.instructionsARM.addeq;
import fr.ensimag.ima.pseudocode.instructionsARM.pushgt;
import fr.ensimag.ima.pseudocode.instructionsARM.pusheq;
import fr.ensimag.ima.pseudocode.instructionsARM.blgt;
import fr.ensimag.ima.pseudocode.instructionsARM.bleq;
import fr.ensimag.ima.pseudocode.instructionsARM.ldr;
import fr.ensimag.ima.pseudocode.instructionsARM.addgt;



import fr.ensimag.ima.pseudocode.ARMRegister;


import java.util.HashMap;

/**
 * Initial version : 19/1/2022
 * @author gl49
 * Class used to generate all the code relative to functions in ARM
 * programs' execution
 */

public class ARMFunctionManager {


    public ARMFunctionManager() {
        super();
    }

    /**
     * FunctionManager generates codes for functions that are put there
     */
    public void genCodeFunctionManager(DecacCompiler compiler) {
        genCodePrintRelatif(compiler);
        genCodeDivide(compiler);
        genCodePrintNumber(compiler);
        genCodePrintInt(compiler);
        genCodePrintMinus(compiler);
        genCodePrint0(compiler);
        genCodePrint1(compiler);
        genCodePrint2(compiler);
        genCodePrint3(compiler);
        genCodePrint4(compiler);
        genCodePrint5(compiler);
        genCodePrint6(compiler);
        genCodePrint7(compiler);
        genCodePrint8(compiler);
        genCodePrint9(compiler);
        genCodeEndProgram(compiler);
    }

    public void genCodeEndProgram(DecacCompiler compiler) {
        Label _end = new Label("_end", true);
        compiler.addLabel(_end);
        compiler.addInstruction(new mov(ARMRegister.getR(0), 0));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 1));
        compiler.addInstruction(new svc(0));
        compiler.addARMComment("end program");

    }

    public void genCodePrintRelatif(DecacCompiler compiler) {
        compiler.addARMComment(" ////////////////// Display integer in register r0 ///////////////// ");
        Label _printrelatif = new Label("_printrelatif", true);
        compiler.addLabel(_printrelatif);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1), ARMRegister.lr));
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 0));
        compiler.addInstruction(new movmi(ARMRegister.getR(1), 0));
        compiler.addInstruction(new submi(ARMRegister.getR(0), ARMRegister.getR(1), ARMRegister.getR(0)));
        compiler.addInstruction(new blmi("_printminus"));
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 0));
        compiler.addInstruction(new blmi("_printnumber"));
        compiler.addInstruction(new blpl("_printnumber"));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1), ARMRegister.lr));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodeDivide(DecacCompiler compiler) {
        compiler.addARMComment(" ////////////// Euclidian division between r0 and r1.  ///////////// ");
        compiler.addARMComment(" ////////////////// Quotient in r2 and rest in r0  ///////////////// ");        
        Label _divide = new Label("_divide", true);
        compiler.addLabel(_divide);
        compiler.addARMBlock("Z = 0");
        compiler.addARMBlock("EQ = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new poplo(ARMRegister.getR(3)));
        compiler.addInstruction(new movlo(ARMRegister.pc, ARMRegister.getR(3)));
        compiler.addInstruction(new sub(ARMRegister.getR(0), ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new add(ARMRegister.getR(2), ARMRegister.getR(2), 1));
        compiler.addInstruction(new bl("_divide"));
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrintNumber(DecacCompiler compiler) {
        compiler.addARMComment(" ////////////// Perform integer printing with euclidian division between r0.  ///////////// ");      
        Label _printnumber = new Label("_printnumber", true);
        compiler.addLabel(_printnumber);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1), ARMRegister.getR(2), ARMRegister.lr));
        compiler.addARMBlock("Z = 0");
        compiler.addARMBlock("EQ = 0");
        compiler.addInstruction(new mov(ARMRegister.getR(4), ARMRegister.getR(0)));
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 10));
        compiler.addARMComment("If lower than 10 we can directly proceed to printing");      
        compiler.addInstruction(new bllo("_printint"));
        compiler.addInstruction(new poplo(ARMRegister.getR(0), ARMRegister.getR(1), ARMRegister.getR(2), ARMRegister.lr));
        compiler.addInstruction(new movlo(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMComment("Otherwise we perform the euclidian division by 10");      
        compiler.addInstruction(new movgt(ARMRegister.getR(1), 10));
        compiler.addInstruction(new moveq(ARMRegister.getR(1), 10));
        compiler.addInstruction(new movgt(ARMRegister.getR(2), 0));
        compiler.addInstruction(new moveq(ARMRegister.getR(2), 0));
        compiler.addInstruction(new movgt(ARMRegister.getR(3), ARMRegister.pc));
        compiler.addInstruction(new moveq(ARMRegister.getR(3), ARMRegister.pc));
        compiler.addInstruction(new addgt(ARMRegister.getR(3), ARMRegister.getR(3), 8));
        compiler.addInstruction(new addeq(ARMRegister.getR(3), ARMRegister.getR(3), 8));
        compiler.addInstruction(new pushgt(ARMRegister.getR(3)));
        compiler.addInstruction(new pusheq(ARMRegister.getR(3)));
        compiler.addInstruction(new blgt("_divide"));
        compiler.addInstruction(new bleq("_divide"));
        compiler.addInstruction(new cmp(ARMRegister.getR(4), 10));
        compiler.addInstruction(new movgt(ARMRegister.getR(1), ARMRegister.getR(0)));
        compiler.addInstruction(new moveq(ARMRegister.getR(1), ARMRegister.getR(0)));
        compiler.addInstruction(new movgt(ARMRegister.getR(0), ARMRegister.getR(2)));
        compiler.addInstruction(new moveq(ARMRegister.getR(0), ARMRegister.getR(2)));
        compiler.addInstruction(new blgt("_printnumber"));
        compiler.addInstruction(new cmp(ARMRegister.getR(4), 10));
        compiler.addInstruction(new bleq("_printnumber"));
        compiler.addInstruction(new movlo(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new bllo("_printint"));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1), ARMRegister.getR(2), ARMRegister.lr));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrintInt(DecacCompiler compiler) {
        compiler.addARMComment(" /////// Perform integer printing for integer between 0 and 9  ////// ");      
        Label _printint = new Label("_printint", true);
        compiler.addLabel(_printint);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.lr));
        
        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 0));
        compiler.addInstruction(new bleq("_print0"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 1));
        compiler.addInstruction(new bleq("_print1"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 2));
        compiler.addInstruction(new bleq("_print2"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 3));
        compiler.addInstruction(new bleq("_print3"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 4));
        compiler.addInstruction(new bleq("_print4"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 5));
        compiler.addInstruction(new bleq("_print5"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 6));
        compiler.addInstruction(new bleq("_print6"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 7));
        compiler.addInstruction(new bleq("_print7"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 8));
        compiler.addInstruction(new bleq("_print8"));

        compiler.addARMBlock("Z = 0");
        compiler.addInstruction(new cmp(ARMRegister.getR(0), 9));
        compiler.addInstruction(new bleq("_print9"));

        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.lr));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrintMinus(DecacCompiler compiler) {
        compiler.addARMComment(" ////////////////////////// Print minus  /////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _printminus = new Label("_printminus", true);
        compiler.addLabel(_printminus);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_minus"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenminus"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _minus = new Label("_minus", true);
        compiler.addLabel(_minus);
        compiler.addARMBlock(".ascii \"-\"");
        compiler.addARMBlock("_lenminus = . - _minus");
    }

    public void genCodePrint0(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 0  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print0 = new Label("_print0", true);
        compiler.addLabel(_print0);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int0"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint0"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int1 = new Label("_int0", true);
        compiler.addLabel(_int1);
        compiler.addARMBlock(".ascii \"0\"");
        compiler.addARMBlock("_lenint0 = . - _int0");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint1(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 1  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print1 = new Label("_print1", true);
        compiler.addLabel(_print1);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int1"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint1"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int1 = new Label("_int1", true);
        compiler.addLabel(_int1);
        compiler.addARMBlock(".ascii \"1\"");
        compiler.addARMBlock("_lenint1 = . - _int1");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint2(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 2  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print2 = new Label("_print2", true);
        compiler.addLabel(_print2);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int2"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint2"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int2 = new Label("_int2", true);
        compiler.addLabel(_int2);
        compiler.addARMBlock(".ascii \"2\"");
        compiler.addARMBlock("_lenint2 = . - _int2");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint3(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 3  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print3 = new Label("_print3", true);
        compiler.addLabel(_print3);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int3"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint3"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int3 = new Label("_int3", true);
        compiler.addLabel(_int3);
        compiler.addARMBlock(".ascii \"3\"");
        compiler.addARMBlock("_lenint3 = . - _int3");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint4(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 4  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print4 = new Label("_print4", true);
        compiler.addLabel(_print4);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int4"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint4"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int4 = new Label("_int4", true);
        compiler.addLabel(_int4);
        compiler.addARMBlock(".ascii \"4\"");
        compiler.addARMBlock("_lenint4 = . - _int4");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint5(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 5  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print5 = new Label("_print5", true);
        compiler.addLabel(_print5);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int5"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint5"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int5 = new Label("_int5", true);
        compiler.addLabel(_int5);
        compiler.addARMBlock(".ascii \"5\"");
        compiler.addARMBlock("_lenint5= . - _int5");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint6(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 6  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print6 = new Label("_print6", true);
        compiler.addLabel(_print6);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int6"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint6"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int6 = new Label("_int6", true);
        compiler.addLabel(_int6);
        compiler.addARMBlock(".ascii \"6\"");
        compiler.addARMBlock("_lenint6 = . - _int6");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint7(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 7  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print7 = new Label("_print7", true);
        compiler.addLabel(_print7);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int7"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint7"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int7 = new Label("_int7", true);
        compiler.addLabel(_int7);
        compiler.addARMBlock(".ascii \"7\"");
        compiler.addARMBlock("_lenint7 = . - _int7");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint8(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 8  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print8 = new Label("_print8", true);
        compiler.addLabel(_print8);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int8"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint8"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int8 = new Label("_int8", true);
        compiler.addLabel(_int8);
        compiler.addARMBlock(".ascii \"8\"");
        compiler.addARMBlock("_lenint8 = . - _int8");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }

    public void genCodePrint9(DecacCompiler compiler) {
        compiler.addARMComment(" ///////////////////////////// Print 9  ////////////////////////////// ");      
        compiler.addARMBlock(".text");
        Label _print9 = new Label("_print9", true);
        compiler.addLabel(_print9);
        compiler.addInstruction(new push(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.getR(0), 1));
        compiler.addInstruction(new ldr(ARMRegister.getR(1), "=_int9"));
        compiler.addInstruction(new ldr(ARMRegister.getR(2), "=_lenint9"));
        compiler.addInstruction(new mov(ARMRegister.getR(7), 4));
        compiler.addInstruction(new svc(0));
        compiler.addInstruction(new pop(ARMRegister.getR(0), ARMRegister.getR(1)));
        compiler.addInstruction(new mov(ARMRegister.pc, ARMRegister.lr));
        compiler.addARMBlock(".data");
        Label _int9 = new Label("_int9", true);
        compiler.addLabel(_int9);
        compiler.addARMBlock(".ascii \"9\"");
        compiler.addARMBlock("_lenint9 = . - _int9");
        compiler.addARMComment(" /////////////////////////////////////////////////////////////////// ");
    }
}