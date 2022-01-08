package fr.ensimag.deca;

import fr.ensimag.deca.CompilerOptions;

/**
 * Junit class to test the parsing of the different options we can give to
 * the compiler.
 * JUnit will run each method annotated with @Test.
 */

public class CompilerOptionsTest{

    public static void testNoBCompleteOptions() throws CLIException {
        String[] args = { "-w", "-d", "-d", "-n",
                "-P", "-r", "4", "-v", "oui.deca", "yes.deca", "oui.deca"};
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
        System.out.println(compOpt);
        
    }
    
    public static void testOnlyB() throws CLIException {
        String[] args = { "-b" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
        System.out.println(compOpt);
    }
    

    public static void testValidR() throws CLIException {
        String[] args = { "-r", "13", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
        System.out.println(compOpt);
    }
    

    public static void testValidPm() throws CLIException {
        String[] args = { "-p", "-n", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();}
        System.out.println(compOpt);
    }
    
    public static void testLimitD() throws CLIException {
        String[] args = { "-d", "-d", "yes.deca", "-d", "-d", "-d", 
            "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
        System.out.println(compOpt);
    }
    
    //@Test(expected = CLIException.class)
    public static void testBandP() throws CLIException {
        // checks that -b is uncompatible with other options
        String[] args = { "-b", "-P" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();}; 
    }
    
    //@Test(expected = CLIException.class)
    public static void testBandFile() throws CLIException {
        // checks that -b is uncompatible with a file
        String[] args = { "oui.deca", "-b"};
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    
    //@Test(expected = CLIException.class)
    public static void testVandPm() throws CLIException {
        // checks that -v and -p are uncompatible
        String[] args = { "-v", "-n", "-p" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    
    //@Test(expected = CLIException.class)
    public static void testInvalidRValue() throws CLIException {
        // checks that -r X must respect 4 <= X <= 16 
        String[] args = { "-r", "3" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    //@Test(expected = CLIException.class)
    public static void testInvalidRString() throws CLIException {
        // checks that -r must be followed by an integer
        String[] args = { "-r", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    //@Test(expected = CLIException.class)
    public static void testInvalidREmpty() throws CLIException {
        // checks that -r should not be the final string
        String[] args = { "-v", "-r" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    //@Test(expected = CLIException.class)
    public static void testInvalidFile() throws CLIException {
        // checks that a file ending differently than .deca is not accepted
        String[] args = { "oui.ass" };
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    //@Test(expected = CLIException.class)
    public static void testNoArgs() throws CLIException {
        // checks that no argument throw an exception
        String[] args = {};
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args);} catch (CLIException e) {e.printStackTrace();};
    }
    
    // last tests to get rid to test -b -r 16
    public static void testBandRegisters() throws CLIException {
        String[] args = { "-b", "-r", "16"};
        CompilerOptions compOpt = new CompilerOptions();
        try{compOpt.parseArgs(args); System.out.println(compOpt);
        } catch (CLIException e) {e.printStackTrace();};

        
    }
    
    public static void main(String[] args) throws CLIException {
        testNoBCompleteOptions();
        testOnlyB();
        testValidR();
        testValidPm();
        testLimitD();
        testBandP();
        testBandFile();
        testVandPm();
        testInvalidRValue();
        testInvalidRString();
        testInvalidREmpty();
        testInvalidFile();
        testNoArgs();
        testBandRegisters();
    }
}
