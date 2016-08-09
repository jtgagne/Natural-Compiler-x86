package inter;
import code_generation.AssemblyFile;
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
    public Type _type;

    public Id(Word id, Type p, int b){
        super(id, p); 
        offset = b;
        _word = id;
        _type = p;
        Printer.writeIdentifier(this);
    }

	public String toString() {
        return "Identifier: " + _word.lexeme + "\t TYPE: " + _type.lexeme;
    }

    public String getName(){
        return _word.lexeme;
    }

    public String getType() {
        return _type.lexeme;
    }

    @Override
    public Expr gen() {
        return super.gen();
    }

    @Override
    public String toAsmData() {
        return String.format("%s:\t%s\n", _word.lexeme, getAsmDataType());
    }

    private String getAsmDataType(){
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
}


