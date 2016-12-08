package inter;
import code_generation.ASMGen;
import code_generation.AsmBoolean;
import lexer.*;

/**
 * Stores data regarding an OR join
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Or extends Logical {

    //Member variables inherited from Logical:
    //public Expr expr1;
    //public Expr expr2;
    //protected Token mToken;
    //protected Type mType;
    //protected String register;
    private String mLabel;

    public Or(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    /**
     * These should be identifier as an OR node.
     * @return true
     */
    @Override
    public boolean isOr() {
        return true;
    }

    @Override
    public String toAsmMain() {
        return genOrExpr();
    }

    @Override
    public void jumping(int t, int f) {
        int label = f != 0 ? f : newlabel();
        expr1.jumping(0, label);
        expr2.jumping(t,f);
        if( f == 0 ){
            emit(genLabel(label));
        }
        mLabel = Integer.toString(label);
    }

    @Override
    public String toAsmData() {
//        StringBuilder sb = new StringBuilder();
//        String s1 = expr1.toAsmData();
//        String s2 = expr2.toAsmData();
//        if (s1 != null) sb.append(s1);
//        if (s2 != null) sb.append(s2);
//        return sb.toString();
        return "";
    }

    @Override
    public String toAsmConstants() {
//        StringBuilder sb = new StringBuilder();
//        String s1 = expr1.toAsmConstants();
//        String s2 = expr2.toAsmConstants();
//        if (s1 != null) sb.append(s1);
//        if (s2 != null) sb.append(s2);
//        return sb.toString();
        return "";
    }

    /**
     * Generate the expression to 'AND' two expressions
     * @return the formatted expression.
     */
    private String genOrExpr(){
        String register1, register2;
        StringBuilder sb = new StringBuilder();

        sb.append(expr1.toAsmMain());
        register1 = expr1.getResultRegister();
        sb.append(expr2.toAsmMain());
        register2 = expr2.getResultRegister();

        sb.append(AsmBoolean.genOrExpr(register1, register2));

        mRegister = AsmBoolean.getResultRegister();

        return sb.toString();
    }
}
