package inter;
import code_generation.ASMGen;
import code_generation.AsmBoolean;
import code_generation.AssemblyFile;
import code_generation.RegisterManager;
import symbols.*;

/**
 * Node for an if statement
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class If extends Stmt {

    Expr expr;  //Boolean expression
    Stmt stmt;  //Block of code
    private String mRegister;
    private int label;

    public If(Expr x, Stmt s) {
        expr = x;
        stmt = s;

        if( expr.mType != Type.Bool ){
            expr.error("boolean required in if");
        }
    }

    @Override
    public boolean isIf() {
        return true;
    }

    @Override
    public void emit(String s) {
        super.emit(s);
    }

    @Override
    public void gen(int b, int a) {
        label = newlabel();     // label for the code for stmt
        expr.jumping(0, a);     // fall through on true, goto a on false
        setAfter(a);

        //For some reason, when jumping is called it 'double reserves a register'
        mRegister = expr.getResultRegister();
        RegisterManager.freeRegister(mRegister);

        // if we are dealing with values that are singular, load them into memory
        if(expr.isConstant() || expr.isIdentifier()){
            emit(loadIntoRegister());
            emit(AsmBoolean.genBranchTo(mRegister));
        } else if (expr.isRel()){
            emit(expr.toAsmMain());
            mRegister = expr.getResultRegister();
            emit(AsmBoolean.genRelationalJump(expr.getToken()));
        }
        else{
            emit(expr.toAsmMain());
            mRegister = expr.getResultRegister();
            emit(AsmBoolean.genBranchTo(mRegister));
        }

        emit(stmt.toAsmMain());
        stmt.gen(label, a);
        RegisterManager.freeRegister(mRegister);
        AssemblyFile.addVariables(this.toAsmData());
        AssemblyFile.addConstant(this.toAsmConstants());
    }

    @Override
    public String toAsmMain() {
        return super.toAsmMain();
    }

    @Override
    public String toAsmData() {
        StringBuilder sb = new StringBuilder();
        String s2 = expr.toAsmData();
        String s1 = stmt.toAsmData();
        if (s2 != null) sb.append(s2);
        if (s1 != null) sb.append(s1);
        return sb.toString();
    }

    @Override
    public String toAsmConstants() {
        return "";
    }

    private String loadIntoRegister(){
        return expr.load();
    }
}
