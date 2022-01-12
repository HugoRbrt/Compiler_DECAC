package fr.ensimag.deca.tools;

import fr.ensimag.deca.tools.CodeAnalyzer;
import org.apache.log4j.Logger;
/**
 *
 * @author gl49
 */
public class TestCodeAnalyzer {
    
    private static final Logger LOG = Logger.getLogger(TestCodeAnalyzer.class);
            
    /* Must return 7 */
    private static int testOne() {
        
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        
        codeAnalyzer.incrPushCount(1); //stack:1
        codeAnalyzer.incrPushCount(1); //stack:2
        codeAnalyzer.incrPopCount(1);  //stack:1
        codeAnalyzer.incrPushCount(2); //stack:3
        codeAnalyzer.incrPopCount(1);  //stack:2
        codeAnalyzer.incrPushCount(5); //stack:7
        codeAnalyzer.incrPopCount(3);  //stack:4
        //needed:7
        return codeAnalyzer.getStackSizeInstructions();
    }
    
    /* Must return 2 */
    private static int testTwo() {
        
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        
        codeAnalyzer.incrPushCount(1); //stack:1
        codeAnalyzer.incrPushCount(1); //stack:2
        codeAnalyzer.incrPopCount(1);  //stack:1
        codeAnalyzer.incrPopCount(1);  //stack:0
        codeAnalyzer.incrPushCount(1); //stack:1
        //needed:2
        return codeAnalyzer.getStackSizeInstructions();
    }
    
    
    /* Must print an AssertException */
    private static void testThree() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        
        try {
            codeAnalyzer.incrPopCount(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(testOne());
        System.out.println(testTwo());
        testThree();
    }
}
        