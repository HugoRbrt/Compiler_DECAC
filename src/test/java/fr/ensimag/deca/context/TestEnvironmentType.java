package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class TestEnvironmentType {

    @Test
    public void testGet() throws ContextualError {
        EnvironmentType envT = EnvironmentType.getEnvTypes();
        SymbolTable symbT= new SymbolTable();
        SymbolTable.Symbol symb = symbT.create("x");
        envT.put(symb, Mockito.mock(TypeDefinition.class));
        assertInstanceOf(TypeDefinition.class, envT.get(symb));

    }

    @Test
    public void testGetFail() throws ContextualError {
        assertThrows(ContextualError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                EnvironmentType envT = EnvironmentType.getEnvTypes();
                SymbolTable symbT= new SymbolTable();
                SymbolTable.Symbol symb = symbT.create("x");
                envT.get(symb, null);
            }
        });
    }
}
