package lexer;

import symbols.Type;

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
    private String constantId;
    private Type mType;

    public Real(float v) {
        super(Tag.REAL);
        value = v;
    }

    @Override
    public String toString() {
        return "REAL: value is " + value + " and tag is " + tag;
    }

    @Override
    public String toAsmConstant() {
        return genConstantString();
    }

    private String genConstantString(){
        switch (mType.lexeme){
            case "double":
                return String.format("%s:\t.double\t%f\n", this.constantId, value);
            case "float":
                return String.format("%s:\t.float\t%f\n", this.constantId, value);
        }

        return "ERROR REAL: Generating a constant\n";
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
    public void setType(Type t){
        mType = t;
    }

    @Override
    public void setConstantId(String constantId){
        this.constantId = constantId;
    }

    @Override
    public String valueToString() {
        return Float.toString(value);
    }
}
