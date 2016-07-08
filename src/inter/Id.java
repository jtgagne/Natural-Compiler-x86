package inter;
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
    Word _word;
    Type _type;

    public Id(Word id, Type p, int b) 
    { 
        super(id, p); 
        offset = b;
        _word = id;
        _type = p;
    }

	public String toString() {
        return "Line: " + (Lexer.lineCount - 1) + "\tIdentifier: " + _word.lexeme + "\t Type: " + _type.lexeme + "\n";
    }

    public String getName(){
        return _word.lexeme;
    }
}
