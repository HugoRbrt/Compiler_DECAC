package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import org.junit.jupiter.api.Test;
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
    public void testTypeArithModulo() throws ContextualError {
        when(t1.isInt()).thenReturn(true);
        when(t2.isInt()).thenReturn(true);
        assertTrue(ContextTools.typeArithModulo(compiler, t1, t2, null).isInt());
    }

    @Test
    public void testTypeArithModuloFloat() {
        when(t1.isInt()).thenReturn(true);
        when(t2.isFloat()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeArithModulo(compiler, t1, t2, null);});
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

    @Test
    public void testTypeUnaryMinusInt() throws ContextualError {
        when(t1.isInt()).thenReturn(true);
        assertTrue(ContextTools.typeUnaryMinus(compiler, t1, null).isInt());
    }

    @Test
    public void testTypeUnaryMinusFloat() throws ContextualError {
        when(t1.isFloat()).thenReturn(true);
        assertTrue(ContextTools.typeUnaryMinus(compiler, t1, null).isFloat());
    }

    @Test
    public void testTypeUnaryMinusBool() {
        when(t1.isBoolean()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeUnaryMinus(compiler, t1, null);});
    }

    @Test
    public void testTypeUnaryNot() throws ContextualError {
        when(t1.isBoolean()).thenReturn(true);
        assertTrue(ContextTools.typeUnaryNot(compiler, t1, null).isBoolean());
    }

    @Test
    public void testTypeUnaryNotInt() {
        when(t1.isInt()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeUnaryNot(compiler, t1, null);});
    }

    @Test
    public void testTypeInstanceOf() throws ContextualError {
        when(t1.isClassOrNull()).thenReturn(true);
        when(t2.isClass()).thenReturn(true);
        assertTrue(ContextTools.typeInstanceOf(compiler, t1, t2, null).isBoolean());
    }

    @Test
    public void testTypeInstanceOfNotClass() {
        when(t2.isClass()).thenReturn(true);
        assertThrows(ContextualError.class, ()-> {ContextTools.typeInstanceOf(compiler, t1, t2, null);});
    }
}
