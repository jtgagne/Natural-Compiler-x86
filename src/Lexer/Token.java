package Lexer;

/**
 * Created by gagnej3 on 6/5/16.
 */
public class Token {
    public final int tag1;
    public final int tag2;

    public Token(int t) {
        tag1 = t;

        //Not required for this token
        tag2 = Tag.NULL;
    }

    /**
     * Constructor to be used for more complex tags when using phrases. EX: Less than or equal to.
     * @param t1 first tag ex: less than
     * @param t2 second tag ex: equal to
     */
    public Token(int t1, int t2){
        tag1 = t1;
        tag2 = t2;
    }

    @Override
    public String toString() {
        if(tag2 == Tag.NULL){
            return "TOKEN: tag is " + (char)tag1;
        }
        else{
            return "TOKEN: tag1 is " + (char)tag1 + " TOKEN: tag2 is " + (char)tag2;
        }
    }
}
