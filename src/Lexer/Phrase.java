package Lexer;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * TODO: so far most terms work. However we need a better way of classifying stuff.
 * TODO: Phrases are formed by identifying an "INITIALIZER" aka a term to start a phrase like: "greater than or equal to"
 * TODO: Where "to" is the terminal term to the phrase.
 * Class to be used to reserve the various phrases used to equality checking
 * Created by Justin Gagne on 6/5/16.
 */
public class Phrase extends Token{

    public static final Phrase
            and = new Phrase("and", Tag.AND),
            or = new Phrase( "or", Tag.OR),
            eq = new Phrase( "equal to", Tag.EQ),
            ne = new Phrase( "not equal to", Tag.EQ),
            le = new Phrase( "less than or equal to", Tag.LE),
            ge = new Phrase( "greater than or equal to", Tag.GE),
            gt = new Phrase("greater than", Tag.GREATER),
            lt = new Phrase("less than", Tag.LESS),
            increase = new Phrase("increase by", Tag.INCREASE),
            decrease = new Phrase("decrease by", Tag.DECREASE),
            not = new Phrase( "not", Tag.NOT)
            ;

    public final String lexeme;

    public Phrase(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    /**
     * Initialize the reserved phrases and add all the phrases to this arraylist.
     */
    public static void reservePhrases(Hashtable phrases){
        phrases.put(and.lexeme, and);
        phrases.put(or.lexeme, or);
        phrases.put(eq.lexeme, eq);
        phrases.put(ne.lexeme, ne);
        phrases.put(le.lexeme, le);
        phrases.put(ge.lexeme, ge);
        phrases.put(gt.lexeme, gt);
        phrases.put(lt.lexeme, lt);
        phrases.put(increase.lexeme, increase);
        phrases.put(decrease.lexeme, decrease);
        phrases.put(not.lexeme, not);
    }

    public String getLexeme(){
        return lexeme;
    }

    public int getTag(){
        return tag1;
    }
}
