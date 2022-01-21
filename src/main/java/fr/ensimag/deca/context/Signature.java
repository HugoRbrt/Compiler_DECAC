package fr.ensimag.deca.context;

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
        return sig.substring(0, sig.length()-2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        if (args.size() != signature.args.size()) return false;
        for (int i = 0; i < args.size() - 1; i++) {
            if (!args.get(i).equals(signature.args.get(i))) {
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
