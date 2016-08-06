package inter;

import lexer.Lexer;

/**
 * The base node class
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Node {

   int lexline = 0;

   Node() {
      lexline = Lexer.lineCount;
   }

   void error(String s) 
   { 
       throw new Error("near line "+lexline+": "+s);
   }

   static int labels = 0;
   static int printLabel = 0;
   public int newPrintLabel(){ return ++printLabel;}
   public int newlabel() { return ++labels; }

   public void emitlabel(int i) {
      //System.out.print("L" + i + ":");
   }
   public void emit(String s) {
      System.out.println(s);
   }
}
