package inter;
import symbols.*;

/**
 * Node for a do-while loop
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Do extends Stmt {
    Expr expr;
    Stmt stmt;

    public Do() {
        expr = null;
        stmt = null;
    }

    @Override
    public boolean isDo() {
        return true;
    }

    public void init(Stmt s, Expr x) {
        expr = x;
        stmt = s;

        if(expr.mType != Type.Bool){
            expr.error("boolean required in do");
        }
    }

    @Override
    public void gen(int b, int a) {
        after = a;
        int label = newlabel();   // label for expr
        stmt.gen(b,label);
        emit(genLabel(label));
        expr.jumping(b,0);
    }
}
