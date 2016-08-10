package inter;
import code_generation.AssemblyFile;
import code_generation.Registers;
import information.Printer;
import lexer.Char;
import lexer.Real;
import semantics.TypeCasting;
import symbols.*;

/**
 * Updates the types of the assignments
 * creates a node for variable assignment
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Set extends Stmt {

   public Id id; 
   public Expr expr;

   private static final String ERROR = "ERROR SET: generating assignment";

   public Set(Id i, Expr x) {
      id = i;
      expr = x;
      if(expr.isConstant() && i.type.isFloatingPoint()){
         expr.type = i.type;
      } else{
         expr = TypeCasting.updateAssignmentTypes(id, expr);
      }

      if ( check(id.type, expr.type) == null ) error("type error");

      Printer.writeAssignment(this);
   }


   public String toString(){
      return "Assign: (" + id.type.lexeme + ") " + id._word.lexeme + " = (" + expr.type.lexeme + ") " + expr.getValue();
   }

   public Type check(Type p1, Type p2) {
      if ( Type.numeric(p1) && Type.numeric(p2) ) 
          return p2;
      else if ( p1 == Type.Bool && p2 == Type.Bool ) 
          return p2;
      else if(p1 == Type.Char && p2 == Type.Char){
          return p2;
      }
      else return null;
   }

   @Override
   public void gen(int b, int a) {
      AssemblyFile.addToMain(this.toAsmMain());
      AssemblyFile.addVariables(this.toAsmData());
      emit( id.toString() + " = " + expr.gen().toString() );
   }

   @Override
   public String toAsmMain() {
      String code = genAssignment();
      Registers.clearAllRegs();
      return code;
   }

   /**
    * For any floating point values, they need to be declared initially as a global variable (MIPS problem)
    * To account for this, any floating point variables are added to the data section from here.
    * @return
     */
   @Override
   public String toAsmData() {
      if(expr.isConstant()){
         if(expr.type.isFloatingPoint()){
            Real real = (Real) expr.op;
            return String.format("%s:\t%s\t%f\n", id.getName(), id.genAsmDataType(), real.value);
         } else if(expr.type == Type.Char){
            Char character = (Char) expr.op;
            return String.format("%s:\t%s\t\'%s\'\n", id.getName(), id.genAsmDataType(), character.value);
         }
      } else if(id.type.isFloatingPoint()){
         return String.format("%s:\t%s\t 0,0,0\n", id.getName(), id.genAsmDataType());
      }
      return super.toAsmData();
   }

   /**
    * Assign:
    * id = expr
    * @return
     */
   private String genAssignment(){
      StringBuilder sb = new StringBuilder();

      if(expr.isConstant()){
         Constant constant = (Constant) expr;
         sb.append(constant.load(id.getName()));
      }else{
         sb.append(expr.toAsmMain());                  //Generate the code for the expression
      }
      String savedReg = expr.getResultRegister();   //Get the register of the saved operation
      sb.append(String.format("\t%s\t %s, %s\t\t", this.genStoreVariable(), savedReg, id.getName()));
      sb.append(String.format("#Store the value at the address of %s\n\n", id.getName()));

      return sb.toString();
   }

   private String genStoreVariable(){
      Type type = id.type;
      switch (type.lexeme){
         case "int":
            return "sw";
         case "long":
            return "sw";
         case "float":
            return "s.s";
         case "double":
            return "s.d";
         case "char":
            return "sb";
         case "boolean":
            return "la";
      }
      return ERROR;
   }

   @Override
   public String getStoreType(Type type, String error) {
      return super.getStoreType(type, error);
   }

   @Override
   public String getLoadType(Type type, String error) {
      return super.getLoadType(type, error);
   }
}
