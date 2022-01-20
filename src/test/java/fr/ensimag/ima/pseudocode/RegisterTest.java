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
        
        // table to get the registers concerned by loop
        GPRegister[] registerTab = new GPRegister[indexMax + 5];
        
        // getting the registers in order 17 times
        for (int k = 2; k < indexMax + 5; k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        
        for (int k = indexMax - 1; k > 1; k--) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        
        assertEquals(4, registerTab[indexMax].getNbPushOnRegister());
        assertEquals(true, registerTab[3].available());
        
        for (int i = 0; i < 2; i++) {
            bench.freeRegister(registerTab[indexMax-1], mockCompiler);
        }

        for (int k = 2; k < indexMax + 5; k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        
        for (int k = indexMax - 1; k > 1; k--) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        
        for (int i = 0; i < 9; i++) {
            bench.freeRegister(registerTab[indexMax-1], mockCompiler);
        }
        
        assertEquals(true, registerTab[12].available());
        assertEquals(0, registerTab[12].getNbPushOnRegister());
    }

}       
