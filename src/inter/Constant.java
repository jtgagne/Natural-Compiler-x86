package inter;
import code_generation.ASMGen;
import code_generation.AsmConstants;
import code_generation.RegisterManager;
import lexer.*;
import symbols.*;

/**
 * Create a node containing a numerical constant value
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Constant extends Expr {

    public static final Constant True  = new Constant(Word.True,  Type.Bool);
    public static final Constant False = new Constant(Word.False, Type.Bool);
    private String mConstantId;     //The generated identifier for floating point and chars
    private static final String ERROR = "ERROR in Constant:";

    //Member variables inherited from Expr:
    //protected Token mToken;
    //protected Type mType;
    //protected String register;

    public Constant(Token tok, Type p){
        super(tok, p);
        mType = p;
        mToken = tok;
    }

    public Constant(int i) {
        super(new Num(i), Type.Int);
    }

    /**
     * This node is of type Constant.
     * @return true
     */
    @Override
    public boolean isConstant() {
        return true;
    }

   @Override
   public String toAsmConstants() {
       return "";
   }

   public void setType(Type type) {
      mType = type;
   }

   @Override
   public String toString() {
      return mToken.toString() + " " + mType.toString();
   }

   @Override
   public void jumping(int t, int f) {
//      if ( this == True && t != 0 ){
//          emit("L" + t);
//      }
//      else if ( this == False && f != 0){
//          emit("L" + f);
//      }
   }

   @Override
   public Expr gen() {
       return super.gen();
   }


   /**
    * Generate the Assembly code to be added to the main
    * @return assembly code
     */
   @Override
   public String toAsmMain() {
      mToken.setType(mType);
      if(mToken.tag == Tag.TRUE){
         mToken.setConstantId(AsmConstants.BOOLEAN_TRUE);
      }else if(mToken.tag == Tag.FALSE){
         mToken.setConstantId(AsmConstants.BOOLEAN_FALSE);
      }
      mConstantId = mToken.getConstantId();
      return load(mConstantId);
   }

    @Override
    public String load() {
        return super.load();
    }

    /**
    * Assembly code to load a floating point into a register
    * @param identifier name of the variable
    * @return assembly code to load number into register
     */
   private String load(String identifier){
        Token token = mToken;
        Num num;
        Char natChar;
        Real real;
        StringBuilder sb = new StringBuilder();

        switch (mType.lexeme){
            case "int":
                mRegister = RegisterManager.getGeneralPurpose16().toString();
                num = (Num) token;
                return String.format("\tMOV\t %s, %d\t\t; Load an immediate value into the register\n", mRegister, num.value); //AsmLoad int into temp register
            case "long":
                mRegister = RegisterManager.getGeneralPurpose32().toString();
                num = (Num) token;
                return String.format("\tMOV\t %s, %s\t\t; Load an immediate value into the register\n", mRegister, num.value); //AsmLoad int into temp register
            case "char":
                natChar = (Char) token;
                mRegister = RegisterManager.getGeneralPurpose8().toString();
                sb.append(String.format("\tMOV\t %s, %s\t\t\n", mRegister, String.format("\'%c\'", natChar.value)));
                return sb.toString();

            case "boolean":
                mRegister = RegisterManager.getGeneralPurpose8().toString();
                return String.format("\tMOV\t %s, %s\t\t; Load a boolean value\n", mRegister, identifier);

            case "float":
                return "";

            case "double":
                return "";
      }
      return ERROR + " generating assembly.\n";
   }

    @Override
    public String getName() {
        return mConstantId;
    }

    @Override
    public String getResultRegister() {
        return mRegister;
    }
}
