package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class TestEnvironmentExp {

    @Test
    public void testDeclareParentEnvironment() throws EnvironmentExp.DoubleDefException {
        SymbolTable symbolTable = new SymbolTable();
        EnvironmentExp envExp = new EnvironmentExp(null);
        EnvironmentExp envExp2 = new EnvironmentExp(null);
        // Check that we can create a symbol which was already created in the parent environment (with same definition)
        envExp.declare(symbolTable.create("x"),
                mock(ExpDefinition.class));
        envExp2.declare(symbolTable.create("x"),
                mock(ExpDefinition.class));
        assertThrows(EnvironmentExp.DoubleDefException.class, () -> {envExp2.declare(symbolTable.create("x"),
                mock(ExpDefinition.class));});
    }

    @Test
    public void testGet() throws EnvironmentExp.DoubleDefException {
        SymbolTable symbolTable = new SymbolTable();
        EnvironmentExp envExp = new EnvironmentExp(null);
        // Check that we get the same answer when we try to get 2 times the same symbol
        envExp.declare(symbolTable.create("y"),
                mock(ExpDefinition.class));
        assertEquals(envExp.get(symbolTable.create("y")),
                envExp.get(symbolTable.create("y")));
        // Check that we get null when the symbol does not exist in the environment
        assertNull(envExp.get(symbolTable.create("x")));
    }

}
