package inter;
import information.Printer;
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

   public Set(Id i, Expr x) 
   {
      id = i;
      expr = x;

      expr = TypeCasting.updateAssignmentTypes(id, expr);

      if ( check(id.type, expr.type) == null ) 
          error("type error");

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
      emit( id.toString() + " = " + expr.gen().toString() );
   }

}
