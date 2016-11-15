package code_generation;

import inter.Op;
import lexer.Token;
import symbols.Type;

/**
 * Class for generating x86 assembly code to do arithmetic
 * Created by gagnej3 on 11/15/16.
 */
public class AsmArith {

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
                return genAddition(type, saveReg, reg1, reg2);

            //Generate assembly for subtraction
            case '-':
                return genSubtraction(type, saveReg, reg1, reg2);

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

    private static String genAddition(Type type, String saveReg, String reg1, String reg2){
        if(type == Type.Float){
            return String.format("\tadd.s\t %s, %s, %s\t\t; add the two registers\n", saveReg, reg1, reg2);
        }
        else if(type == Type.Double){
            return String.format("\tadd.d\t %s, %s, %s\t\t#add the two registers\n", saveReg, reg1, reg2);
        }
        return String.format("\tADD\t %s, %s\t\t ; add the two registers\n", reg1, reg2);
    }

    private static String genSubtraction(Type type, String saveReg, String reg1, String reg2){
        if(type == Type.Float){
            return String.format("\tsub.s\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
        }
        else if(type == Type.Double){
            return String.format("\tsub.d\t %s, %s, %s\t\t#add the two registers\n\n", saveReg, reg1, reg2);
        }
        return String.format("\tSUB\t %s, %s\t\t; subtract the two registers\n", reg1, reg2);
    }
}
