package fr.ensimag.deca.tools;

/**
 * Global code analyzer used during code generation to
 * compute the dynamic variables used for error portions
 * and label generation
 */


public class CodeAnalyzer {

    /**
    * Number that keeps track of declared variables
    */
    private int nbDeclaredVariables = 0;
    
    /**
    * Number that keeps track of the needed size of memory in
    * stack to compute the instructions
    */
    private int stackSizeInstructions = 0;
    
    /**
     * Special counter to keep track of a the number of PUSH - POP
     */
    private int diffPushPop = 0;
    
    public int getNbDeclaredVariables() {
        return nbDeclaredVariables;
    }
    
    public int getNeededStackSize() {
        return stackSizeInstructions + nbDeclaredVariables;
    }

    /**
    * @args nbPush : number of consecutive PUSH instructions to be declared
    */
    public void incrPushCount(int nbPush) {
        diffPushPop += nbPush;
        if (diffPushPop > stackSizeInstructions) {
            stackSizeInstructions = diffPushPop;
        }
    }

    /**
    * @args nbPop: number of consecutive POP instructions to be declared
    */
    public void incrPopCount(int nbPop) {
        diffPushPop -= nbPop;
        
        // Defensive programing
        if (diffPushPop < 0) {
            diffPushPop = 0;
        }
    }

    /**
     * @args nbVariables : number of declared variables in a row
     */
    public void incrDeclaredVariables(int nbVariables) {
        nbDeclaredVariables += nbVariables;
    }
    
}
