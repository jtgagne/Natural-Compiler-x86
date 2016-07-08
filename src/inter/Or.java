package inter;
import lexer.*;

/**
 * Stores data regarding an OR join
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Or extends Logical {

   public Or(Token tok, Expr x1, Expr x2) 
   { 
       super(tok, x1, x2); 
   }

   @Override
   public void jumping(int t, int f) 
   {
      int label = t != 0 ? t : newlabel();
      expr1.jumping(label, 0);
      expr2.jumping(t,f);
      if( t == 0 ) emitlabel(label);
   }
}
