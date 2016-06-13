package Lexer;

/**
 *
 * Created by Justin Gagne on 6/5/16.
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
        return "REAL: value is " + value + " and tag is " + tag1;
    }
}
