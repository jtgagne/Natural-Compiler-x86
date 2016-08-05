package lexer;

/**
 * A class to store content to be printed to the console.
 * Created by gagnej3 on 8/4/16.
 */
public class Print extends Token{
    public final String value;

    public Print(String c){
        super(Tag.PRINT);
        value = c;
    }

    @Override
    public String toString() {
        return "OUTPUT: value is \'" + value + "\' and tag is " + tag;
    }


    @Override
    public boolean isPrint() {
        return true;
    }
}
