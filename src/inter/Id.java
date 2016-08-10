package inter;
import code_generation.Registers;
import information.Printer;
import lexer.*;
import symbols.*;

/**
 * Object class for storing information regarding an identifier
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Id extends Expr {

    public int offset;     // relative address
    public final Word _word;
    public Type mType;
    private String register;    //Output register

    public Id(Word id, Type p, int b){
        super(id, p); 
        offset = b;
        _word = id;
        mType = p;
        Printer.writeIdentifier(this);
    }

	public String toString() {
        return "Identifier: " + _word.lexeme + "\t TYPE: " + mType.lexeme;
    }

    public String getName(){
        return _word.lexeme;
    }

    public String getType() {
        return mType.lexeme;
    }

    @Override
    public Expr gen() {
        return super.gen();
    }

    @Override
    public String toAsmData() {
        if(mType == Type.Char) return null;
        return String.format("%s:\t%s\t0,0,0\n", _word.lexeme, genAsmDataType());
    }

    @Override
    public String toAsmMain() {
        return getAsmLoad();
    }

    public String genAsmDataType(){
        switch (type.lexeme){
            case "int":
                return ".word";
            case "long":
                return ".word";
            case "float":
                return ".float";
            case "double":
                return ".double";
            case "char":
                return ".byte";
            case "boolean":
                return ".asciiz";
        }
        return "IDENTIFER ERROR";
    }

    public String getAsmLoad(){
        if(mType == Type.Float){
            register = Registers.getFloatingPointReg();
        } else if (mType == Type.Double){
            register = Registers.getDoubleReg();
        } else{
            register = Registers.getTempReg();    //Set the output register
        }

        switch (mType.lexeme){
            case "int":
                return String.format(
                        "\tlw\t %s, %s\n", register, _word.lexeme); //Load int into temp register
            case "long":
                 return String.format(
                         "\tlw\t %s, %s\n", register, _word.lexeme); //Load int into temp register
            case "float":
                return String.format(
                        "\tl.s\t %s, %s\n", register, _word.lexeme); //Load float into temp register
            case "double":
                return String.format(
                        "\tl.d\t %s, %s\n", register, _word.lexeme); //Load double into temp register
            case "char":
                return String.format(
                        "\tlb\t %s, %s\n", register, _word.lexeme); //Load char into temp register
            case "boolean":
                return String.format(
                        "\tld\t %s, %s\n", register, _word.lexeme); //Load boolean into temp register
        }
        return "IDENTIFER ERROR: Loading data type asm code";
    }

    @Override
    public String getResultRegister() {
        return register;
    }
}


