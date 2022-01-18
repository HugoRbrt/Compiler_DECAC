package fr.ensimag.deca.tools;

import fr.ensimag.deca.tools.CodeAnalyzer;
import fr.ensimag.deca.tools.DecacInternalError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gl49
 */
public class CodeAnalyzerTest {
    
    /* Must return 7 */
    @Test
    public void testOne() {
        
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        
        codeAnalyzer.incrPushCount(1); //stack:1
        codeAnalyzer.incrPushCount(1); //stack:2
        codeAnalyzer.incrPopCount(1);  //stack:1
        codeAnalyzer.incrPushCount(2); //stack:3
        codeAnalyzer.incrPopCount(1);  //stack:2
        codeAnalyzer.incrPushCount(5); //stack:7
        codeAnalyzer.incrPopCount(3);  //stack:4
        //needed:7
        assertEquals(7, codeAnalyzer.getNeededStackSize());
    }
    
    /* Must return 2 */
    @Test
    public void testTwo() {
        
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        codeAnalyzer.incrPushCount(1); //stack:1
        codeAnalyzer.incrPushCount(1); //stack:2
        codeAnalyzer.incrPopCount(1);  //stack:1
        codeAnalyzer.incrPopCount(1);  //stack:0
        codeAnalyzer.incrPushCount(1); //stack:1
        //needed:2
        assertEquals(2, codeAnalyzer.getNeededStackSize());
    }
    
    
    /* Must return 0 */
    @Test
    public void testThree() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        
        codeAnalyzer.incrPopCount(1);
        
        assertEquals(0, codeAnalyzer.getNeededStackSize());
    }
}     