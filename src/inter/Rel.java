package inter;
import code_generation.AsmBoolean;
import code_generation.Macros;
import lexer.*; import symbols.*;

import javax.crypto.Mac;

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
   private Type mRelationalType;

   public Rel(Token tok, Expr x1, Expr x2) {
      super(tok, x1, x2);
   }

   @Override
   public Type getChildType() {
      return expr1.getType();
   }

   @Override
   public boolean isRel() {
      return true;
   }

   @Override
   public Type check(Type p1, Type p2) {
      if( p1 == p2 ){
         mRelationalType = p1;
         return Type.Bool;
      }
      return null;
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
      return genOperation();
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

//      if(expr1.isIdentifier() && expr1.getType() == Type.Bool){
//         Id id = (Id) expr1;
//         sb.append(id.loadForArithmetic());
//         register1 = id.getResultRegister();
//      }else{
//         sb.append(expr1.toAsmMain());
//         register1 = expr1.getResultRegister();
//      }
//
//      if(expr2.isIdentifier() && expr2.getType() == Type.Bool){
//         Id id = (Id) expr2;
//         sb.append(id.loadForArithmetic());
//         register2 = id.getResultRegister();
//      }else{
//         sb.append(expr2.toAsmMain());
//         register2 = expr2.getResultRegister();
//      }
      Id id1 = (Id) expr1;
      Id id2 = (Id) expr2;
      sb.append(AsmBoolean.evaluateRelational(id1, id2, getMacroName()));

      //sb.append(AsmBoolean.genCompare(register1, register2, expr1.getType()));
      mRegister = AsmBoolean.getResultRegister();
      return sb.toString();
   }


    /**
     * Gets the name of the associated Macro to be called to convert a comparison down to a natural
     * boolean value
     * @return the name of the macro to be called.
     */
   private String getMacroName(){
      switch (this.mToken.tag){
          case Tag.LESS:
              return Macros.REL_LESS_THAN;
          case Tag.GREATER:
              return Macros.REL_GREATER_THAN;
          case Tag.GE:
              return Macros.REL_GREATER_THAN_EQ;
          case Tag.LE:
              return Macros.REL_LESS_THAN_EQ;
          case Tag.EQ:
              return Macros.REL_EQUAL_TO;
          case Tag.NE:
              return Macros.REL_NOT_EQUAL_TO;
      }
      return "";
   }

}
