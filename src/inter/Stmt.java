package inter;

/**
 * Base class for statements
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Stmt extends Node 
{
   public Stmt() { }

   public static Stmt Null = new Stmt();

   public void gen(int b, int a) {} // called with labels begin and after

   int after = 0;                   // saves label after

   public static Stmt Enclosing = Stmt.Null;  // used for break stmts

    public boolean isEnd(){
        return false;
    }
}
