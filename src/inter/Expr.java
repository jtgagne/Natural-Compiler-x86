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

    //Member variables for an expression
    protected Token mToken;
    protected Type mType;
    protected String mRegister;

    public Expr(Token tok, Type type) {
        mToken = tok;
        mType = type;
    }

    @Override
    public String getResultRegister() {
        return mRegister;
    }

    public String getTypeStr(){
        return mType.lexeme;
    }

    public Type getType(){
        return mType;
    }

    public void setType(Type type){
        this.mType = type;
    }

    /**
     * Only overridden by Constant and Id class. Generate Assembly to load the value into a register
     * Sets the mRegister value of the node which can be obtained using the getResultRegister() method
     * on the object
     * @return Assembly code to load the value
     */
    public String load(){
        return null;
    }

    /**
     * Return the name of an Identifier or Constant
     * @return
     */
    public String getName(){
        return null;
    }

    public Expr gen() {
       return this;
    }

    public Expr reduce() {
       return this;
    }

    /**
     * Add assembly code to the main function and print the labels
     * @param t start label
     * @param f end label
     */
    public void jumping(int t, int f) {
       emitjumps(this.toAsmMain(), t, f);
    }


    public void emitjumps(String test, int t, int f) {
        if( t != 0 && f != 0 ) {
            emit("L" + t + ":\n");
            emit(test);
            emit("L" + f + ":\n" + test);
        }
        else if( t != 0 ){
            emit("L" + t + ":\n" + test);
        }

        else if( f != 0 ){
           // emit("L" + f + ":\n");
        }
    }

    @Override
    public String toAsmMain() {
        return super.toAsmMain();
    }

    @Override
    public String toString() {
        return mToken.toString();
    }

    public Token getToken(){
        return mToken;
    }

    //Constants, identifiers, and temps
    public boolean isConstant(){
        return false;
    }

    public boolean isIdentifier(){
        return false;
    }

    public boolean isTemp(){
        return false;
    }

    //Logical boolean types of expressions
    public boolean isLogical(){
        return false;
    }

    public boolean isAnd(){
        return false;
    }

    public boolean isNot(){
        return false;
    }

    public boolean isOr(){
        return false;
    }

    public boolean isRel(){
        return false;
    }

    //Operational expressions
    public boolean isOp(){
        return false;
    }

    public boolean isArith(){
        return false;
    }

    public boolean isUnary(){
        return false;
    }

    public String getValue(){
        if(mType.isChar()){
            Char c = (Char) mToken;
            String str = "";
            str += c.value;
            return str;
        } else if(mType.isWord()){
            Word word = (Word) mToken;
            return word.lexeme;
        } else if(mToken.isType()){
            Type type = (Type) mToken;
            return type.lexeme;
        } else if(mToken.isPhrase()){
            Phrase phrase = (Phrase) mToken;
            return phrase.getLexeme();
        } else if(mToken.isNum()){
            Num num = (Num) mToken;
            return String.valueOf(num.value);
        } else if(mToken.isReal()){
            Real real = (Real) mToken;
            return String.valueOf(real.value);
        } else if (mToken.isPrint()){
            Print print = (Print) mToken;
            return String.valueOf(print.value);
        }

        return mToken.toString();
    }

}
