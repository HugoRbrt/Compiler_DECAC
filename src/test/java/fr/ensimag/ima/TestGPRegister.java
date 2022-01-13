package fr.ensimag.ima.pseudocode;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.GPRegister;
import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;

/**
 * Initial version : 13/1/2022
 * @author Paul
 */
public class TestGPRegister {
    
    private static final Logger LOG = Logger.getLogger(TestGPRegister.class);
            
    private static void fillingRegistersTest(int indexMax) {

        Register bench = new Register("TestBench", indexMax);
        
        LOG.trace("Initial register bench" + bench.debugDisplay());
        
        GPRegister[] registerTab = new GPRegister[indexMax + 5];
        
        for (int k = 2; k < indexMax + 5; k++) {
            LOG.trace("Loop n°" + Integer.toString(k) + ":\n");
            registerTab[k] = bench.getRegisterWithoutCompiler();
            LOG.trace("Getting a register: " + bench.debugDisplay());
        }
        
        for (int k = indexMax - 1; k > 1; k--) {
            bench.freeRegisterWithoutCompiler(registerTab[k]);
            LOG.trace("Freeing a register: " + bench.debugDisplay());
        }
        
        for (int i = 0; i < 2; i++) {
            bench.freeRegisterWithoutCompiler(registerTab[indexMax-1]);
            LOG.trace("Freeing a register: " + bench.debugDisplay());
        }

        for (int k = 2; k < indexMax + 5; k++) {
            LOG.trace("Loop n°" + Integer.toString(k) + ":\n");
            registerTab[k] = bench.getRegisterWithoutCompiler();
            LOG.trace("Getting a register: " + bench.debugDisplay());
        }
        
        for (int k = indexMax - 1; k > 1; k--) {
            bench.freeRegisterWithoutCompiler(registerTab[k]);
            LOG.trace("Freeing a register: " + bench.debugDisplay());
        }
        
        for (int i = 0; i < 9; i++) {
            bench.freeRegisterWithoutCompiler(registerTab[indexMax-1]);
            LOG.trace("Freeing a register: " + bench.debugDisplay());
        }
    }


    public static void main(String[] args) {
        // Test visuel : on doit voir se remplir les registres de 0
        // jusqu'au dernier qui accumule des entiers
        
        // ensuite la libération rend vide tous les registres sauf le dernier
        
        // bis repetita
        fillingRegistersTest(7);
    }
}
        