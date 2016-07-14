package inter;
import lexer.*; import symbols.*;

/**
 * Node for a logical expression
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Logical extends Expr 
{
   public Expr expr1, expr2;

   Logical(Token tok, Expr x1, Expr x2) 
   {
      super(tok, null);                      // null type to start
      expr1 = x1; 
      expr2 = x2;
      
      type = check(expr1.type, expr2.type);
      
      if (type == null ) 
          error("type error");
   }

   // Verify both types are bool
   public Type check(Type p1, Type p2) 
   {
      if ( p1 == Type.Bool && p2 == Type.Bool ) 
          return Type.Bool;
      else 
          return null;
   }

   @Override
   public Expr gen() {
      int f = newlabel();                                   //Label for false part
      int a = newlabel();                                   //Label after false
      Temp temp = new Temp(type);                           //Holds logical result of expression
      this.jumping(0,f);                                    //Depends on operator (And, Or, Not ...) .. Fall through on true and generate the label on false
      emit(temp.toString() + " = true");                    // Put in a line for a temporary as true
      emit("goto L" + a);                                   // Goto
      emitlabel(f);
      emit(temp.toString() + " = false");                   //Print out a line that t2 is false
      emitlabel(a);                                         // Goto a if t2 is false
      return temp;
   }

   @Override
   public String toString() {
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
