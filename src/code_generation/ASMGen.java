package code_generation;

import inter.Id;
import inter.Op;
import inter.Stmt;
import lexer.Tag;
import lexer.Token;
import symbols.Env;
import symbols.Type;

/**
 * Static use to get various assembly strings
 * Created by gagnej3 on 8/9/16.
 */
public class ASMGen {

    private static int CONSTANT_COUNT = 0;
    private static int LABEL_COUNT = 0;
    private static String mSavedRegister;

    public static String getSavedRegister(){
        return mSavedRegister;
    }

    public static String genLabel(){
        return String.format("L%d", ++LABEL_COUNT);
    }

    public static void resetLabelCount(){
        LABEL_COUNT = 0;
    }


    /**
     * Return the appropriate comparison operation in assembly code
     * @param reg1 first register
     * @param reg2 second register
     * @param op the relational operator
     * @param type the type of the comparison
     * @return the generated, formatted assembly string
     */
    public static String genRelationalComparison(String reg1, String reg2, Token op, Type type){
        if(type.isFloatingPoint()){
            return genFloatingComparison(reg1, reg2, op, type);
        }else{
            return genIntegerComparison(reg1, reg2, op);
        }
    }

    /**
     * Generate the assembly code for a relational comparison of whole number values
     * @param reg1 first register
     * @param reg2 second register
     * @param op comparison operator
     * @return the formatted assembly code
     */
    private static String genIntegerComparison(String reg1, String reg2, Token op){

        StringBuilder sb = new StringBuilder();
        //mSavedRegister = RegisterManager.getSavedTempReg();

        switch (op.tag){
            case Tag.LESS:
                sb.append(String.format("\tslt\t %s, %s, %s\n", mSavedRegister, reg1, reg2));  //Goto next label if reg2 > reg1)
                return sb.toString();

            //True: reg1 <= reg2, False: reg1 > reg2, branch if less than
            case Tag.LE:
                sb.append(String.format("\tsle\t %s, %s, %s\n", mSavedRegister, reg1, reg2)); //Goto next label if reg1 > reg2
                return sb.toString();

            //True: reg1 > reg2, False: reg1 <= reg2
            case Tag.GREATER:
                sb.append(String.format("\tsgt\t %s, %s, %s\n", mSavedRegister, reg1, reg2)); //Goto next label if reg1 <= reg2
                return sb.toString();

            //True: reg1 >= reg2, False: reg1 < reg2, branch if less than
            case Tag.GE:
                sb.append(String.format("\tsge\t %s, %s, %s\n", mSavedRegister, reg1, reg2)); //Goto next label if reg1 < reg2
                return sb.toString();

            //True: reg1 == reg2, False: reg1 != reg2, branch if less than
            case Tag.EQ:
                sb.append(String.format("\tseq\t %s, %s, %s\n", mSavedRegister, reg1, reg2)); //Goto next label if reg1 != reg2
                return sb.toString();

            //True: reg1 <= reg2, False: reg1 > reg2, branch if less than
            case Tag.NE:
                sb.append(String.format("\tsne\t %s, %s, %s\n", mSavedRegister, reg1, reg2));  //Goto next label if reg1 == reg2
                return sb.toString();
        }
        return null;
    }

    /**
     * Floating Point comparison in mips only supports the following:
     * c.eq.d   --> compare equal to double
     * c.eq.s   --> compare equal to single
     * c.le.d   --> compare less than or equal to double
     * c.le.s   --> compare less than or equal to single
     * c.lt.d   --> compare less than double
     * c.lt.s   --> compare less than single
     * @param op operator token
     * @param reg1 first register
     * @param reg2 second register
     * @return formatted string for comparison operation
     */
    private static String genFloatingComparison(String reg1, String reg2, Token op, Type type){

        Stmt s = Stmt.Enclosing;
        String label = s.getLabelAfter();   //Get the enclosing label of the current statement
        //mSavedRegister = RegisterManager.getTempReg();
        StringBuilder sb = new StringBuilder();

        switch (op.tag){
            //True: reg1 < reg2 Branch on False: reg1 >= reg2 --> reg2 <= reg1
            case Tag.LESS:
                //Goto next label if reg2 <= reg1
                if(type == Type.Double){ sb.append(String.format("\tc.lt.d\t %s, %s\n", reg1, reg2)); break; }
                else { sb.append(String.format("\tc.lt.s\t %s, %s\n", reg1, reg2)); }
                break;

            //True: reg1 <= reg2 Branch on False: reg1 > reg2 --> reg2 < reg1
            case Tag.LE:
                if(type == Type.Double){ sb.append(String.format("\tc.le.d\t %s, %s\n", reg1, reg2)); break; }
                sb.append(String.format("\tc.le.s\t %s, %s\n", reg1, reg2));
                break;

            //True: reg1 > reg2 --> reg2 < reg1
            case Tag.GREATER:
                if(type == Type.Double){ sb.append(String.format("\tc.lt.d\t %s, %s\n", reg2, reg1)); break; }
                sb.append(String.format("\tc.lt.s\t %s, %s\n", reg2, reg1));
                break;

            //True: reg1 >= reg2 --> reg2 < reg1
            case Tag.GE:
                if(type == Type.Double){
                    sb.append(String.format("\tc.lt.d\t %s, %s\n", reg2, reg1)); //Goto next label if reg1 > reg2
                    break;
                }
                sb.append(String.format("\tc.lt.s\t %s, %s\n", reg2, reg1));
                break;

            //True: reg1 == reg2, False: reg1 != reg2. Branch on inequality
            case Tag.EQ:
                if(type == Type.Double){ sb.append(String.format("\tc.eq.d\t %s, %s\n", reg1, reg2)); break; }
                sb.append(String.format("\tc.eq.s\t %s, %s\n", reg1, reg2));
                break;

            //TODO: Update when Unary is added
            //True: reg1 != reg2, False: reg1 == reg2, Branch on equality
            case Tag.NE:
                if(type == Type.Double){
                    sb.append(String.format("\tc.eq.d\t %s, %s\n", reg1, reg2)); //Goto next label if reg1 > reg2
                    break;
                }
                sb.append(String.format("\tc.eq.s\t %s, %s\n", reg1, reg2));
                break;
        }

        mSavedRegister = "0";
        return sb.toString();
    }

    /**
     * Generate the .data declaration for a variable. Some values such as float,
     * double, and char require an initial value stored in the data section
     * @param identifier the Id object
     * @param initialValue the initial value
     * @return data string declaration
     */
    public static String genDeclaration(Id identifier, String initialValue){
        Type type = identifier.getType();
        String name = identifier.getName();
        Token token = identifier.getToken();

        //Check if the value has already been added to the data section
        if(Env.declaredVars.get(token) != null){
            return null;
        }

        Env.declaredVars.put(token, identifier);

        switch (type.lexeme){
            case "int":
                return String.format("%s\tSWORD\t%s\n", name, initialValue);
            case "long":
                return String.format("%s\tSDWORD\t%s\n", name, initialValue);
            case "char":
                return String.format("%s\tBYTE\t%s\n", name, initialValue);
            case "boolean":
                return String.format("%s\tBYTE\t%s\n", name, initialValue);
            case "float":
                return String.format("%s\tREAL4\t%s\n", name, initialValue);
            case "double":
                return String.format("%s\tREAL8\t%s\n", name, initialValue);
        }
        return null;
    }



    /**
     * TODO: reduce like statements!!!!
     * Store a register value at the address of an identifier
     * @param identifier the identifier object
     * @param register the register to be loaded from
     * @return the assembly code
     */
    public static String storeVar(Id identifier, String register){
        Type type = identifier.getType();
        String result = "";

        switch (type.lexeme){
            case "int":
                result = String.format("\tMOV\t %s, %s\n\n", identifier.getName(), register);
                break;
            case "long":
                result = String.format(
                        "\tMOV\t %s, %s\n\n", identifier.getName(), register);
                break;

            case "char":
                result = String.format(
                        "\tMOV\t %s, %s\n\n", identifier.getName(), register);
                break;

            case "boolean":
                result = String.format("\tMOV\t %s, %s\n\n", identifier.getName(), register);
                break;

            case "float":
                result = String.format("\tFSTP\t %s\n\n", identifier.getName());
                break;

            case "double":
                result = String.format("\tFSTP\t %s\n\n", identifier.getName());
                break;
        }

        RegisterManager.freeRegister(register);
        return result;
    }

}
