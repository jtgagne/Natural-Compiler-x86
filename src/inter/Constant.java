package inter;
import lexer.*;
import symbols.*;

/**
 * Create a node containing a numerical constant value
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Constant extends Expr 
{
   public Constant(Token tok, Type p) {
      super(tok, p);
   }
   public Constant(int i) 
   { 
       super(new Num(i), Type.Int); 
   }

   public static final Constant
      True  = new Constant(Word.True,  Type.Bool),
      False = new Constant(Word.False, Type.Bool);

   @Override
   public String toString() {
      return op.toString() + " " + type.toString();
   }

   @Override
   public void jumping(int t, int f)
   {
      if ( this == True && t != 0 ) emit("goto L" + t);
      else if ( this == False && f != 0) emit("goto L" + f);
   }
}
