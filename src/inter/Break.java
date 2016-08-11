package inter;

/**
 * Class to store a break node
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Break extends Stmt {

    Stmt stmt;

    public Break() {

        if(Stmt.Enclosing == Stmt.Null) {
            error("unenclosed break");
        }

        stmt = Stmt.Enclosing;
    }

    @Override
    public boolean isBreak() {
        return true;
    }

    @Override
    public void gen(int b, int a) {
        emit( "L" + stmt.after + ":");
    }
}
