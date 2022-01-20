package fr.ensimag.ima.pseudocode;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
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
    public void normalUseTest() {

        int indexMax = 13;
        //initialization with 13 registers
        Register bench = new Register("TestBench", indexMax);
        DecacCompiler mockCompiler = mock(DecacCompiler.class);

        for (int j = 2; j < indexMax; j++) {
            assertEquals(true, Register.getR(j).available());
            assertEquals(0, Register.getR(j).getNbPushOnRegister(), "ALLO"+Integer.toString(j));
        }
        // table to get all the registers before freeing them
        GPRegister[] registerTab = new GPRegister[indexMax-3];
        
        
        /* Test 1 : using all registers but the last one */
        // we want to fill the registers R2 to RMAX - 1
        for (int k = 0; k < indexMax - 3;  k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        //checking that it was done correctly
        for (int j = 2; j < indexMax-1; j++) {
            assertEquals(false, Register.getR(j).available());
            assertEquals(0, Register.getR(j).getNbPushOnRegister(), "ALLO"+Integer.toString(j));
        }
        
        assertEquals(true, bench.getR(indexMax-1).available());
        assertEquals(0, Register.getR(indexMax-1).getNbPushOnRegister());
        
        /* Test 2 : freeing all the gotten registers */
        for (int k = 0; k < indexMax - 3;  k++) {
            bench.freeRegister(registerTab[k], mockCompiler);
        }
        
        // checking that it was done correctly
        for (int j = 2; j < indexMax; j++) {
            assertEquals(true, Register.getR(j).available());
            assertEquals(0, Register.getR(j).getNbPushOnRegister());
        }
    }
    
    @Test
    public void intensiveUseTest() {

        int indexMax = 5;
        //initialization with 13 registers
        Register bench = new Register("TestBench", indexMax);
        DecacCompiler mockCompiler = mock(DecacCompiler.class);
        System.out.println(bench.debugDisplay());
        // table to get all the registers before freeing them
        GPRegister[] registerTab = new GPRegister[indexMax+1];
        
        /* Test 1 : using all registers but the last one */
        // we want to fill the registers R2 to RMAX - 1
        // and push one time on R2 to R5
        for (int k = 0; k < indexMax + 1;  k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        // checking that it was done correctly
        for (int j = 2; j < indexMax; j++) {
            assertEquals(false, Register.getR(j).available());
        }
        
        for (int j = 2; j <= 3; j++) {
            assertEquals(1, Register.getR(j).getNbPushOnRegister());
        }
        for (int j = 6; j < indexMax; j++) {
            assertEquals(0, Register.getR(indexMax-1).getNbPushOnRegister());
        }
        
        /* Test 2 : freeing all the gotten registers */
        for (int j = 2; j <= 5;  j++) {
            bench.freeRegister(registerTab[j], mockCompiler);
            assertEquals(0, Register.getR(j).getNbPushOnRegister());
            assertEquals(false, Register.getR(j).available());
        }
        
        // checking that it was done correctly
        for (int k = 0; k < indexMax - 3; k++) {
            bench.freeRegister(registerTab[k], mockCompiler);
            assertEquals(true, Register.getR(k).available());
            assertEquals(0, Register.getR(k).getNbPushOnRegister());
        }
    }

    
    @Test
    public void useAllRegistersForMethodInit() {

        int indexMax = 13;
        //initialization with 13 registers
        Register bench = new Register("TestBench", indexMax);
        DecacCompiler mockCompiler = mock(DecacCompiler.class);
        System.out.println(bench.debugDisplay());
        // table to get all the registers before freeing them
        GPRegister[] registerTab = new GPRegister[3 * indexMax];
        
        /* Test 1 : using all registers but the last one */
        // we want to fill the registers R2 to RMAX - 1
        for (int k = 0; k < 3 * indexMax;  k++) {
            registerTab[k] = bench.getRegister(mockCompiler);
        }
        
        bench.useAllRegisters();
        
        // checking that it was done correctly
        for (int j = 2; j < indexMax; j++) {
            assertEquals(false, Register.getR(j).available());
            assertEquals(0, Register.getR(j).getNbPushOnRegister());
        }
    }

}       
