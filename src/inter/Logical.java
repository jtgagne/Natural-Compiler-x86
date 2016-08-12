package inter;
import code_generation.AssemblyFile;
import lexer.*; import symbols.*;

/**
 * Node for a logical expression
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Logical extends Expr
{
    public Expr expr1;
    public Expr expr2;

    //Member variables inherited from Expr:
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    Logical(Token tok, Expr x1, Expr x2) {
        super(tok, null);                      //Expr(Token tok, Type type)
        expr1 = x1;
        expr2 = x2;

        mType = check(expr1.getType(), expr2.getType());    //Set mType

        if (mType == null ){
            error("type error");
        }
    }

    @Override
    public boolean isLogical() {
        return true;
    }

    // Verify both types are bool
    public Type check(Type p1, Type p2) {
        if (p1 == Type.Bool && p2 == Type.Bool )
            return Type.Bool;
        else
            return null;
    }

    @Override
    public Expr gen() {
        AssemblyFile.addToMain(this.toAsmMain());
        AssemblyFile.addVariables(this.toAsmData());
        AssemblyFile.addConstant(this.toAsmConstants());
        int f = newlabel();                                   //Label for false part
        int a = newlabel();                                   //Label after false
        Temp temp = new Temp(mType);                           //Holds logical result of expression
        this.jumping(0,f);                                    //Depends on operator (And, Or, Not ...) .. Fall through on true and generate the label on false
        emit(temp.toString() + " = true");                    // Put in a line for a temporary as true
        emit("goto L" + a);                                   // Goto
        emit(genLabel(f));
        emit(temp.toString() + " = false");                   //Print out a line that t2 is false
        emit(genLabel(a));                                         // Goto a if t2 is false
        return temp;
    }

    @Override
    public String toString() {
        return expr1.toString()+" "+ mToken.toString()+" "+expr2.toString();
    }

    @Override
    public String toAsmMain() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmMain();
        String s2 = expr2.toAsmMain();

        if (s1 != null) sb.append(s1);
        if (s2 != null) sb.append(s2);

        return sb.toString();
    }

    @Override
    public String toAsmConstants() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmConstants();
        String s2 = expr2.toAsmConstants();

        if (s1 != null) sb.append(s1);
        if (s2 != null) sb.append(s2);

        return sb.toString();
    }

    @Override
    public String toAsmData() {
        StringBuilder sb = new StringBuilder();
        String s1 = expr1.toAsmData();
        String s2 = expr2.toAsmData();

        if (s1 != null) sb.append(s1);
        if (s2 != null) sb.append(s2);

        return sb.toString();
    }

}
