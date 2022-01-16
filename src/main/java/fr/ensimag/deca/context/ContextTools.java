package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;

/**
 * Tools for checking the validity of binary operation types.
 *
 * @author Teimur, Troy
 * @date 10/01/2022
 */
public class ContextTools {
    /**
     *
     *
     * @param envType
     * @param t1
     * @param t2
     * @return
     */
    public static boolean assignCompatible(EnvironmentType envType, Type t1, Type t2) {
        return (t1.isFloat() && t2.isInt()) || subtype(envType, t2, t1);
    }

    public static boolean castCompatible(EnvironmentType envType, Type t1, Type t2) {
        return (assignCompatible(envType, t1, t2) || assignCompatible(envType, t2, t1));
    }

    public static boolean subtype(EnvironmentType envType, Type t2, Type t1) {
        if (t1.sameType(t2)) {
            return true;
        }
        if (t2.isNull() && t1.isClass()) {
            return true;
        }
        if (t1.isClass() && t2.isClass()) {
            ClassType c1 = (ClassType) t1;
            ClassType c2 = (ClassType) t2;
            return c2.isSubClassOf(c1);
        }
        return false;
    }

    public static Type typeArithOp(DecacCompiler compiler, Type t1, Type t2,
            Location location) throws ContextualError {
        if (t1.isInt() && t2.isInt()) {
            return t1;
        }
        if ((t1.isInt() || t1.isFloat()) && (t2.isInt() || t2.isFloat())) {
            return compiler.getEnvTypes().get(compiler.getSymbTable().create("float")).getType();
        }
        throw new ContextualError("(RULE 3.33) Illegal operand type.", location);
    }

    public static Type typeArithModulo(DecacCompiler compiler, Type t1, Type t2,
                                       Location location) throws ContextualError {
        if (t1.isInt() && t2.isInt()) {
            return t1;
        }
        throw new ContextualError("(RULE 3.33) Illegal operand type.", location);
    }

    public static Type typeBoolOp(DecacCompiler compiler, Type t1, Type t2,
                                   Location location) throws ContextualError {
        if (!(t1.isBoolean() && t2.isBoolean())) {
            throw new ContextualError("(RULE 3.33) Illegal operand type.", location);
        }
        return compiler.getEnvTypes().get(compiler.getSymbTable().create("boolean")).getType();
    }

    public static Type typeCmpOp(DecacCompiler compiler, String op, Type t1, Type t2,
                                  Location location) throws ContextualError {
        if ((t1.isInt() || t1.isFloat()) && (t2.isInt() || t2.isFloat())) {
            return compiler.getEnvTypes().get(compiler.getSymbTable().create("boolean")).getType();
        }
        if (op.equals("==") || op.equals("!=")) {
            if (!(t1.isBoolean() && t2.isBoolean())) {
                throw new ContextualError("(RULE 3.33) Illegal operand type.", location);
            }
            return compiler.getEnvTypes().get(compiler.getSymbTable().create("boolean")).getType();
        }
        throw new ContextualError("(RULE 3.33) Illegal operand type.", location);
    }

    public static Type typeUnaryMinus(DecacCompiler compiler, Type t1,
            Location location) throws ContextualError {
        if (t1.isInt()) {
            return compiler.getEnvTypes().get(compiler.getSymbTable().create("int")).getType();
        }
        if (t1.isFloat()) {
            return compiler.getEnvTypes().get(compiler.getSymbTable().create("float")).getType();
        }
        throw new ContextualError("(RULE 3.37) Illegal operand type.", location);
    }

    public static Type typeUnaryNot(DecacCompiler compiler, Type t1,
            Location location) throws ContextualError {
        if (t1.isBoolean()) {
            return compiler.getEnvTypes().get(compiler.getSymbTable().create("boolean")).getType();
        }
        throw new ContextualError("(RULE 3.37) Illegal operand type.", location);
    }
}
