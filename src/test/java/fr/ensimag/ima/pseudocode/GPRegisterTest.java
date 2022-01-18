package fr.ensimag.ima.pseudocode;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Initial version : 13/1/2022
 * @author Paul
 */
public class GPRegisterTest {
    
    @Test
    public void fillingRegistersTest() {

        int indexMax = 13;
        //initialization with 13 registers
        Register bench = new Register("TestBench", indexMax);
        
        // table to get the registers concerned by loop
        GPRegister[] registerTab = new GPRegister[indexMax + 5];
        
        // getting the registers in order 17 times
        for (int k = 2; k < indexMax + 5; k++) {
            registerTab[k] = bench.getRegisterWithoutCompiler();
        }
        
        for (int k = indexMax - 1; k > 1; k--) {
            bench.freeRegisterWithoutCompiler(registerTab[k]);
        }
        
        assertEquals(4, registerTab[indexMax].getNbPushOnRegister());
        assertEquals(true, registerTab[3].available());
        
        for (int i = 0; i < 2; i++) {
            bench.freeRegisterWithoutCompiler(registerTab[indexMax-1]);
        }

        for (int k = 2; k < indexMax + 5; k++) {
            registerTab[k] = bench.getRegisterWithoutCompiler();
        }
        
        for (int k = indexMax - 1; k > 1; k--) {
            bench.freeRegisterWithoutCompiler(registerTab[k]);
        }
        
        for (int i = 0; i < 9; i++) {
            bench.freeRegisterWithoutCompiler(registerTab[indexMax-1]);
        }
        
        assertEquals(true, registerTab[12].available());
        assertEquals(0, registerTab[12].getNbPushOnRegister());
    }

}       
