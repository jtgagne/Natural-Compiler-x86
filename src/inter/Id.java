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

    /**
     * Generate a line to be used as a global variable
     * @return
     */
    @Override
    public String toAsmData() {
        String data = ASMGen.toData(this, "0,0,0"); //Gen the data declaration with default initial value
        return data == null ? "ERROR ID: generating data" : data;
    }

    /**
     * Get the assembly to load an identifier
     * @return assembly
     */
    @Override
    public String toAsmMain() {
        return getAsmLoad();
    }

    /**
     * Generate assembly to load a variable
     * @return assembly
     */
    public String getAsmLoad(){
        //Set the appropriate register value before generating assembly
        if(mType == Type.Float){
            register = Registers.getFloatingPointReg();
        } else if (mType == Type.Double){
            register = Registers.getDoubleReg();
        } else{
            register = Registers.getTempReg();    //Set the output register
        }

        String code = ASMGen.loadVar(this, register);

        return  code == null ? "IDENTIFER ERROR: Loading data type asm code" : code;
    }

    @Override
    public String getResultRegister() {
        return register;
    }
}


