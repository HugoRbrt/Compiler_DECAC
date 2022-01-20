package fr.ensimag.ima.pseudocode;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;

/**
 * Initial version : 13/1/2022
 * @author Paul
 */
public class RegisterTest {
    
    @Test
    public void fillingRegistersTest() {

        int indexMax = 13;
        //initialization with 13 registers
        Register bench = new Register("TestBench", indexMax);
        DecacCompiler mockCompiler = mock(DecacCompiler.class);
        
        System.out.println(bench.debugDisplay());
        
        // table to get the registers concerned by loop
        GPRegister[] registerTab = new GPRegister[indexMax - 3];
        
        // getting the registers in order 17 times
        for (int k = 0; k < indexMax - 3; k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        
        System.out.println(bench.debugDisplay());
        
        for (int k = 0; k < indexMax - 3; k++) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        
        System.out.println(bench.debugDisplay());
        
        /* Intensive use of registers */
        registerTab = new GPRegister[indexMax+1];
        for (int k = 0; k < indexMax + 1; k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }

        System.out.println(bench.debugDisplay());
        for (int k = 0; k <= 5; k++) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        for (int k = 6; k < indexMax-3; k++) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        
        System.out.println(bench.debugDisplay());
        
        for (int k = indexMax - 2; k < indexMax + 1; k++ ) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        
        
        System.out.println(bench.debugDisplay());
        
        /* Using all the registers a certain amount of time then pop them */
        registerTab = new GPRegister[3 * indexMax];
        
        for (int k = 0; k < 3 * indexMax; k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        
        System.out.println(bench.debugDisplay());
        
        bench.useAllRegisters();
        
        System.out.println(bench.debugDisplay());
    }

}       
