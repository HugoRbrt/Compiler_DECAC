package fr.ensimag.deca.codegen;

import fr.ensimag.deca.tools.SymbolTable;

import java.util.HashMap;
import java.util.Map;


/**
 * Class that contains the values of string variables
 *
 * Will be used by the compiler to print these variables
 * @author Teimur
 * @date 01/01/2022
 */
public class StringIdentMap {

    private Map<SymbolTable.Symbol, String> strings = new HashMap<>();

    public StringIdentMap() {
    }

    public String getIdentString(SymbolTable.Symbol symbol) {
        if (!strings.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol has no string value yet");
        }

        return strings.get(symbol);
    }

    public void setIdentString(SymbolTable.Symbol symbol, String value) {
        strings.put(symbol, value);
    }

}
