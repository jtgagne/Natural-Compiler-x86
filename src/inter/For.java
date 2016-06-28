package inter;
import symbols.*;

public class For extends Stmt
{
    Stmt assigned;
    Expr expr;
    Stmt stmt;

    public For()
    {
        assigned = null;
        expr = null;
        stmt = null;
    }

    public void init(Expr x, Stmt y,Stmt s)
    {
        assigned = y;
        expr = x;
        stmt = s;
        if( expr.type != Type.Bool )
            expr.error("boolean required in while");
    }

    @Override
    public void gen(int b, int a)
    {
        after = a;                                // save label a
        expr.jumping(0, a);
        int label = newlabel();                   // label for stmt
        emitlabel(label);
        stmt.gen(label, b);
        emit("goto L" + b);
    }
}
