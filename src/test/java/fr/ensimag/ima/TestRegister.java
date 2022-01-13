package fr.ensimag.deca.ima;

import fr.ensimag.deca.ima.Register;
import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl49
 */
public class TestRegister {
    
    private static final Logger LOG = Logger.getLogger(TestCodeAnalyzer.class);
            
    private static int fillingRegistersTest(int indexMax) {

        Register bench = new Register("TestBench", indexMax);
        
        LOG.trace("Initial register bench" + bench.displayDebug());
        
        Register rX;
        for (int k = 2, k < indexMax + 2 ; k++) {
            LOG.trace("Loop n°" + Integer.toString(k) + ":\n");
            LOG.trace("Get register: " + bench.displayDebug());
            rX = bench.getRegister();
            LOG.trace("Free register: " + bench.getRegister());
            bench.freeRegister(rX))
        }

    }


    public static void main(String[] args) {
        fillingRegistersTest(16);
        fillingRegistersTest(3);
    }
}
        