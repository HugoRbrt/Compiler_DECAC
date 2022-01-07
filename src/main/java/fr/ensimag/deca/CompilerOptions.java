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
    
    public boolean getParse() {
        return parse;
    }

    public boolean getVerification() {
        return verification;
    }
    
    public boolean getNoCheck() {
        return noCheck;
    }
    
    public int getRegisters() {
        return registers;
    }
    
    public boolean getWarnings() {
        return warnings;
    }
    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();
    
    private boolean parse = false;
    private boolean verification = false;
    private boolean noCheck = false;
    private int registers = 16;
    private boolean warnings = false;
    
    public void parseArgs(String[] args) throws CLIException {
        
        for (int k = 0; k < args.length; k++) {
            
            if ( args[k].equals("-r") && ( k+1 < args.length ) ) {
                // we try to associate the next argument to a number of
                // registers
                checkRegisters(args[k+1]);
            } else if ( args[k].equals("-r") && ( k+1 >= args.length )) {
                // no number of registers will be recognized
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
        // sourceFiles.add(new File(args[0]));

        // if no file was written
        if (sourceFiles.size() == 0) {
            if (!(printBanner && ( parallel || (debug != 0) || parse ||
                    verification || noCheck || warnings || 
                    (registers != 16) ) )) {
                // if printBanner was written but no other options
                displayUsage();
            }
            // else no problem : we have -b   
        }

        //throw new UnsupportedOperationException("not yet implemented");
    }

    protected void displayUsage() {
        throw new IllegalArgumentException("decac [[-p | -v] [-n]" +
			"[-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]");
    }

    // private function to process the arguments (except -r X)
    private void processArg(String[] args, int k) {
        String arg = args[k];
        
        if (arg.equals("-p")) {
            // -p and -v are uncompatible
	    if (verification) {
                displayUsage();
            } else {
                parse = true;
            }
        }
        
        else if (arg.equals("-v")) {
            // -v and -p are uncompatible
            if (parse) {
                displayUsage();
            } else {
                verification = true;
            }
        }
        
        else if (arg.equals("-n")) {
            noCheck = true;
        }

        else if (arg.equals("-d")) {
            debug++;
            // no higher log level than TRACE
            if (debug > TRACE) {
                debug = TRACE;
            }
        }
        
        else if (arg.equals("-P")) {
            parallel = true;
        }
        
        // we only accept files ending with the extension .deca
        else if (arg.endsWith(".deca")) {
            // if the file is already present
            // do not add it again
            File decaFile = new File(arg);
            if (! sourceFiles.contains(decaFile)) {
                sourceFiles.add(decaFile);
            }
        }
        
        else if (arg.equals("-w")) {
            warnings = true;
        }
        
        else {
            displayUsage();
        }
        
    }
        
    // Treats the argument following "-r" to get the correct number of registers
    private void checkRegisters(String nbRegistersString) {
        int nbRegisters = -1;
        try {
            nbRegisters = Integer.parseInt(nbRegistersString);
        } catch (NumberFormatException nfe) {
            displayUsage();
        }
        if ((nbRegisters < 4) || (nbRegisters > 16)) {
                displayUsage();
        } else {
            registers = nbRegisters;
        }
    }
    
    @Override
    public String toString() {
        String s = "CompilerOptions[\n";
        s += "-b (printBanner) :" + Boolean.toString(printBanner) + "\n";
        s += "-d (debug) :" + Integer.toString(debug) + "\n";
        s += "-P (parallel) :" + Boolean.toString(parallel) + "\n";
        s += "-v (verification) :" + Boolean.toString(verification) + "\n";
        s += "-p (parse) :" + Boolean.toString(parse) + "\n";
        s += "-n (noCheck) :" + Boolean.toString(noCheck) + "\n";
        s += "-w (warnings) :" + Boolean.toString(warnings) + "\n";
        s += "-r (registers) :" + Integer.toString(registers) + "\n";
        s += "-files : " + sourceFiles.toString() + "\n";
        s += "]";
        
        return s;
    }
    

}
