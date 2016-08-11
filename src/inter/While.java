package inter;
import symbols.*;

/**
 * Creates a node for a while statement
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class While extends Stmt 
{
    Expr expr;
    Stmt stmt;

   public While() {
       expr = null; 
       stmt = null;
   }

    @Override
    public boolean isWhile() {
        return true;
    }

    public void init(Expr x, Stmt s) {
        expr = x;  
        stmt = s;
        if( expr.mType != Type.Bool )
            expr.error("boolean required in while");
   }
   
   @Override
   public void gen(int b, int a){
        after = a;                                // save label a
        expr.jumping(0, a);
        int label = newlabel();                   // label for stmt
        emit(genLabel(label));
        stmt.gen(label, b);
        emit("L" + b);
   }
}
