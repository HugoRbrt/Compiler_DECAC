package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl49
 * @date 01/01/2022
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();
    
    private boolean parse = false;
    private boolean verification = false;
    private boolean noCheck = false;
    private int registers = 16;
    
    public void parseArgs(String[] args) throws CLIException {
        
        for (k = 0; k < args.length; k++) {
            
            if ( args[k].equals("-r") && ( k+1 < args.length ) ) {
                // we verify is the next argument is a correct number
                // of registers
                checkRegisters(args[k+1]);
            } else if ( args[k].equals("-r") && ( k+1 >= args.length )) {
                // no number of registers can be recognized
                displayUsage();
            } else {
                processArg(args, k);
            }
        }

        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }
        // A FAIRE: modifier pour le cas general
        sourceFiles.add(new File(args[0]));

        //throw new UnsupportedOperationException("not yet implemented");
    }

    protected void displayUsage() {
        throw new IllegalArgumentException("decac [[-p | -v] [-n] \
			[-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]");
    }

    // private function to process the arguments (except -r X)
    private void processArg(String[] args, int k) {
        String arg = args[k];
        
        if (arg.equals("-p")) {
            // -p and -v are uncompatible
	    if (verification) { displayUsage(); } else { parse = true; }
        }
        }
        if (arg.equals("-v")) {
            // -v and -p are uncompatible
            if (parse) { displayUsage(); } else { verification = true; }
        }
        if (arg.equals("-n")) {
            noCheck = true;
        }

        
            
        }
    }
        
    // Treats the argument following "-r" to get the correct number of registers
    private void checkRegisters(String nbRegisterString) {
        if String.isNumeric(nbRegistersString) {
            registers = Integer.parseInt(nbRegistersString);
        } else {
            displayUsage();
        }
    }

}
