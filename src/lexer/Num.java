package lexer;

/**
 * Object class for storing information on an integer token value
 * Created by Justin Gagne on 6/5/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Num extends Token {
    public final int value;

    public Num(int v)
    {
        super(Tag.NUM);
        value = v;
    }
    @Override
    public String toString() {
        return "NUM: value is " + value + " and tag is " + tag;
    }

    @Override
    public boolean isNum() {
        return true;
    }
}
