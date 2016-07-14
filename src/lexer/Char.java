package lexer;

/**
 * Created on 7/7/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Char extends Token{
    public final char value;

    public Char(char c){
        super(Tag.BASIC);
        value = c;
    }

    @Override
    public String toString() {
        return "BASIC: value is " + value + " and tag is " + tag;
    }

    @Override
    public boolean isChar() {
        return true;
    }
}
