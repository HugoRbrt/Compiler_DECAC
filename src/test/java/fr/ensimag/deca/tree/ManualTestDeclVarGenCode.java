/* A manual test for the initial sketch of code generation included in
 * students skeleton.
 *
 * It is not intended to still work when code generation has been updated.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 *
 * @author Ensimag
 * @date 01/01/2022
 */
public class ManualTestDeclVarGenCode {

    public static AbstractProgram initTest1() {/*
        ListInst linst = new ListInst();
        ListDeclVar LDecl = new ListDeclVar();
        // ¨Pour le faire fonctionner il faut passer le constructeur de Symbol en public
        LDecl.add(new DeclVar(new Identifier(new Symbol("int")), new Identifier(new Symbol("x")), new Initialization(new IntLiteral(3))));
        LDecl.add(new DeclVar(new Identifier(new Symbol("float")), new Identifier(new Symbol("y")), new Initialization(new IntLiteral(7))));
        LDecl.add(new DeclVar(new Identifier(new Symbol("int")), new Identifier(new Symbol("z")), new Initialization(new IntLiteral(42))));
        AbstractProgram source =
                new Program(
                        new ListDeclClass(),
                        new Main(LDecl,linst));
        ListExpr lexp1 = new ListExpr(), lexp2 = new ListExpr();
        linst.add(new Print(false,lexp1));
        linst.add(new Println(false,lexp2));
        lexp1.add(new StringLiteral("Hello "));
        lexp2.add(new StringLiteral("everybody !"));
        return source;*/
        return new Program(
                new ListDeclClass(),
                new Main(new ListDeclVar(),new ListInst()));
    }

    public static String gencodeSource(AbstractProgram source) {
        DecacCompiler compiler = new DecacCompiler(null,null);
        source.codeGenProgram(compiler);
        return compiler.displayProgram();
    }

    public static void test1() {
        AbstractProgram source = initTest1();
        System.out.println("---- From the following Abstract Syntax Tree ----");
        source.prettyPrint(System.out);
        System.out.println("---- We generate the following assembly code ----");
        String result = gencodeSource(source);
        System.out.println(result);
        assert(result.equals(
                "; Main program\n" +
                        "; Beginning of main function:\n" +
                        "   LOAD #3, R0\n" +
                        "   STORE R0, 1(GB)\n" +
                        "   LOAD #7, RO\n" +
                        "   STORE RO, 2(GB)\n" +
                        "   LOAD #42, R0\n" +
                        "   STORE R0, 3(GB)\n" +
                        "	WSTR \"Hello \"\n" +
                        "	WSTR \"everybody !\"\n" +
                        "	WNL\n" +
                        "	HALT\n"));
    }



    public static void main(String args[]) {
        test1();
    }
}

