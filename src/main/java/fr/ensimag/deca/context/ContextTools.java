package fr.ensimag.deca.context;

public class ContextTools {
    public static boolean assignCompatible(EnvironmentType envType, Type t1, Type t2) {
        return (t1.isFloat() && t2.isInt()) || subtype(envType, t2, t1);
    }

    private static boolean subtype(EnvironmentType envType, Type t2, Type t1) {
        return false;
        // TO IMPLEMENT
    }
}
