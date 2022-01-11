package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestContextTools {
    DecacCompiler compiler = new DecacCompiler(null, null);
    Type t1 = Mockito.mock(Type.class);
    Type t2 = Mockito.mock(Type.class);

    @Test
    public void testAssignCompatibleTrue() {
        EnvironmentType envType = EnvironmentType.getEnvTypes();
        when(t1.isFloat()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertTrue(ContextTools.assignCompatible(envType, t1, t2));
    }

    @Test
    public void testAssignCompatibleSubtype() {
        EnvironmentType envType = EnvironmentType.getEnvTypes();
        when(t1.isFloat()).thenReturn(false);
        when(t2.isInt()).thenReturn(true);
        when(t1.sameType(t2)).thenReturn(true);
        assertTrue(ContextTools.assignCompatible(envType, t1, t2));
    }

    @Test
    public void testAssignCompatibleFalse() {
        EnvironmentType envType = EnvironmentType.getEnvTypes();
        when(t1.isFloat()).thenReturn(false);
        when(t2.isInt()).thenReturn(true);
        when(t1.sameType(t2)).thenReturn(false);
        assertFalse(ContextTools.assignCompatible(envType, t1, t2));
    }

    @Test
    public void testTypeArithInt() throws ContextualError {
        when(t1.isInt()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertTrue(ContextTools.typeArithOp(compiler, t1, t2, null).isInt());
    }

    @Test
    public void testTypeArithFloat0() throws ContextualError {
        when(t1.isInt()).thenReturn(false);
        when(t1.isFloat()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        when(t2.isFloat()).thenReturn(false);
        assertTrue(ContextTools.typeArithOp(compiler, t1, t2, null).isFloat());
    }

    @Test
    public void testTypeArithFloat1() throws ContextualError {
        when(t1.isInt()).thenReturn(false);
        when(t1.isFloat()).thenReturn(true);
        when(t2.isInt()).thenReturn(false);
        when(t2.isFloat()).thenReturn(true);
        assertTrue(ContextTools.typeArithOp(compiler, t1, t2, null).isFloat());
    }

    @Test
    public void testTypeArithOther() throws ContextualError {
        when(t1.isInt()).thenReturn(false);
        when(t1.isFloat()).thenReturn(false);
        when(t2.isInt()).thenReturn(false);
        when(t2.isFloat()).thenReturn(false);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeArithOp(compiler, t1, t2, null);});
    }

//    @Test
//    public void testTypeCmpInt() {
//        when(t1.isInt()).thenReturn(true);
//        when(t2.isInt()).thenReturn(true);
//        assertTrue(ContextTools.typeCmpOp(compiler, op, t1, t2, null).isBoolean());
//    }
}
