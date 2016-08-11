package inter;
import lexer.*;
import symbols.*;

/**
 * Class for storing information on a unary node
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Unary extends Op {

    //Member variables inherited from Op:
    //protected Token mToken;
    //protected Type mType;
    //protected String register;
    public Expr expr;

    public Unary(Token tok, Expr x) {    // handles minus, for ! see Not
        super(tok, null);
        expr = x;
        mType = Type.max(Type.Int, expr.mType);

        if (mType == null ){
            error("type error");
        }

    }

    @Override
    public Expr gen() {
        return new Unary(mToken, expr.reduce());
    }

    @Override
    public String toString() {
        return mToken.toString()+" "+expr.toString();
    }

}
