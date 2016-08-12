package inter;
import code_generation.ASMGen;
import lexer.*;

/**
 * Stores data regarding an OR join
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Or extends Logical {

    //Member variables inherited from Logical:
    //public Expr expr1;
    //public Expr expr2;
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    public Or(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    /**
     * These should be identifier as an OR node.
     * @return true
     */
    @Override
    public boolean isOr() {
        return true;
    }

    @Override
    public String toAsmMain() {
        return genOrExpr();
    }

    @Override
    public String toAsmData() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmData();
        String s2 = expr2.toAsmData();
        if (s1 != null) sb.append(s1);
        if (s2 != null) sb.append(s2);
        return sb.toString();
    }

    @Override
    public String toAsmConstants() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmConstants();
        String s2 = expr2.toAsmConstants();
        if (s1 != null) sb.append(s1);
        if (s2 != null) sb.append(s2);
        return sb.toString();
    }

    /**
     * Generate the expression to 'AND' two expressions
     * @return the formatted expression.
     */
    private String genOrExpr(){
        String register1, register2;
        StringBuilder sb = new StringBuilder();

        sb.append(expr1.toAsmMain());
        register1 = expr1.getResultRegister();

        sb.append(expr2.toAsmMain());
        register2 = expr2.getResultRegister();

        sb.append(ASMGen.genOrExpr(register1, register2));

        mRegister = ASMGen.getSavedRegister();

        return sb.toString();
    }
}
