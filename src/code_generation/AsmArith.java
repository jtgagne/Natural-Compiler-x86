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
     * @param reg1 first register to be used
     * @param reg2 second register to be used
     * @return assembly code for the operation
     */
    public static String genMath(Op operation, String saveReg, String reg1, String reg2){
        Token token = operation.getToken();
        Type type = operation.getType();

        switch (token.tag){

            // Generate assembly for addition
            case '+':
                return genAddition(type, reg1, reg2);

            // Generate assembly for subtraction
            case '-':
                return genSubtraction(type, saveReg, reg1, reg2);

        }
        return null;
    }

    /**
     * Generate the x86 code to add values in 2 registers
     * @param type the type of the variable
     * @param reg1 the register containing the first value
     * @param reg2 the register containing the second value
     * @return the x86 code to be assembled
     */
    private static String genAddition(Type type, String reg1, String reg2){
        if(type == Type.Float || type == Type.Double){
            RegisterManager.freeFPURegister();  // Free the two registers used, the end value will be in ST0
            RegisterManager.freeFPURegister();
            return "\tFADD\t\t; Adds the values in ST(0) to ST(1), result in ST(0)\n";
        }
        return String.format("\tADD\t %s, %s\t\t ; add the two registers\n", reg1, reg2);
    }

    private static String genSubtraction(Type type, String saveReg, String reg1, String reg2){
        // floating point arithmetic must be done on the FPU.
        if(type == Type.Float || type == Type.Double){
            return "\tFSUB\t\t; Calculate ST(1) - ST(0)\n";
        }
        return String.format("\tSUB\t %s, %s\t\t; subtract the two registers\n", reg1, reg2);
    }

}
