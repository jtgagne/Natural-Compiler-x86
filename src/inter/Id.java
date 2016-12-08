package inter;
import code_generation.*;
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
    private final String mAsmId;
    private boolean isInitialized = false;

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
        mAsmId = String.format("%s%s", this.genVarName(), mWord.lexeme);
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
        //return mWord.lexeme;
        return mAsmId;
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
        return ASMGen.genDeclaration(this, "?"); //Gen the data declaration with default initial value
    }

    public String genDeclaration(Token assignTo){
        return ASMGen.genDeclaration(this, assignTo.valueToString());
    }


    @Override
    public String toAsmMain() {
        return load();
    }

    /**
     * AsmLoad this identifier into memory
     * @return assembly code
     */
    @Override
    public String load() {
        Register register;

        if(mType == Type.Long) {
            register = RegisterManager.getGeneralPurpose32();   // Get 32 bit register
        }
        else if(mType == Type.Bool){
            register = RegisterManager.getGeneralPurpose8();
        }
        else{
            register = RegisterManager.getGeneralPurpose16();   // Get 16 bit register
        }

        mRegister = register.toString();    //Set the output register
        return AsmLoad.loadVariable(this, mRegister);
    }

    public String loadForArithmetic(){
        Register register;
        if(mType == Type.Long) {
            register = RegisterManager.getGeneralPurpose32();   // Get 32 bit register
        } else if(mType == Type.Float || mType == Type.Double){
            register = RegisterManager.getFPURegister();        // Get a register from the FPU stack
        } else {
            register = RegisterManager.getGeneralPurpose16();   // Get 16 bit register
        }
        mRegister = register.toString();
        return AsmLoad.loadVariableForArith(this, mRegister);
    }

    @Override
    public String getResultRegister() {
        return super.getResultRegister();
    }
}


