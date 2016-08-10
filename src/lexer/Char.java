package lexer;

import symbols.Type;

/**
 * Created on 7/7/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Char extends Token{
    public final char value;
    private String constantId;

    public Char(char c){
        super(Tag.CHAR);
        value = c;
    }

    @Override
    public String toString() {
        return "CHAR: value is " + value + " and tag is " + tag;
    }

    @Override
    public boolean isChar() {
        return true;
    }
    @Override
    public String toAsmConstant() {
        return String.format("%s:\t.byte\t\'%s\'\n", constantId, value);
    }


    @Override
    public String getConstantId() {
        return this.constantId;
    }

    @Override
    public boolean isReal() {
        return true;
    }

    @Override
    public void setConstantId(String constantId){
        this.constantId = constantId;
    }
}
