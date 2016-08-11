package inter;
import lexer.*; import symbols.*;

/**
 * Stores data regarding an operator
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Op extends Expr {

    //Member variables inherited from Expr:
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    public Op(Token tok, Type p) {
        super(tok, p);      //Expr(Token tok, Type type)
    }

    /**
     * This node should be identified as an Op-node
     * @return true
     */
    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public Expr reduce() {
        Expr x = gen();
        Temp t = new Temp(mType);
        emit( t.toAsmMain()  + x.toAsmMain() );
        return t;
    }
}
