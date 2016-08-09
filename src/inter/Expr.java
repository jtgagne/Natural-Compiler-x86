package inter;
import lexer.*; 
import symbols.*;


/**
 * A node to contain an expression
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Expr extends Node {

    public Token op;
    public Type type;

    public String getType(){
        return type.lexeme;
    }

    public String getValue(){
        if(op.isChar()){
            Char c = (Char) op;
            String str = "";
            str += c.value;
            return str;
        } else if(op.isWord()){
            Word word = (Word) op;
            return word.lexeme;
        } else if(op.isType()){
            Type type = (Type) op;
            return type.lexeme;
        } else if(op.isPhrase()){
            Phrase phrase = (Phrase) op;
            return phrase.getLexeme();
        } else if(op.isNum()){
            Num num = (Num) op;
            return String.valueOf(num.value);
        } else if(op.isReal()){
            Real real = (Real) op;
            return String.valueOf(real.value);
        } else if (op.isPrint()){
            Print print = (Print) op;
            return String.valueOf(print.value);
        }

        return op.toString();
    }

    public Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }

    public Expr gen()
    {
       return this;
    }

    public Expr reduce()
    {
       return this;
    }

    public void jumping(int t, int f)
    {
       emitjumps(toString(), t, f);
    }

    public void emitjumps(String test, int t, int f)
    {
        if( t != 0 && f != 0 ) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        }
        else if( t != 0 )
            emit("if " + test + " goto L" + t);
        else if( f != 0 )
            emit("iffalse " + test + " goto L" + f);
        else ; // nothing since both t and f fall through
    }

    @Override
    public String toString()
    {
       return op.toString();
    }

}
