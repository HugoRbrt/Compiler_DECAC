package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl49
 * @date 01/01/2022
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public Signature(Type... types) {
        args.addAll(List.of(types));
    }

    public void add(Type t) {
        args.add(t);
    }

    public Type get(int i) { return args.get(i); }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Type t: args) {
            sb.append(t).append(", ");
        }
        String sig = sb.toString();
        return sig.length() == 0? sig : sig.substring(0, sig.length()-2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        if (args.size() != signature.args.size()) return false;
        for (int i = 0; i < args.size(); i++) {
            if (!ContextTools.assignCompatible(signature.args.get(i), args.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(args);
    }
}
