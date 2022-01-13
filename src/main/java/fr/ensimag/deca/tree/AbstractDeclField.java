package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

public abstract class AbstractDeclField extends Tree {

    /**
     * Returns non-terminal AbstractInitialization
     * @return
     *      the concrete class's AbstractInitialization field
     */
    public abstract AbstractInitialization getInit();
}
