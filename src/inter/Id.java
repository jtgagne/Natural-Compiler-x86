package inter;
import code_generation.ASMGen;
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

    public int mOffset;            //relative address
    private final Word mWord;

    //Member variables inherited from Expr:
    //Member variables for an expression
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    public Id(Word identifier, Type type, int offset){
        super(identifier, type);                        //Expr(Token tok, Type type)
        mOffset = offset;
        mWord = identifier;
        mType = type;
    }

    public String toString() {
        return "Identifier: " + mWord.lexeme + "\t TYPE: " + mType.lexeme;
    }

    @Override
    public boolean isIdentifier() {
        return true;
    }

    /**
     * Return the variable's name
     * @return name
     */
    public String getName(){
        return mWord.lexeme;
    }

    public String getTypeStr() {
        return mType.lexeme;
    }

    @Override
    public void jumping(int t, int f) {
        super.jumping(t, f);
    }

    /**
     * @return line to be used as a global variable --> add to .data in assembly file.
     */
    @Override
    public String toAsmData() {
        return ASMGen.toData(this, "0,0,0"); //Gen the data declaration with default initial value
    }

    @Override
    public String toAsmMain() {
        return load();
    }

    /**
     * Load this identifier into memory
     * @return assembly code
     */
    @Override
    public String load() {
        //Set the appropriate register value before generating assembly
        if(mType == Type.Float){
            mRegister = Registers.getFloatingPointReg();
        } else if (mType == Type.Double){
            mRegister = Registers.getDoubleReg();
        } else{
            mRegister = Registers.getTempReg();    //Set the output register
        }
        return ASMGen.loadVar(this, mRegister);
    }

    @Override
    public String getResultRegister() {
        return super.getResultRegister();
    }
}


