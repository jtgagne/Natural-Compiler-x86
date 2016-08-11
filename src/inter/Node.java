package inter;

import code_generation.ASMGen;
import code_generation.AssemblyFile;
import lexer.Lexer;
import lexer.Token;
import symbols.Env;
import symbols.Type;

/**
 * The base node class
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Node {

    private static int labels = 0;
    private static int msg = 0;

    int lexline = 0;

    Node() {
        lexline = Lexer.lineCount;
    }

    public static void resetLabels(){
        labels = 0;
        msg = 0;
    }

    public String genMessage(){
        return String.format("msg%d", ++msg);
    }

    void error(String s) {
        throw new Error("near line "+lexline+": "+s);
    }

    public int newlabel() {
        return ++labels;
    }

    public String genLabel(){
        return String.format("L%d", ++labels);
    }

    public String genLabel(int labelNumber){
        return String.format("L%d", labelNumber);
    }

    public void emit(String s) {
        if(s != null){
            AssemblyFile.addToMain(s);
        }
    }

    public String toAsmData(){
        return null;
    }

    public String toAsmMain(){
        return null;
    }

    public String toAsmConstants(){
        return null;
    }

    public String getResultRegister(){
        return null;
    }

}
