package inter;
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

    @Override
    public void jumping(int t, int f) {
        expr2.jumping(f, t); }

    @Override
    public String toString() {
        return mToken.toString()+" "+expr2.toString();
    }
}
