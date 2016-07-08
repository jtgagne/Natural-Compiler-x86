package inter;
import lexer.Tag;
import lexer.Token;
import symbols.*;

/**
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
      if(id.type.equals(Type.Long) && expr.type.equals(Type.Int)){
         Token token = expr.op;
         expr = new Expr(token, Type.Long);
      }else if(id.type.equals(Type.Double) && expr.type.equals(Type.Float)){
         Token token = expr.op;
         expr = new Expr(token, Type.Double);
      } else if(id.type.equals(Type.Char) && expr.op.tag == Tag.BASIC){
          expr.type = Type.Char;
      }

      if ( check(id.type, expr.type) == null ) 
          error("type error");
   }

   public Type check(Type p1, Type p2) 
   {
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
