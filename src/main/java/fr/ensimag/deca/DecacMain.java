package fr.ensimag.deca;

import java.io.File;
import org.apache.log4j.Logger;
import java.util.concurrent.*;
import java.lang.Runtime;
import java.util.LinkedList;
import java.lang.Runnable;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl49
 * @date 01/01/2022
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            System.out.println("g9/gl49");
        }

        if (options.getParallel()) {
            ExecutorService executor = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors());
            LinkedList<Future> listFuture = new LinkedList<Future>();
            for (File source : options.getSourceFiles()){
                listFuture.add(executor.submit(new DecacCompiler(options, source)));
            }
            for(Future future : listFuture){
                try{
                    future.get();//waiting for the end of the compiling task for each files
                }catch (CancellationException e) {
                    System.err.println("Error during parrallel compilation : CancellationException");
                    System.exit(1);
                }catch (ExecutionException e) {
                    System.err.println("Error during parrallel compilation : ExecutionException");
                    System.exit(1);
                }catch (InterruptedException e) {
                    System.err.println("Error during parrallel compilation : InterruptedException");
                    System.exit(1);
                }
            }
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
