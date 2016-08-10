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

   //An expression can be one of the following types:
   // Id | Constant
   public Expr expr1, expr2;
   private String reg1, reg2, reg3;
   private String ERROR = "ERROR ARITH: error generating asm";

   public Arith(Token tok, Expr x1, Expr x2)  {
      super(tok, null); 

      expr1 = x1;
      expr2 = x2;

      if(tok.tag != Tag.INCREASE && tok.tag != Tag.DECREASE ){
         type = Type.max(expr1.type, expr2.type);
      }else{
         if((expr1.type == Type.Int || expr1.type == Type.Long) && !expr2.isConstant()){
               type = null;
         }
      }

      if (type == null){
         error("type error");
      }
   }

   @Override
   public Expr gen() {
       return new Arith(op, expr1.reduce(), expr2.reduce());
   }

   @Override
   public String toString() {
       return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }

   /**
    * Generate Assembly, clear the registers used
    * @return assembly code for the operation
     */
   @Override
   public String toAsmMain() {
       StringBuilder sb = new StringBuilder();
       sb.append(expr1.toAsmMain());    //Load the first expression to the first register
       reg1 = expr1.getResultRegister();
       sb.append(expr2.toAsmMain());
       reg2 = expr2.getResultRegister();

       //The result register
       if(expr1.type == Type.Double || expr2.type == Type.Double){
           reg3 = Registers.getDoubleReg();
       } else if(expr1.type == Type.Float){
           reg3 = Registers.getFloatingPointReg();
       } else{
           reg3 = Registers.getTempReg();
       }
       sb.append(ASMGen.genMath(this, reg3, reg1, reg2));  //Generate Assembly for a mathematical operation

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
       return reg3;
   }


    /**
     * Set the types of registers to be used and generate assembly for this operation
     * @return assembly code for the arithmetic
     */
    private String genAsm(){
        StringBuilder sb = new StringBuilder();

        sb.append(genLoadString(expr1));    //Load the first expression to the first register
        reg1 = expr1.getResultRegister();
        sb.append(genLoadString(expr2));
        reg2 = expr2.getResultRegister();

        //The result register
        if(expr1.type == Type.Double || expr2.type == Type.Double){
            reg3 = Registers.getDoubleReg();
        } else if(expr1.type == Type.Float){
            reg3 = Registers.getFloatingPointReg();
        } else{
            reg3 = Registers.getTempReg();
        }

        sb.append(ASMGen.genMath(this, reg3, reg1, reg2));  //Generate Assembly for a mathematical operation

        return sb.toString();
    }


    /**
     * Method to generate the assembly load for an identifier or constant
     * @param expr the expression
     * @return the formatted string
     */
    private String genLoadString(Expr expr){

        //If the expression is an identifier
        if(expr.isIdentifier(expr.getOp())){
            Id id = Env.getCurrent().get(expr.getOp());     //Get the identifier from symbol table
            return String.format("%s", id.toAsmMain());     //Return the formatted string
        } else{
            return String.format("%s", expr.toAsmMain());    //Generate the code to load a constant
        }
    }

}
