package lexer;

/**
 * Stores a token read in by the lexer
 * Created on 6/5/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Token {
    public final int tag;
    public final int tag2;

    public Token(int t) {
        tag = t;

        //Not required for this token
        tag2 = Tag.NULL;
    }

    /**
     * Constructor to be used for more complex tags when using phrases. EX: Less than or equal to.
     * @param t1 first tag ex: less than
     * @param t2 second tag ex: equal to
     */
    public Token(int t1, int t2){
        tag = t1;
        tag2 = t2;
    }

    @Override
    public String toString() {
        if(tag2 == Tag.NULL){
            return "TOKEN: tag is " + (char) tag;
        }
        else{
            return "TOKEN: tag is " + (char) tag + " TOKEN: tag2 is " + (char)tag2;
        }
    }

    public boolean isWord(){
        return false;
    }
    public boolean isPhrase(){
        return false;
    }
    public boolean isType(){
        return false;
    }
    public boolean isChar(){ return false; }
    public boolean isNum(){ return false; }
    public boolean isReal() { return false; }
    public boolean isPrint(){ return false; }
}
