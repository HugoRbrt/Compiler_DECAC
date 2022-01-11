package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Lower;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestLowerPlain {
    final Type INT = new IntType(null);
    final Type FLOAT = new FloatType(null);
    final Type BOOLEAN = new BooleanType(null);

    @Test
    public void testTypeInt() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        AbstractExpr left = Mockito.mock(AbstractExpr.class);
        when(left.verifyExpr(compiler, null, null)).thenReturn(INT);
        AbstractExpr right = Mockito.mock(AbstractExpr.class);
        when(right.verifyExpr(compiler, null, null)).thenReturn(INT);
        Lower t = new Lower(left, right);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(left).verifyExpr(compiler, null, null);
        verify(right).verifyExpr(compiler, null, null);
    }

    @Test
    public void testTypeFloat() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        AbstractExpr left = Mockito.mock(AbstractExpr.class);
        when(left.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        AbstractExpr right = Mockito.mock(AbstractExpr.class);
        when(right.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        Lower t = new Lower(left, right);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(left).verifyExpr(compiler, null, null);
        verify(right).verifyExpr(compiler, null, null);
    }

    @Test
    public void testTypeBoolean() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        AbstractExpr left = Mockito.mock(AbstractExpr.class);
        when(left.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        AbstractExpr right = Mockito.mock(AbstractExpr.class);
        when(right.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        Lower t = new Lower(left, right);
        // check the result
        assertThrows(ContextualError.class, ()-> {t.verifyExpr(compiler, null, null).isBoolean();});
        // check that the mocks have been called properly.
        verify(left).verifyExpr(compiler, null, null);
        verify(right).verifyExpr(compiler, null, null);
    }

    @Test
    public void testTypeBoolInt() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        AbstractExpr left = Mockito.mock(AbstractExpr.class);
        when(left.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        AbstractExpr right = Mockito.mock(AbstractExpr.class);
        when(right.verifyExpr(compiler, null, null)).thenReturn(INT);
        Lower t = new Lower(left, right);
        // check the result
        assertThrows(ContextualError.class, ()-> {t.verifyExpr(compiler, null, null).isBoolean();});
        // check that the mocks have been called properly.
        verify(left).verifyExpr(compiler, null, null);
        verify(right).verifyExpr(compiler, null, null);
    }

    @Test
    public void testTypeBoolFloat() throws ContextualError {
        DecacCompiler compiler = new DecacCompiler(null, null);
        AbstractExpr left = Mockito.mock(AbstractExpr.class);
        when(left.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        AbstractExpr right = Mockito.mock(AbstractExpr.class);
        when(right.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        Lower t = new Lower(left, right);
        // check the result
        assertThrows(ContextualError.class, ()-> {t.verifyExpr(compiler, null, null).isBoolean();});
        // check that the mocks have been called properly.
        verify(left).verifyExpr(compiler, null, null);
        verify(right).verifyExpr(compiler, null, null);
    }
}
