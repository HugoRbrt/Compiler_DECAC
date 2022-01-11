package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Dictionary associating built-in types to their names.
 *
 * Built-ins are added during the compiler's initialization.
 *
 * Uses the singleton pattern to guarantee the existence of only one instance
 * of the dictionary.
 *
 * @author Troy
 * @date 07/01/2022
 */
public class EnvironmentType {
    private static EnvironmentType instance = new EnvironmentType();
    private Map<Symbol, TypeDefinition> envTypes = new HashMap<>();

    private EnvironmentType() {}

    public static EnvironmentType getEnvTypes() {
        return instance;
    }

    public TypeDefinition get(Symbol s, Location location) throws ContextualError {
        if (!envTypes.containsKey(s)) {
            throw new ContextualError("Type does not exist.", location);
        }
        return envTypes.get(s);
    }

    public TypeDefinition get(Symbol s) {
        return envTypes.get(s);
    }

    public void put(Symbol symb, TypeDefinition type) {
        envTypes.put(symb, type);
    }


}
