package lexer;

/**
 * Created by Justin Gagne on 6/5/16.
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
        return "NUM: value is " + value + " and tag is " + tag1;
    }
}
