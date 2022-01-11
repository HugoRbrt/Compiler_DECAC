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
        when(t2.isInt()).thenReturn(true);
        when(t1.sameType(t2)).thenReturn(true);
        assertTrue(ContextTools.assignCompatible(envType, t1, t2));
    }

    @Test
    public void testAssignCompatibleFalse() {
        EnvironmentType envType = EnvironmentType.getEnvTypes();
        when(t2.isInt()).thenReturn(true);
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
        when(t1.isFloat()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertTrue(ContextTools.typeArithOp(compiler, t1, t2, null).isFloat());
    }

    @Test
    public void testTypeArithFloat1() throws ContextualError {
        when(t1.isFloat()).thenReturn(true);
        when(t2.isFloat()).thenReturn(true);
        assertTrue(ContextTools.typeArithOp(compiler, t1, t2, null).isFloat());
    }

    @Test
    public void testTypeArithOther() {
        assertThrows(ContextualError.class, ()-> {ContextTools.typeArithOp(compiler, t1, t2, null);});
    }

    @Test
    public void testTypeCmpInt() throws ContextualError {
        String op = "";
        when(t1.isInt()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertTrue(ContextTools.typeCmpOp(compiler, op, t1, t2, null).isBoolean());
    }

    @Test
    public void testTypeCmpFloat() throws ContextualError {
        String op = "";
        when(t1.isFloat()).thenReturn(true);
        when(t2.isFloat()).thenReturn(true);
        assertTrue(ContextTools.typeCmpOp(compiler, op, t1, t2, null).isBoolean());
    }

    @Test
    public void testTypeCmpIntFloat() throws ContextualError {
        String op = "";
        when(t1.isInt()).thenReturn(true);
        when(t2.isFloat()).thenReturn(true);
        assertTrue(ContextTools.typeCmpOp(compiler, op, t1, t2, null).isBoolean());
    }

    @Test
    public void testTypeCmpEqBool() throws ContextualError {
        String op = "==";
        when(t1.isBoolean()).thenReturn(true);
        when(t2.isBoolean()).thenReturn(true);
        assertTrue(ContextTools.typeCmpOp(compiler, op, t1, t2, null).isBoolean());
    }

    @Test
    public void testTypeCmpEqIntBool() {
        String op = "==";
        when(t1.isInt()).thenReturn(true);
        when(t2.isBoolean()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeCmpOp(compiler, op, t1, t2, null);});
    }

    @Test
    public void testTypeCmpString() {
        String op = "";
        when(t1.isString()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeCmpOp(compiler, op, t1, t2, null);});
    }

    @Test
    public void testTypeBool() throws ContextualError {
        when(t1.isBoolean()).thenReturn(true);
        when(t2.isBoolean()).thenReturn(true);
        assertTrue(ContextTools.typeBoolOp(compiler, t1, t2, null).isBoolean());
    }

    @Test
    public void testTypeBoolInt() {
        when(t1.isBoolean()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeBoolOp(compiler, t1, t2, null);});
    }
}
