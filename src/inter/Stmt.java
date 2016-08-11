package inter;

import code_generation.AssemblyFile;

/**
 * Base class for statements
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Stmt extends Node {

    public static int after = 0;                   // saves label after

    public Stmt() {

    }

    public static String labelAfter;

    public static Stmt Null = new Stmt();

    // called with labels begin and after
    public void gen(int b, int a) {
       after = a;
   }

    public void setAfter(int a){
        after = a;
    }

    public String getLabelAfter(){
        return String.format("L%d", after);
    }


    public static Stmt Enclosing = Stmt.Null;  // used for break stmts

    public boolean isBreak(){
        return false;
    }

    public boolean isDo(){
        return false;
    }

    public boolean isElse(){
        return false;
    }

    public boolean isFor(){
        return false;
    }

    public boolean isIf(){
        return false;
    }

    public boolean isInputNode(){
        return false;
    }

    public boolean isPrintNode(){
        return false;
    }

    public boolean isSeq(){
        return false;
    }

    public boolean isSet(){
        return false;
    }

    public boolean isWhile(){
        return false;
    }

}
