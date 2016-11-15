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
     * Branch if expression is false
     * @param register register containing a boolean 1 or 0
     * @return the formatted String
     */
    public static String genBranchTo(String register){
        Stmt s = Stmt.Enclosing;
        String label = s.getLabelAfter();   //Get the enclosing label of the current statement
        if (register.equals("0")){
            return String.format("\tbc1f\t %s, %s\n\n", register, label);
        }
        return String.format("\tbeqz\t %s, %s\n\n", register, label);
    }

    public static String genOrExpr(String register1, String register2){
        //mSavedRegister = RegisterManager.getSavedTempReg();
        return String.format("\tor\t %s, %s, %s\n", mSavedRegister, register1, register2);
    }

    public static String genNotExpr(String register1){
        //mSavedRegister = RegisterManager.getTempReg();
        //StringBuilder sb = new StringBuilder();
        return String.format("\txor\t %s, %s, %s\n", mSavedRegister, register1, register1);
    }

    public static String genAndExpr(String register1, String register2){
        //mSavedRegister = RegisterManager.getSavedTempReg();
        return String.format("\tand\t %s, %s, %s\n", mSavedRegister, register1, register2);
    }

    //TODO: make an assembly function for this instead
    public static String genBooleanPrint(String label1, String label2, String label3){
        StringBuilder sb = new StringBuilder();

        String L1 = String.format(
                "%s:\tli\t $v0, 4\n" +
                "\tla \t$a0, BOOL_TRUE_STR\n" +
                "\tsyscall\n" +
                "\tj\t %s\n\n", label1, label3);

        String L2 = String.format(
                "%s:\tli\t $v0, 4\n" +
                "\tla \t $a0, BOOL_FALSE_STR\n" +
                "\tsyscall\n" +
                "\tj\t %s\n\n", label2, label3);

        String L3 = String.format("%s:", label3);

        sb.append(L1);
        sb.append(L2);
        sb.append(L3);
        return sb.toString();
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
                //sb.append(String.format("\t\t %s, %s, %s\n\n", reg1, reg2, label));  //Goto next label if reg2 > reg1)
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

    public static void resetConstantCount(){
        CONSTANT_COUNT = 0;
    }

    public static String genConstantName(){
        return String.format("CONST%d", ++CONSTANT_COUNT);
    }

//    public static String genBooleanTrue(){
//        return "";
//        //return "BOOL_TRUE:\t.byte\t1\n";
//    }
//
//    public static String genBooleanFalse(){
//        return "BOOL_FALSE:\t.byte\t0\n";
//    }
//
//    public static String genBooleanTrueStr(){
//        return "BOOL_TRUE_STR:\t.asciiz\t\"true\"\n";
//    }
//
//    public static String genBooleanFalseStr(){
//        return "BOOL_FALSE_STR:\t.asciiz\t\"false\"\n";
//    }

    public static String getStoreType(Type type){
        switch (type.lexeme){
            case "int":
                return "sw";
            case "long":
                return "sw";
            case "float":
                return "s.s";
            case "double":
                return "s.d";
            case "char":
                return "sb";
            case "boolean":
                return "sb";
        }
        return null;
    }

    public static String getLoadType(Type type){
        switch (type.lexeme){
            case "int":
                return "MOV";
            case "long":
                return "lw";
            case "float":
                return "l.d";
            case "double":
                return "l.d";
            case "char":
                return "lb";
            case "boolean":
                return "lb";
        }
        return null;
    }

    /**
     * Generate the .data declaration for a variable. Some values such as float,
     * double, and char require an initial value stored in the data section
     * @param identifier the Id object
     * @param initialValue the initial value
     * @return data string declaration
     */
    public static String toData(Id identifier, String initialValue){
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
                return String.format("%s:\t.word\t%s\n", name, initialValue);
            case "float":
                return String.format("%s:\t.float\t%s\n", name, initialValue);
            case "double":
                return String.format("%s:\t.double\t%s\n", name, initialValue);
            case "char":
                return String.format("%s:\t.byte\t%s\n", name, initialValue);
            case "boolean":
                return String.format("%s:\t.byte\t%s\n", name, initialValue);
        }
        return null;
    }


    /**
     * AsmLoad an identifier to a register
     * @param identifier the identifier object
     * @param register the register to be loaded to
     * @return the assembly code
     */
    public static String loadVar(Id identifier, String register){
        Type type = identifier.getType();

        switch (type.lexeme){
            case "int":
                return String.format(
                        "\tMOV\t %s, %s\n", register, identifier.getName()); //AsmLoad int into temp register
            case "long":
                return String.format(
                        "\tlw\t %s, %s\n", register, identifier.getName()); //AsmLoad int into temp register
            case "float":
                return String.format(
                        "\tl.s\t %s, %s\n", register, identifier.getName()); //AsmLoad float into temp register
            case "double":
                return String.format(
                        "\tl.d\t %s, %s\n", register, identifier.getName()); //AsmLoad double into temp register
            case "char":
                return String.format(
                        "\tlb\t %s, %s\n", register, identifier.getName()); //AsmLoad char into temp register
            case "boolean":
                return String.format(
                        "\tlb\t %s, %s\n", register, identifier.getName()); //AsmLoad boolean into temp register
        }
        return null;
    }

    /**
     * Store a register value at the address of an identifier
     * @param identifier the identifier object
     * @param register the register to be loaded from
     * @return the assembly code
     */
    public static String storeVar(Id identifier, String register){
        Type type = identifier.getType();

        switch (type.lexeme){
            case "int":
                return String.format(
                        "\tMOV\t %s, %s\n\n", identifier.getName(), register); //AsmLoad int into temp register
            case "long":
                return String.format(
                        "\tsw\t %s, %s\n\n", register, identifier.getName()); //AsmLoad int into temp register
            case "float":
                return String.format(
                        "\ts.s\t %s, %s\n\n", register, identifier.getName()); //AsmLoad float into temp register
            case "double":
                return String.format(
                        "\ts.d\t %s, %s\n\n", register, identifier.getName()); //AsmLoad double into temp register
            case "char":
                return String.format(
                        "\tsb\t %s, %s\n\n", register, identifier.getName()); //AsmLoad char into temp register
            case "boolean":
                return String.format(
                        "\tsb\t %s, %s\n\n", register, identifier.getName());

        }
        return null;
    }

}
