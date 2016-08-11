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

    /**
     * reset the static variables
     */
    public static void resetLabels(){
        labels = 0;
        msg = 0;
    }

    /**
     * Generate the next message identifier to be added to .data
     * @return formatted identifier
     */
    public String genMessage(){
        return String.format("msg%d", ++msg);
    }


    void error(String s) {
        throw new Error("near line "+lexline+": "+s);
    }

    /**
     * return the next label
     * @return incremented label value
     */
    public int newlabel() {
        return ++labels;
    }

    /**
     * Generate the next label
     * @return formatted label string
     */
    public String genLabel(){
        return String.format("L%d", ++labels);
    }

    /**
     * Generate a label for a block
     * @param labelNumber the number
     * @return the formatted string
     */
    public String genLabel(int labelNumber){
        return String.format("L%d", labelNumber);
    }

    /**
     * Write formatted assembly code to the main function of the file.
     * @param s formatted assembly code
     */
    public void emit(String s) {
        if(s != null){
            AssemblyFile.addToMain(s);
        }
    }

    /**
     * Convert a node to Assembly .data declarations when appropriate
     * @return .data declaration
     */
    public String toAsmData(){
        return null;
    }

    /**
     * Assembly Code that is to be appended to the main
     * @return formatted assembly code
     */
    public String toAsmMain(){
        return null;
    }

    /**
     * Any string constants that should be generated
     * @return constant declarations
     */
    public String toAsmConstants(){
        return null;
    }

    /**
     * Return the register the result of an operation is stored in.
     * @return the register
     */
    public String getResultRegister(){
        return null;
    }

}
