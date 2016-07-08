package lexer;

/**
 *
 * Created by gagnej3 on 7/7/16.
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
}
