package inter;
import code_generation.ASMGen;
import code_generation.AssemblyFile;
import code_generation.Registers;
import symbols.*;

/**
 * Node for an Else statement
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Else extends Stmt {
    Expr expr;
    Stmt stmt1, stmt2;

    public Else(Expr x, Stmt s1, Stmt s2) {
        expr = x;
        stmt1 = s1;
        stmt2 = s2;

        if( expr.mType != Type.Bool ){
            expr.error("boolean required in if");
        }
    }

    /**
     * This node should be identified as an Else statement
     * @return true
     */
    @Override
    public boolean isElse() {
        return true;
    }

    @Override
    public void gen(int b, int a) {
        int label1 = newlabel();   // label1 for stmt1
        int label2 = newlabel();   // label2 for stmt2
        expr.jumping(0, a);    // fall through to stmt1 on true

        //setAfter(a);
        emit(expr.toAsmMain());

        String register = expr.getResultRegister();
        emit(ASMGen.genBranchTo(register));

        emit(stmt1.toAsmMain());
        stmt1.gen(label1, a);

        emit(genLabel(a) + ":");
        emit(stmt2.toAsmMain());
        stmt2.gen(label2, a);


        Registers.clearAllRegs();   //Clear all registers

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
        String s1 = expr.toAsmData();
        String s2 = stmt1.toAsmData();
        String s3 = stmt2.toAsmData();
        if (s1 != null) sb.append(s1);
        if (s2 != null) sb.append(s2);
        if (s3 != null) sb.append(s3);
        return sb.toString();
    }

    @Override
    public String toAsmConstants() {
        StringBuilder sb = new StringBuilder();
        String s2 = expr.toAsmConstants();
        String s1 = stmt1.toAsmConstants();
        String s3 = stmt2.toAsmConstants();
        if (s2 != null) sb.append(s2);
        if (s1 != null) sb.append(s1);
        if (s3 != null) sb.append(s3);

        return sb.toString();
    }

}
