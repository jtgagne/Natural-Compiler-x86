package inter;
import code_generation.ASMGen;
import code_generation.Registers;
import lexer.*; import symbols.*;

/**
 * Stores data regarding a relational operation
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Rel extends Logical {

   //Member variables inherited from Logical:
   //public Expr expr1;
   //public Expr expr2;
   //protected Token mToken;
   //protected Type mType;
   //protected String mRegister;

   public Rel(Token tok, Expr x1, Expr x2) {
      super(tok, x1, x2);
   }

   @Override
   public boolean isRel() {
      return true;
   }

   @Override
   public Type check(Type p1, Type p2) {
      if( p1 == p2 )
         return Type.Bool;
      else return null;
   }


   @Override
   public void jumping(int t, int f) {
      //Expr a = expr1.reduce();
      //Expr b = expr2.reduce();
      //String test = a.toAsmMain() + " " + b.toAsmMain();
      //emitjumps(test, t, f);
   }

   @Override
   public String toAsmMain() {
      String code = genOperation();
      Registers.clearAllTempRegs();
      return code;
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

   /**
    * less than, greater than, equal to
    * expr1 OP expr2
    * @return
    */
   public String genOperation(){
      String register1, register2;
      StringBuilder sb = new StringBuilder();

      sb.append(expr1.toAsmMain());
      register1 = expr1.getResultRegister();

      sb.append(expr2.toAsmMain());
      register2 = expr2.getResultRegister();

      sb.append(ASMGen.genLogical(register1, register2, this.mToken));

      return sb.toString();
   }

}
