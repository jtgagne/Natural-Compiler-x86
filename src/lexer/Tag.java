package lexer;

/**
 * Define constants for Tokens (for readability)
 * Tags for various tokens
 * Refactored to group like tags
 * Created on 6/5/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Tag {

    public final static int
        END         = 0;

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
        BASIC       = 267,          //For characters
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

    //Data type tags
    public static final int
        INT         = 290,
        LONG        = 291,
        FLOAT       = 292,
        DOUBLE      = 293,
        CHAR        = 294,
        BOOL        = 295;


    public static boolean isDataType(int tag){
        return (tag == Tag.INT ||
                tag == Tag.LONG ||
                tag == Tag.FLOAT ||
                tag == Tag.DOUBLE ||
                tag == Tag.CHAR ||
                tag == Tag.BOOL);
    }

}