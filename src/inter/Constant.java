package inter;
import code_generation.Registers;
import lexer.*;
import symbols.*;

/**
 * Create a node containing a numerical constant value
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Constant extends Expr {

   private String register;
   private String ERROR = "ERROR CONSTANT: error generating asm\n\n";
   private Type mType;
   private Token mToken;


   public static final Constant
           True  = new Constant(Word.True,  Type.Bool),
           False = new Constant(Word.False, Type.Bool);

   public Constant(Token tok, Type p){
      super(tok, p);
      mType = p;
      mToken = tok;
   }

   public void setType(Type type) {
      mType = type;
   }

   public Constant(int i) {
      super(new Num(i), Type.Int);
   }


   @Override
   public boolean isConstant() {
      return true;
   }

   @Override
   public String toString() {
      return op.toString() + " " + type.toString();
   }

   @Override
   public void jumping(int t, int f) {
      if ( this == True && t != 0 ) emit("goto L" + t);
      else if ( this == False && f != 0) emit("goto L" + f);
   }

   @Override
   public String load(String ...identifiers) {
      String identifier = identifiers[0];
      if(mType == Type.Float || mType == Type.Double){  return loadFloatingPoint(identifier); }

      else if(mType == Type.Char){ return loadChar(identifier); }

      return loadImmediate();
   }

   @Override
   public String toAsmMain() {
      return loadImmediate();
   }

   private String loadFloatingPoint(String identifier){

      StringBuilder sb = new StringBuilder();
      switch (type.lexeme){
         case "float":
            register = Registers.getFloatingPointReg();
            sb.append(String.format("\tla\t $a0, %s\t\t #Load an immediate value to register\n",identifier));
            sb.append(String.format("\tl.s\t %s, 0($a0)\t\t #Load the value at the address\n", register));
            return sb.toString();
         case "double":
            register = Registers.getDoubleReg();   //Take up two registers
            sb.append(String.format("\tla\t $a0, %s\t\t #Load an immediate value to register\n",identifier));
            sb.append(String.format("\tl.d\t %s, 0($a0)\t\t #Load the value at the address\n", register));
            return sb.toString();
      }
      return ERROR;
   }

   private String loadImmediate() {
      Token token = op;
      Num num;
      register = Registers.getTempReg();

      switch (mType.lexeme){
         case "int":
            num = (Num) token;
            return String.format(
                    "\tli\t %s, %d\t\t#Load an immediate value into the register\n", register, num.value); //Load int into temp register
         case "long":
            num = (Num) token;
            return String.format(
                    "\tli\t %s, %s\t\t#Load an immediate value into the register\n", register, num.value); //Load int into temp register
      }
      return ERROR;
   }

   private String loadChar(String identifier){

      String tempReg = Registers.getTempReg();
      register = Registers.getTempReg();

      StringBuilder sb = new StringBuilder();
      sb.append(String.format("\tla\t %s, %s\t\t\n", tempReg, identifier));
      sb.append(String.format("\tlb\t %s, 0(%s)\t\t#Load an immediate value to register\n", register, tempReg));
      return sb.toString();
   }

   @Override
   public String getResultRegister() {
      return register;
   }
}
