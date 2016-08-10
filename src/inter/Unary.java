package inter;
import code_generation.ASMGen;
import code_generation.Registers;
import lexer.*;
import symbols.*;

/**
 * Class for storing information on a unary node
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Unary extends Op {

   public Expr expr;

   public Unary(Token tok, Expr x) {    // handles minus, for ! see Not
      super(tok, null);
      expr = x;
      type = Type.max(Type.Int, expr.type);
      if (type == null )
          error("type error");
   }

   @Override
   public Expr gen() { return new Unary(op, expr.reduce()); }

   @Override
   public String toString()
   {
       return op.toString()+" "+expr.toString();
   }

}
