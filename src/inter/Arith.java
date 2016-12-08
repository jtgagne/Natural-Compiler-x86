package inter;
import code_generation.AsmArith;
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
    private String mTempRegister;
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

       StringBuilder sb = new StringBuilder();
       sb.append(genLoadString(expr1));
       reg1 = mTempRegister;
       sb.append(genLoadString(expr2));
       reg2 = mTempRegister;

       mRegister = reg1;    //TODO: this will be different for division


       sb.append(AsmArith.genMath(this, mRegister, reg1, reg2));  //Generate Assembly for a mathematical operation
       RegisterManager.freeRegister(reg2);

       this.toInfix();
       return sb.toString();
   }

    private String genLoadString(Expr expr){
        Id id;
        StringBuilder sb = new StringBuilder();

        // Boolean values must be loaded differently for arithmetic
        if(expr.isIdentifier()){
            id = (Id) expr;
            sb.append(id.loadForArithmetic());
            mTempRegister = id.getResultRegister();
        }else{
            sb.append(expr.toAsmMain());    //AsmLoad the first expression
            mTempRegister = expr.getResultRegister();
        }
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

    private void toInfix(){
        System.out.printf("%s %s %s\n", expr1.toString(), expr2.toString(), mToken.toString());
    }
}
