package inter;

/**
 * a sequence of statements
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Seq extends Stmt {

    Stmt stmt1; Stmt stmt2;

    public Seq(Stmt s1, Stmt s2) {
        stmt1 = s1;
        stmt2 = s2;
    }

    @Override
    public boolean isSeq() {
        return true;
    }

    @Override
    public void gen(int b, int a) {
        if ( stmt1 == Stmt.Null ){
            stmt2.gen(b, a);
            //stmt2.emit(this.toAsmMain());
        }
        else if ( stmt2 == Stmt.Null ) {
            stmt1.gen(b, a);
            //stmt1.emit(this.toAsmMain());
        }
        else {
            int label = newlabel();
            //emit(genLabel(label));
            //emit(stmt1.toAsmMain());
            stmt1.gen(b,label);
            emit(String.format("%s:", genLabel(label)));
            stmt2.gen(label,a);
        }
    }
}
