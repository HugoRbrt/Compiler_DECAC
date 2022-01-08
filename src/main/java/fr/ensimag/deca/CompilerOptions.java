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
                try{
                    checkRegisters(args[k+1]);
                } catch (CLIException e) {
                    throw e;
                }
                
            } else if ( args[k].equals("-r") && ( k+1 >= args.length )) {
                // no number of registers will be recognized
                throw new CLIException("");
            } else {
                try{
                    processArg(args, k);
                } catch (CLIException e) {
                    throw e;
                }
            }
            
        }

        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        if(warnings){
            logger.setLevel(Level.WARN);
        }
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
        
        // if no arguments were seen, we explain how decac should be used
        if (args.length == 0) {
            throw new CLIException("");
        }
        
        // if no file was written
        if (sourceFiles.size() == 0) {
            if (!(printBanner && ( parallel || (debug != 0) || parse ||
                    verification || noCheck || warnings || 
                    (registers != 16) ) )) {
                // if printBanner was written but no other options
                throw new CLIException("");
            }
            // else no problem : we have -b   
        }

        //throw new UnsupportedOperationException("not yet implemented");
    }

    protected void displayUsage() {
        System.out.println("Usage : decac [[-p | -v] [-n]" +
			"[-r X] [-d]* [-P] [-w] <fichier deca>...] | [-b]");
        
        System.out.println("-b  (banner): prints the team banner");
        System.out.println("-p  (parse): stops at the tree building step and " +
                "displays its decompilation (i.e. one source file should " +
                "output a syntaxically correct deca program");
        System.out.println("-v  (verification): stops decac " +
                "after the verification test");
        System.out.println("-n  (no check): deletes programs that" +
                "are incorrect, or correct but cannot be executed" +
                " due to machine performances");
        System.out.println("-r X (registers) : limits the number of " +
                "registers to R0 ... R{X-1} with 4 <= X <= 16");
        System.out.println("-d (debug) : activates debug traces. " +
                "Repeat several time for diferent traces level: " +
                "INFO, DEBUG, TRACE");
        System.out.println("-P  (parallel) : if several source files "+
                "are given, starts their parallel compilations");
        System.out.println("-w  (warnings) : enables warning messages during "+
                "compilation");
    }


    // function to process the arguments (except -r X)
    private void processArg(String[] args, int k)
        throws CLIException {
        
        String arg = args[k];
        
        if (arg.equals("-p")) {
            // -p and -v are uncompatible
	    if (verification) {
                throw new CLIException("");
            } else {
                parse = true;
            }
        }
        
        else if (arg.equals("-v")) {
            // -v and -p are uncompatible
            if (parse) {
                throw new CLIException("");
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
            throw new CLIException("");
        }
        
    }
        
    // Treats the argument following "-r" to get the correct number of registers
    private void checkRegisters(String nbRegistersString)
        throws CLIException {
        
        int nbRegisters = -1;
        try {
            nbRegisters = Integer.parseInt(nbRegistersString);
        } catch (NumberFormatException nfe) {
            throw new CLIException("");
        }
        if ((nbRegisters < 4) || (nbRegisters > 16)) {
                throw new CLIException("");
        } else {
            registers = nbRegisters;
        }
    }
    
    // debug function
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
