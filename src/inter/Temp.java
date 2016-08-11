package inter;
import code_generation.Registers;
import lexer.*;
import symbols.*;
import symbols.Type;

/**
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Temp extends Expr 
{

   static int count = 0;
   int number = 0;

   public Temp(Type p) {
       super(Word.temp, p); 
       number = ++count; 
   }

   @Override
   public String toString() {
       //return "t" + number;
       return Registers.getTempReg();
   }
}
