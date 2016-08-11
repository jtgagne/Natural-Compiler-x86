package inter;
import symbols.*;

/**
 * Node for an Else statement
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Else extends Stmt {
    Expr expr;
    Stmt stmt1, stmt2;

    public Else(Expr x, Stmt s1, Stmt s2) {
        expr = x;
        stmt1 = s1;
        stmt2 = s2;

        if( expr.mType != Type.Bool ){
            expr.error("boolean required in if");
        }
    }

    /**
     * This node should be identified as an Else statement
     * @return true
     */
    @Override
    public boolean isElse() {
        return true;
    }

    @Override
    public void gen(int b, int a) {
        int label1 = newlabel();   // label1 for stmt1
        int label2 = newlabel();   // label2 for stmt2
        expr.jumping(0, label2);    // fall through to stmt1 on true
        emit(genLabel(label1));
        stmt1.gen(label1, a);
        emit("L" + a);
        emit(genLabel(label2));
        stmt2.gen(label2, a);
    }

}
