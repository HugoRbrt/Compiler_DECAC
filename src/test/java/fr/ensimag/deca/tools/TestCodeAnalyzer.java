package fr.ensimag.deca.tools;

import fr.ensimag.deca.tools.CodeAnalyzer;

/**
 *
 * @author gl49
 */
public class TestCodeAnalyzer {
    
    
    /* Must return 7 */
    private static int testOne() {
        
        private codeAnalyzer = new CodeAnalyzer();
        
        codeAnalyzer.incrPushCount(1); //stack:1
        codeAnalyzer.incrPushCount(1); //stack:2
        codeAnalyzer.incrPopCount(1);  //stack:1
        codeAnalyzer.incrPushCount(2); //stack:3
        codeAnalyzer.incrPopCount(1);  //stack:2
        codeAnalyzer.incrPopCount(5);  //stack:7
        
        return codeAnalyzer.getStackSizeInstructions();
    }
    
    /* Must return 2 */
    private static int testTwo() {
        
        private codeAnalyzer = new CodeAnalyzer();
        
        codeAnalyzer.incrPushCount(1); //stack:1
        codeAnalyzer.incrPushCount(1); //stack:2
        codeAnalyzer.incrPopCount(1);  //stack:1
        codeAnalyzer.incrPopCount(1);  //stack:0
        codeAnalyzer.incrPushCount(1); //stack:1
        
        return codeAnalyzer.getStackSizeInstructions();
    }
    
    
    /* Must print an AssertException */
    private static testThree() {
        private codeAnalyzer = new CodeAnalyzer();
        
        try {
            codeAnalyzer.incrPopCount(1);
        } except(AssertException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testOne();
        testTwo();
        testThree();
    }
}
        