package fr.ensimag.deca;

import fr.ensimag.deca.CompilerOptions;
import fr.ensimag.deca.CLIException;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit class to test the parsing of the different options we can give to
 * the compiler.
 * JUnit will run each method annotated with @Test.
 */

public class CompilerOptionsTest{
   
    @Test
    public void testNoBCompleteOptions() throws CLIException {
        String[] args = { "-w", "-d", "-d", "-n",
                "-P", "-r", "4", "-v", "oui.deca", "yes.deca", "oui.deca"};
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);
    
        assertEquals(2, compOpt.getDebug());
        assertEquals(true, compOpt.getParallel());
        assertEquals(false, compOpt.getPrintBanner());
        assertEquals(2, compOpt.getSourceFiles().size()); 
        
        assertEquals(false, compOpt.getParse());
        assertEquals(true, compOpt.getVerification());
        assertEquals(true, compOpt.getNoCheck());
        assertEquals(4, compOpt.getRegisters());
        assertEquals(true, compOpt.getWarnings());
        
        assertEquals(false, compOpt.getArmBool());

    }
    
    @Test
    public void testOnlyB() throws CLIException {
        String[] args = { "-b" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);

        assertEquals(0, compOpt.getDebug());
        assertEquals(false, compOpt.getParallel());
        assertEquals(true, compOpt.getPrintBanner());
        assertEquals(0, compOpt.getSourceFiles().size()); 
        
        assertEquals(false, compOpt.getParse());
        assertEquals(false, compOpt.getVerification());
        assertEquals(false, compOpt.getNoCheck());
        assertEquals(16, compOpt.getRegisters());
        assertEquals(false, compOpt.getWarnings());
        
        assertEquals(false, compOpt.getArmBool());
    }
    
    @Test
    public void testValidR() throws CLIException {
        String[] args = { "-r", "13", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);

        assertEquals(0, compOpt.getDebug());
        assertEquals(false, compOpt.getParallel());
        assertEquals(false, compOpt.getPrintBanner());
        assertEquals(1, compOpt.getSourceFiles().size()); 
        
        assertEquals(false, compOpt.getParse());
        assertEquals(false, compOpt.getVerification());
        assertEquals(false, compOpt.getNoCheck());
        assertEquals(13, compOpt.getRegisters());
        assertEquals(false, compOpt.getWarnings());
        
        assertEquals(false, compOpt.getArmBool());
        
    }
    
    @Test
    public void testValidPm() throws CLIException {
        String[] args = { "-p", "-n", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);

        assertEquals(0, compOpt.getDebug());
        assertEquals(false, compOpt.getParallel());
        assertEquals(false, compOpt.getPrintBanner());
        assertEquals(1, compOpt.getSourceFiles().size()); 
        
        assertEquals(true, compOpt.getParse());
        assertEquals(false, compOpt.getVerification());
        assertEquals(true, compOpt.getNoCheck());
        assertEquals(16, compOpt.getRegisters());
        assertEquals(false, compOpt.getWarnings());
        
        assertEquals(false, compOpt.getArmBool());
    }
    
    @Test
    public void testLimitD() throws CLIException {
        String[] args = { "-d", "-d", "yes.deca", "-d", "-d", "-d", 
            "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        compOpt.parseArgs(args);

        assertEquals(3, compOpt.getDebug());
        assertEquals(false, compOpt.getParallel());
        assertEquals(false, compOpt.getPrintBanner());
        assertEquals(2, compOpt.getSourceFiles().size()); 
        
        assertEquals(false, compOpt.getParse());
        assertEquals(false, compOpt.getVerification());
        assertEquals(false, compOpt.getNoCheck());
        assertEquals(16, compOpt.getRegisters());
        assertEquals(false, compOpt.getWarnings());
        
        assertEquals(false, compOpt.getArmBool());
        
    }
    
    @Test
    public void testBandP() throws CLIException {
        // checks that -b is uncompatible with other options
        String[] args = { "-b", "-P" };
        CompilerOptions compOpt = new CompilerOptions();
        

        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    @Test
    public void testBandFile() throws CLIException {
        // checks that -b is uncompatible with a file
        String[] args = { "oui.deca", "-b"};
        CompilerOptions compOpt = new CompilerOptions();
        
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    
    @Test
    public void testVandPm() throws CLIException {
        // checks that -v and -p are uncompatible
        String[] args = { "-v", "-n", "-p" };
        CompilerOptions compOpt = new CompilerOptions();
        
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    @Test
    public void testAandR() throws CLIException {
        // checks that no argument throw an exception
        String[] args = {"-n", "-d", "-d", "-a", "-r", "16", "oui.deca"};
        CompilerOptions compOpt = new CompilerOptions();
        
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }  
    
    @Test
    public void testInvalidRValue() throws CLIException {
        // checks that -r X must respect 4 <= X <= 16 
        String[] args = { "-r", "3" };
        CompilerOptions compOpt = new CompilerOptions();
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    @Test
    public void testInvalidRString() throws CLIException {
        // checks that -r must be followed by an integer
        String[] args = { "-r", "oui.deca" };
        CompilerOptions compOpt = new CompilerOptions();
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    @Test
    public void testInvalidREmpty() throws CLIException {
        // checks that -r should not be the final string
        String[] args = { "-v", "-r" };
        CompilerOptions compOpt = new CompilerOptions();
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    @Test
    public void testInvalidFile() throws CLIException {
        // checks that a file ending differently than .deca is not accepted
        String[] args = { "oui.ass" };
        CompilerOptions compOpt = new CompilerOptions();
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    @Test
    public void testNoArgs() throws CLIException {
        // checks that no argument throw an exception
        String[] args = {};
        CompilerOptions compOpt = new CompilerOptions();
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});
    }
    
    // last tests to get rid to test -b -r 16
    @Test
    public void testBandRegisters() throws CLIException {
        String[] args = { "-b", "-r", "16"};
        CompilerOptions compOpt = new CompilerOptions();
        assertThrows(CLIException.class, () -> {compOpt.parseArgs(args);});

        
    }
    
}
