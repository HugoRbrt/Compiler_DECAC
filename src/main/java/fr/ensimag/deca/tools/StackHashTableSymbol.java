package fr.ensimag.deca.tools;

import java.util.HashMap;
import java.util.Map;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.Register;


/**
 * Manage unique symbols.
 * 
 * A Symbol contains the same information as a String, but the SymbolTable
 * ensures the uniqueness of a Symbol for a given String value. Therefore,
 * Symbol comparison can be done by comparing references, and the hashCode()
 * method of Symbols can be used to define efficient HashMap (no string
 * comparison or hashing required).
 * 
 * @author gl49
 * @date 01/01/2022
 */
public class StackHashTableSymbol {
    private Map<Symbol, RegisterOffset> map = new HashMap<Symbol, RegisterOffset>();
    private int size = 1;

    /**
     * Create or reuse a symbol.
     * 
     * If a symbol already exists with the same name in this table, then return
     * this Symbol. Otherwise, create a new Symbol and add it to the table.
     */
    public RegisterOffset get(Symbol name) {
        if (!map.containsKey(name)) {
            throw new IllegalArgumentException("symbol not found in StackHashTableSymbol");
        }
        return map.get(name);
    }

    public void put(Symbol name, Register R){
        map.put(name, new RegisterOffset(size, R)); // add 1 to avoid 0(GB) error
        size++;
    }


    @Override
    public String toString() {
        String s = "";
        for (Symbol symb: map.keySet()) {
            s += symb + " " + map.get(symb);
        }
        return s;
    }

}
