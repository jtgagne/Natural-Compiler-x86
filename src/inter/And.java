package inter;
import code_generation.ASMGen;
import code_generation.Registers;
import lexer.Token;
import symbols.Type;

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
    public Type check(Type p1, Type p2) {
        if( p1 == p2 ){
            return Type.Bool;
        }
        return null;
    }

    @Override
    public boolean isAnd() {
        return true;
    }

    @Override
    public String toAsmMain() {
        return genAndExpr();
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
    public void jumping(int t, int f) {
        int label = f != 0 ? f : newlabel();
        expr1.jumping(0, label);
        expr2.jumping(t,f);
        if( f == 0 ){
            emit(genLabel(label));
        }
    }

    /**
     * Generate the expression to 'AND' two expressions
     * @return the formatted expression.
     */
    private String genAndExpr(){
        String register1, register2;
        StringBuilder sb = new StringBuilder();

        sb.append(expr1.toAsmMain());
        register1 = expr1.getResultRegister();

        sb.append(expr2.toAsmMain());
        register2 = expr2.getResultRegister();

        sb.append(ASMGen.genAndExpr(register1, register2));
        mRegister = ASMGen.getSavedRegister();

        return sb.toString();
    }
}
