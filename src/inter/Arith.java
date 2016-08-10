package inter;
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

   @Override
   public String toAsmMain() {
      String code = genAsm();
      Registers.clearAllRegs();
      return code;
   }

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
      sb.append(getOperationString(reg3, reg1, reg2));

      return sb.toString();
   }

   @Override
   public String getResultRegister() {
      return reg3;
   }

   private String getOperationString(String saveReg, String reg1, String reg2){
      Token token = this.getOp();
      switch (token.tag){
         case '+':
            if(type == Type.Float){
               return String.format("\tadd.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
            }else if(type == Type.Double){
               return String.format("\tadd.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
            }
            return String.format("\tadd\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
         case '-':
            if(type == Type.Float){
               return String.format("\tsub.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
            }else if(type == Type.Double){
               return String.format("\tsub.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
            }
            return String.format("\tsub\t %s, %s, %s\t\t#subtract the two registers\n\n", saveReg, reg1, reg2);
      }
      return "ERROR ARITH: generating operand\n";
   }

   /**
    * Method to generate the assembly load for an identifier or constant
    * @param expr the expression
    * @return the formatted string
     */
   private String genLoadString(Expr expr){

      //If the expression is an identifier
      if(expr.isIdentifier(expr.getOp())){
         Id id = Env.getCurrent().get(expr.getOp());              //Get the identifier from symbol table
         return String.format("%s", id.toAsmMain());  //Return the formatted string
      } else{
         return String.format("%s", expr.load(null));  //Generate the code to load a constant
      }
   }


}
