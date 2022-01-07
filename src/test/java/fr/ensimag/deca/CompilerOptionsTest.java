package fr.ensimag.deca;

import fr.ensimag.deca.CompilerOptions;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Junit class to test the parsing of the different options we can give to
 * the compiler.
 * JUnit will run each method annotated with @Test.
 */

public class CompilerOptionsTest{

    @Test
    public void testNoBCompleteOptions() {
        String[] args = { "-w", "-d", "-d", "-n"
                + "-P", "-r", "4", "-v", "oui.deca", "yes.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        print(comptOpt);
        assertEquals("printBanner :", compOpt.getPrintBanner(), false);
        // Checks that we detected -r with 13
        assertEquals("registers :", compOpt.getRegisters(), 4);
        assertEquals("parse :", compOpt.getDebug(), false);
        assertEquals("debug :", compOpt.getDebug(), 2);
        assertEquals("parallel :", compOpt.getParallel(), true);
        assertEquals("no check :", compOpt.getNoCheck(), true);
        assertEquals("verification :", compOpt.getVerification(), true);
        assertEquals("warnings :", compOpt.getWarnings(), true);
        assertEquals("number of files", compOpt.size(), 2);
    }
    
    @Test
    public void testOnlyB() {
        String[] args = { "-b" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        print(comptOpt);
        // Checks that we detected the -b
        assertEquals("printBanner :", compOpt.getPrintBanner(), true);
        // Checks that we did not detect anyother options
        assertEquals("registers :", compOpt.getRegisters(), 16);
        assertEquals("debug :", compOpt.getDebug(), 0);
        assertEquals("parse :", compOpt.getDebug(), false);
        assertEquals("parallel :", compOpt.getParallel(), false);
        assertEquals("no check :", compOpt.getNoCheck(), false);
        assertEquals("verification :", compOpt.getVerification(), false);
        assertEquals("warnings :", compOpt.getWarnings(), false);
        assertEquals("number of files", compOpt.size(), 0);
    }
    

    @Test
    public void testValidR() {
        String[] args = { "-r", "13", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        print(comptOpt);
        assertEquals("printBanner :", compOpt.getPrintBanner(), false);
        // Checks that we detected -r with 13
        assertEquals("registers :", compOpt.getRegisters(), 13);
        assertEquals("debug :", compOpt.getDebug(), false);
        assertEquals("parse :", compOpt.getDebug(), 0);
        assertEquals("parallel :", compOpt.getParallel(), false);
        assertEquals("no check :", compOpt.getNoCheck(), false);
        assertEquals("verification :", compOpt.getVerification(), false);
        assertEquals("warnings :", compOpt.getWarnings(), false);
        assertEquals("number of files", compOpt.size(), 1);
    }
    
    
    @Test
    public void testValidPm() {
        String[] args = { "-p", "-n", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        print(comptOpt);
        assertEquals("printBanner :", compOpt.getPrintBanner(), false);
        // Checks that we detected -r with 13
        assertEquals("registers :", compOpt.getRegisters(), 13);
        assertEquals("debug :", compOpt.getDebug(), 0);
        assertEquals("parse :", compOpt.getDebug(), true);
        assertEquals("parallel :", compOpt.getParallel(), false);
        assertEquals("no check :", compOpt.getNoCheck(), false);
        assertEquals("verification :", compOpt.getVerification(), false);
        assertEquals("warnings :", compOpt.getWarnings(), false);
        assertEquals("number of files", compOpt.size(), 1);
    }
    
    @Test
    public void testNoBCompleteOptions() {
        String[] args = { "-d", "-d", "-d", "-d", "-d", 
            "oui.deca", "yes.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        print(comptOpt);
        assertEquals("printBanner :", compOpt.getPrintBanner(), false);
        // Checks that -d is limited to level 3
        assertEquals("registers :", compOpt.getRegisters(), 4);
        assertEquals("parse :", compOpt.getDebug(), false);
        assertEquals("debug :", compOpt.getDebug(), 3);
        assertEquals("parallel :", compOpt.getParallel(), false);
        assertEquals("no check :", compOpt.getNoCheck(), false);
        assertEquals("verification :", compOpt.getVerification(), false);
        assertEquals("warnings :", compOpt.getWarnings(), false);
        assertEquals("number of files", compOpt.size(), 2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testBandP() {
        // checks that -b is uncompatible with other options
        String[] args = { "-b", "-P" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testBandFile {
        // checks that -b is uncompatible with a file
        String[] args = { "oui.deca", "-b"};
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testVandPm(){
        // checks that -v and -p are uncompatible
        String[] args = { "-v", "-n", "-p" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRValue {
        // checks that -r X must respect 4 <= X <= 16 
        String[] args = { "-r", "3" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRString {
        // checks that -r must be followed by an integer
        String[] args = { "-r", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidREmpty {
        // checks that -r should not be the final string
        String[] args = { "-v", "-r" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFile {
        // checks that a file ending differently than .deca is not accepted
        String[] args = { "oui.ass" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNoArgs() {
        // checks that no argument throw an exception
        String[] args = {};
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        print(comptOpt);
    }
}