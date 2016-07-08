package lexer;

/**
 * Object to be used for storing real number values
 * Created on 6/5/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Real extends Token {
    public final float value;

    public Real(float v)
    {
        super(Tag.REAL);
        value = v;
    }

    @Override
    public String toString() {
        return "REAL: value is " + value + " and tag is " + tag;
    }
}
