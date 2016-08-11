package inter;
import code_generation.ASMGen;
import code_generation.Registers;
import lexer.*;
import symbols.*;

/**
 * Arithmetic node for simple mathematical operations
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Arith extends Op {

    //Member variables inherited from Op:
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

   //An expression can be one of the following types:
   // Id | Constant

   public Expr expr1, expr2;
   private String reg1, reg2;   //Temporary registers
   private String ERROR = "ERROR ARITH: error generating asm";

   public Arith(Token tok, Expr x1, Expr x2)  {
      super(tok, null); 

      expr1 = x1;
      expr2 = x2;

      if(tok.tag != Tag.INCREASE && tok.tag != Tag.DECREASE ){
         mType = Type.max(expr1.mType, expr2.mType);
      }

      if (mType == null){
         error("type error");
      }
   }

    @Override
    public Expr gen() {
        return new Arith(mToken, expr1.reduce(), expr2.reduce());
    }

   @Override
   public String toString() {
       return expr1.toString()+" "+ mToken.toString()+" "+expr2.toString();
   }

   /**
    * Generate Assembly, clear the registers used
    * @return assembly code for the operation
     */
   @Override
   public String toAsmMain() {

       StringBuilder sb = new StringBuilder();
       sb.append(expr1.toAsmMain());    //Load the first expression
       sb.append(expr2.toAsmMain());    //Load the second expression

       reg1 = expr1.getRegister();
       reg2 = expr2.getRegister();

       //The result register (member variable of Expr)
       if(expr1.mType == Type.Double || expr2.mType == Type.Double){
           mRegister = Registers.getDoubleReg(); //Set the
       } else if(expr1.mType == Type.Float){
           mRegister = Registers.getFloatingPointReg();
       } else{
           mRegister = Registers.getTempReg();
       }

       sb.append(ASMGen.genMath(this, mRegister, reg1, reg2));  //Generate Assembly for a mathematical operation

       return sb.toString();
   }

    @Override
    public String toAsmData() {
        return super.toAsmData();
    }

    @Override
    public String toAsmConstants() {
        StringBuilder sb = new StringBuilder();
        if(expr1.toAsmConstants() != null){
            sb.append(expr1.toAsmConstants());
        }
        if(expr2.toAsmConstants() != null){
            sb.append(expr2.toAsmConstants());
        }
        return sb.toString();
    }

    /**
    * @return the name of the register the result of an operation is stored in
    */
   @Override
   public String getResultRegister() {
       return mRegister;
   }


}
