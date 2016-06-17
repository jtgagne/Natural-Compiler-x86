package lexer;

/**
 * TODO: Make the containsParentheses method more flexible in the case of refactoring
 * Define constants for Tokens (for readability)
 * Refactored to group like tags
 * Created by Justin Gagne on 6/5/16.
 */
public class Tag {

    //Values start at 256

    //Join operators
    public final static int
            AND         = 256,
            NOT         = 257,
            OR          = 258;

    //Control values
    public final static int
            BREAK       = 259,
            DO          = 260,
            FOR         = 261,
            ELSE        = 262,
            IF          = 263,
            WHILE       = 264;

    //Boolean values
    public static final int
            FALSE       = 265,
            TRUE        = 266;

    //Data types
    public static final int
            BASIC       = 267,
            NUM         = 268,
            REAL        = 269;

    //Comparison operators
    public static final int
            EQ          = 270,
            GE          = 271,
            GREATER     = 272,
            LE          = 273,
            LESS        = 274,
            NE          = 275,
            THAN        = 276;

    //Phrase Identification values
    public static final int
            ERROR       = 277,
            INITIALIZER = 278,
            NULL        = 279,
            PHRASE      = 280,
            TERMINAL    = 281;

    //Incrementation  / looping operators
    public static final int
            DECREASE    = 282,
            INCREASE    = 283,
            TO          = 284;

    //Variable declaration and assignment
    public static final int
            ASSIGNMENT  = 285,
            ID          = 286,
            INDEX       = 287,
            TEMP        = 288;

    //Arithmetic operators
    public static final int
            MINUS       = 289;

    //End of file
    public static final int
            EOF         = 290;

    public static final int
            PAR1        = 291,
            PAR2        = 292,
            PAR3        = 293,
            PAR4        = 294,
            PAR5        = 295,
            PAR6        = 296,
            PAR7        = 297,
            PAR8        = 298,
            PAR9        = 299;


    /**
     * Check if a tag is a valid boolean value
     * @param tag the tag of the token
     * @return true if it is a boolean
     */
    public static boolean isBoolean(int tag){
        return (tag == Tag.FALSE || tag == Tag.TRUE);
    }

    /**
     * Check is a tag is classified as a number, real or int
     * @param tag the tag of the token
     * @return true if the tag is refers to a number
     */
    public static boolean isNumber(int tag){
        return (tag == Tag.NUM ||
                tag == Tag.REAL);
    }

    public static boolean isComparisonOperator(int tag){
        return (tag == Tag.EQ ||
                tag == Tag.GE ||
                tag == Tag.GREATER ||
                tag == Tag.LE ||
                tag == Tag.LESS ||
                tag == Tag.NE);
    }

    public static boolean isJoiningOperator(int tag){
        return (tag == Tag.AND ||
                tag == Tag.OR);
    }

    public static boolean containsParentheses(String input){
        return input.contains("291") ||
                input.contains("292")||
                input.contains("293")||
                input.contains("294")||
                input.contains("295")||
                input.contains("296")||
                input.contains("297")||
                input.contains("298")||
                input.contains("299");
    }
}