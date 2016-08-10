package inter;
import code_generation.ASMGen;
import code_generation.AssemblyFile;
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
   private String constantId;     //Constant identifier


   public static final Constant
           True  = new Constant(Word.True,  Type.Bool),
           False = new Constant(Word.False, Type.Bool);

   public Constant(Token tok, Type p){
      super(tok, p);
      mType = p;
      mToken = tok;
   }

   @Override
   public String toAsmConstants() {
      mToken.setType(this.type);
      if(mToken.getConstantId() == null){
         mToken.setConstantId(ASMGen.genConstantName());
      }
      return mToken.toAsmConstant();
   }

   public void setType(Type type) {
      mType = type;
   }

   public Constant(int i) {
      super(new Num(i), Type.Int);
   }


   /**
    * Check if this Expr-Node contains a constant value
    * @return true
     */
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
   public Expr gen() {
      return super.gen();
   }


   /**
    * Generate the Assembly code to be added to the main
    * @return assembly code
     */
   @Override
   public String toAsmMain() {
      mToken.setType(type);
      if(mToken.tag == Tag.TRUE){
         mToken.setConstantId("BOOL_TRUE");
      }else if(mToken.tag == Tag.FALSE){
         mToken.setConstantId("BOOL_FALSE");
      } else if(mToken.getConstantId() == null){
         mToken.setConstantId(ASMGen.genConstantName());
      }
      constantId = mToken.getConstantId();

      return load(constantId);
   }

   /**
    * Assembly code to load a floating point into a register
    * @param identifier name of the variable
    * @return assembly code to load number into register
     */
   private String load(String identifier){
      Token token = op;
      Num num;

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
         case "char":
               String tempReg = Registers.getTempReg();
               register = Registers.getTempReg();
               sb = new StringBuilder();
               sb.append(String.format("\tla\t %s, %s\t\t\n", tempReg, identifier));
               sb.append(String.format("\tlb\t %s, 0(%s)\t\t#Load an immediate value to register\n", register, tempReg));
               return sb.toString();
         case "int":
               register = Registers.getTempReg();
               num = (Num) token;
               return String.format("\tli\t %s, %d\t\t#Load an immediate value into the register\n", register, num.value); //Load int into temp register
         case "long":
               register = Registers.getTempReg();
               num = (Num) token;
               return String.format("\tli\t %s, %s\t\t#Load an immediate value into the register\n", register, num.value); //Load int into temp register
         case "boolean":
               register = Registers.getTempReg();
               mToken.setRegister(register);
               return String.format("\tlb\t %s, %s\t\t#Load a boolean value\n", register, identifier);
      }
      return ERROR;
   }

   @Override
   public String getResultRegister() {
      return register;
   }
}
