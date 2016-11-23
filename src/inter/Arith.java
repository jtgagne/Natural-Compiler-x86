package inter;
import code_generation.ASMGen;
import code_generation.AsmArith;
import code_generation.AsmLoad;
import code_generation.RegisterManager;
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
    public boolean isArith() {
        return true;
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
       Id id1;
       Id id2;
       StringBuilder sb = new StringBuilder();

       // Boolean values must be loaded differently for arithmetic
       if(expr1.isIdentifier()){
           id1 = (Id) expr1;
           sb.append(id1.loadForArithmetic());
           reg1 = id1.getResultRegister();
       }else{
           sb.append(expr1.toAsmMain());    //AsmLoad the first expression
           reg1 = expr1.getResultRegister();
       }
       if(expr2.isIdentifier()){
           id2 = (Id) expr2;
           sb.append(id2.loadForArithmetic());
           reg2 = expr2.getResultRegister();
       }else{
           sb.append(expr2.toAsmMain());    //AsmLoad the second expression
           reg2 = expr2.getResultRegister();
       }

       //TODO: this will be different for division
       mRegister = reg1;

       //The result register (member variable of Expr)
       if(expr1.mType == Type.Double || expr2.mType == Type.Double){
           //mRegister = RegisterManager.getDoubleReg(); //Set the
       } else if(expr1.mType == Type.Float){
           //mRegister = RegisterManager.getFloatingPointReg();
       } else{
           //mRegister = RegisterManager.getGeneralPurpose16().toString();
       }

       sb.append(AsmArith.genMath(this, mRegister, reg1, reg2));  //Generate Assembly for a mathematical operation
       RegisterManager.freeRegister(reg2);

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
