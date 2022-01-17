package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCastMethods {
    DecacCompiler compiler = new DecacCompiler(null, null);

    @Test
    public void testGetClassDef() {
        Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
        ClassDefinition def = Mockito.mock(ClassDefinition.class);
        ident.setDefinition(def);
        ClassDefinition cl2 = ident.getClassDefinition();
        assertInstanceOf(ClassDefinition.class, cl2);
    }

    @Test
    public void testGetClassDefEx() {
        assertThrows(DecacInternalError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
                Definition def = Mockito.mock(Definition.class);
                ident.setDefinition(def);
                ClassDefinition cl2 = ident.getClassDefinition();
            }
        });
    }

    @Test
    public void testGetMethodDef() {
        Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
        MethodDefinition def = Mockito.mock(MethodDefinition.class);
        ident.setDefinition(def);
        MethodDefinition cl2 = ident.getMethodDefinition();
        assertInstanceOf(MethodDefinition.class, cl2);
    }

    @Test
    public void testGetMethodDefEx() {
        assertThrows(DecacInternalError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
                Definition def = Mockito.mock(Definition.class);
                ident.setDefinition(def);
                MethodDefinition cl2 = ident.getMethodDefinition();
            }
        });
    }

    @Test
    public void testGetFieldDef() {
        Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
        FieldDefinition def = Mockito.mock(FieldDefinition.class);
        ident.setDefinition(def);
        FieldDefinition cl2 = ident.getFieldDefinition();
        assertInstanceOf(FieldDefinition.class, cl2);
    }

    @Test
    public void testGetFieldDefEx() {
        assertThrows(DecacInternalError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
                Definition def = Mockito.mock(Definition.class);
                ident.setDefinition(def);
                FieldDefinition cl2 = ident.getFieldDefinition();
            }
        });
    }

    @Test
    public void testGetVariableDef() {
        Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
        VariableDefinition def = Mockito.mock(VariableDefinition.class);
        ident.setDefinition(def);
        VariableDefinition cl2 = ident.getVariableDefinition();
        assertInstanceOf(VariableDefinition.class, cl2);
    }

    @Test
    public void testGetVariableDefEx() {
        assertThrows(DecacInternalError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
                Definition def = Mockito.mock(Definition.class);
                ident.setDefinition(def);
                VariableDefinition cl2 = ident.getVariableDefinition();
            }
        });
    }

    @Test
    public void testGetExpDef() {
        Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
        ExpDefinition def = Mockito.mock(ExpDefinition.class);
        ident.setDefinition(def);
        ExpDefinition cl2 = ident.getExpDefinition();
        assertInstanceOf(ExpDefinition.class, cl2);
    }

    @Test
    public void testGetExpDefEx() {
        assertThrows(DecacInternalError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Identifier ident = new Identifier(compiler.getSymbTable().create("ident"));
                Definition def = Mockito.mock(Definition.class);
                ident.setDefinition(def);
                ExpDefinition cl2 = ident.getExpDefinition();
            }
        });
    }

    @Test
    public void testAsClassType() throws ContextualError {
        Type t1 = new ClassType(
                compiler.getSymbTable().create("t1"), Mockito.mock(Location.class),
                Mockito.mock(ClassDefinition.class));
        ClassType t2 = t1.asClassType("", Mockito.mock(Location.class));
        assertInstanceOf(ClassType.class, t2);
    }

    @Test
    public void testAsClassTypeEx() throws ContextualError {
        assertThrows(ContextualError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Type t1 = new IntType(compiler.getSymbTable().create("t1"));
                ClassType t2 = t1.asClassType("", null);
            }
        });
    }
}
