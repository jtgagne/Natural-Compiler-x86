package inter;
import lexer.Token;

/**
 * Class for creating an And node
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class And extends Logical {

    //Member variables inherited from Logical:
    //public Expr expr1;
    //public Expr expr2;
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    public And(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    @Override
    public boolean isAnd() {
        return true;
    }

    @Override
    public void jumping(int t, int f) {
        int label = f != 0 ? f : newlabel();
        expr1.jumping(0, label);
        expr2.jumping(t,f);
        if( f == 0 ){
            emit(genLabel(label));
        }
    }
}
