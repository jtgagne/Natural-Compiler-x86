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

    public static String genLabel(){
        return String.format("L%d", ++LABEL_COUNT);
    }

    public static void resetLabelCount(){
        LABEL_COUNT = 0;
    }

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

    public static String genLogical(String reg1, String reg2, Token op){

        Stmt s = Stmt.Enclosing;
        String label = s.getLabelAfter();

        switch (op.tag){

            case Tag.LESS:
                return String.format("\tbgt\t %s, %s, %s\n\n", reg1, reg2, label);  //Goto next label if reg2 > reg1

            //True: reg1 > reg2, False: reg1 <= beg2
            case Tag.GREATER:
                return String.format("\tble\t %s, %s, %s\n\n", reg1, reg2, label);

            //True: reg1 >= reg2, False: reg1 < beg2, branch if less than
            case Tag.GE:
                return String.format("\tblt\t %s, %s, %s\n\n", reg1, reg2, label);

        }
        return null;
    }

    public static void resetConstantCount(){
        CONSTANT_COUNT = 0;
    }

    public static String genConstantName(){
        return String.format("CONST%d", ++CONSTANT_COUNT);
    }

    public static String genBooleanTrue(){
        return "BOOL_TRUE:\t.byte\t1\n";
    }

    public static String genBooleanFalse(){
        return "BOOL_FALSE:\t.byte\t0\n";
    }

    public static String genBooleanTrueStr(){
        return "BOOL_TRUE_STR:\t.asciiz\t\"true\"\n";
    }

    public static String genBooleanFalseStr(){
        return "BOOL_FALSE_STR:\t.asciiz\t\"false\"\n";
    }

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
                return "lw";
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
                return String.format("%s:\t.word\t%s\n", name, initialValue);
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
     * Load an identifier to a register
     * @param identifier the identifier object
     * @param register the register to be loaded to
     * @return the assembly code
     */
    public static String loadVar(Id identifier, String register){
        Type type = identifier.getType();

        switch (type.lexeme){
            case "int":
                return String.format(
                        "\tlw\t %s, %s\n", register, identifier.getName()); //Load int into temp register
            case "long":
                return String.format(
                        "\tlw\t %s, %s\n", register, identifier.getName()); //Load int into temp register
            case "float":
                return String.format(
                        "\tl.s\t %s, %s\n", register, identifier.getName()); //Load float into temp register
            case "double":
                return String.format(
                        "\tl.d\t %s, %s\n", register, identifier.getName()); //Load double into temp register
            case "char":
                return String.format(
                        "\tlb\t %s, %s\n", register, identifier.getName()); //Load char into temp register
            case "boolean":
                return String.format(
                        "\tlb\t %s, %s\n", register, identifier.getName()); //Load boolean into temp register
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
                        "\tsw\t %s, %s\n\n", register, identifier.getName()); //Load int into temp register
            case "long":
                return String.format(
                        "\tsw\t %s, %s\n\n", register, identifier.getName()); //Load int into temp register
            case "float":
                return String.format(
                        "\ts.s\t %s, %s\n\n", register, identifier.getName()); //Load float into temp register
            case "double":
                return String.format(
                        "\ts.d\t %s, %s\n\n", register, identifier.getName()); //Load double into temp register
            case "char":
                return String.format(
                        "\tsb\t %s, %s\n\n", register, identifier.getName()); //Load char into temp register
            case "boolean":
                return String.format(
                        "\tsb\t %s, %s\n\n", register, identifier.getName());

        }
        return null;
    }

    /**
     * Generate assembly code for the arithmetic / unary operation
     * @param operation the operation token
     * @param saveReg register to save the operation to
     * @param reg1 first register to be used
     * @param reg2 second register to be used
     * @return assembly code for the operation
     */
    public static String genMath(Op operation, String saveReg, String reg1, String reg2){
        Token token = operation.getToken();
        Type type = operation.getType();

        switch (token.tag){

            //Generate assembly for addition
            case '+':
                if(type == Type.Float){
                    return String.format("\tadd.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                else if(type == Type.Double){
                    return String.format("\tadd.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                return String.format("\tadd\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);

            //Generate assembly for subtraction
            case '-':
                if(type == Type.Float){
                    return String.format("\tsub.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                else if(type == Type.Double){
                    return String.format("\tsub.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                return String.format("\tsub\t %s, %s, %s\t\t#subtract the two registers\n\n", saveReg, reg1, reg2);

            //Generate assembly for addition
            case '*':
                if(type == Type.Float){
                    return String.format("\tmul.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                else if(type == Type.Double){
                    return String.format("\tmul.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                return String.format("\tmul\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);

            //Generate assembly for subtraction
            case '/':
                if(type == Type.Float){
                    return String.format("\tdiv.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                else if(type == Type.Double){
                    return String.format("\tdiv.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
                }
                return String.format("\tdiv\t %s, %s, %s\t\t#subtract the two registers\n\n", saveReg, reg1, reg2);
        }

        return null;
    }
}
