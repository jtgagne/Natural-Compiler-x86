package inter;
import lexer.*; import symbols.*;

/**
 * Stores data regarding an operator
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Op extends Expr 
{
   public Op(Token tok, Type p)  
   { 
       super(tok, p); 
   }

   @Override
   public Expr reduce() 
   {
      Expr x = gen();
      Temp t = new Temp(type);
      emit( t.toString() + " = " + x.toString() );
      return t;
   }
}
