package inter;
import code_generation.ASMGen;
import code_generation.AssemblyFile;
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

        if(expr.isConstant() || expr.isIdentifier()){
            emit(genBoolCompare());
        }else{
            emit(expr.toAsmMain());
        }

        String register = expr.getResultRegister(); //Get the register the result of this expression is stored in

        emit(ASMGen.genBranchTo(register));         //emit the branch-to line based on the result in the register

        emit(stmt.toAsmMain());
        stmt.gen(label, a);

        //RegisterManager.clearAllRegs();   //Clear all registers

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
        StringBuilder sb = new StringBuilder();
        String s2 = expr.toAsmConstants();
        String s1 = stmt.toAsmConstants();
        if (s2 != null) sb.append(s2);
        if (s1 != null) sb.append(s1);
        return sb.toString();
    }

    private String genBoolCompare(){
        //String register = RegisterManager.getTempReg();   //Get a temp reg to compare the values
        String load = expr.load();
        //String s1 = String.format("\tlb\t %s, BOOL_TRUE\n", register);
        //String s2 = String.format("\tbne\t %s, %s, %s\n\n", register, expr.mRegister, stmt.getLabelAfter());
        //return  load + s1 + s2;
        return "";
    }
}
