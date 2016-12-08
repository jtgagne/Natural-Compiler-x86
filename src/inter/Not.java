package inter;
import code_generation.ASMGen;
import code_generation.AsmBoolean;
import lexer.*;

/**
 * subclass of logical node for negating boolean expressions
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Not extends Logical {

    //Member variables inherited from Logical:
    //public Expr expr1;
    //public Expr expr2;
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    public Not(Token tok, Expr x2) {
        super(tok, x2, x2);
    }

    /**
     * This should be identified as a not-node
     * @return
     */
    @Override
    public boolean isNot() {
        return true;
    }

    /*
    @Override
    public void jumping(int t, int f) {
        expr2.jumping(f, t); }*/

    @Override
    public String toString() {
        return mToken.toString()+" "+expr2.toString();
    }

    @Override
    public String toAsmMain() {
        return genNotExpr();
    }

    @Override
    public String toAsmData() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmData();
        //String s2 = expr2.toAsmData();

        if (s1 != null) sb.append(s1);
        //if (s2 != null) sb.append(s2);

        return sb.toString();
    }

    @Override
    public String toAsmConstants() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmConstants();
        //String s2 = expr2.toAsmConstants();

        if (s1 != null) sb.append(s1);
        //if (s2 != null) sb.append(s2);

        return sb.toString();
    }

    /**
     * Generate the expression to 'AND' two expressions
     * @return the formatted expression.
     */
    private String genNotExpr(){
        String register1;
        StringBuilder sb = new StringBuilder();

        sb.append(expr1.toAsmMain());
        register1 = expr1.getResultRegister();

        sb.append(AsmBoolean.genNotExpr(register1));
        mRegister = AsmBoolean.getResultRegister();

        return sb.toString();
    }
}
