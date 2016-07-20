package inter;
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
    Word _word;
    Type _type;

    public Id(Word id, Type p, int b){
        super(id, p); 
        offset = b;
        _word = id;
        _type = p;
        Printer.printIdentifier(this);
    }

	public String toString() {
        return "Identifier: " + _word.lexeme + "\t TYPE: " + _type.lexeme + "\n";
    }

    public String getName(){
        return _word.lexeme;
    }
}


