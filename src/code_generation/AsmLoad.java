package code_generation;

import inter.Constant;
import inter.Id;
import symbols.Type;

/**
 * Class to generate code to load a variable into a register.
 * Created by gagnej3 on 11/15/16.
 */
public class AsmLoad {

    /**
     * Loading from memory should be in the following format:
     * MOV <reg>, <identifier>
     * @param constant the constant to be loaded
     * @return string
     */
    public static String loadConstant(Constant constant){
        Type type = constant.getType();

        return "";
    }

    /**
     * AsmLoad an identifier to a register
     * @param identifier the identifier object
     * @param register the register to be loaded to
     * @return the assembly code
     */
    public static String loadVariable(Id identifier, String register){
        Type type = identifier.getType();

        switch (type.lexeme){
            case "int":
                return String.format(
                        "\tMOV\t %s, %s\n", register, identifier.getName()); //AsmLoad int into temp register
            case "long":
                return String.format(
                        "\tMOV\t %s, %s\n", register, identifier.getName()); //AsmLoad int into temp register
            case "char":
                return String.format(
                        "\tMOVSX\t %s, %s\n", register, identifier.getName()); //AsmLoad char into temp register

            case "boolean":
                return String.format(
                        "\tMOV\t %s, %s\n", register, identifier.getName()); //AsmLoad boolean into temp register

            // Float will have to be loaded into the FPU registers
            case "float":
                return String.format(
                        "\tFLD\t%s\n", identifier.getName()); //AsmLoad float into temp register
            case "double":
                return String.format(
                        "\tFLD\t %s\n", identifier.getName()); //AsmLoad double into temp register
        }
        return null;
    }

    public static String loadVariableForArith(Id identifier, String register){
        // All arithmetic registers are cast to 16 bit registers but boolean values need to be extended.
        if(identifier.getType() == Type.Bool) {
            return String.format("\tMOVZX %s, %s\n", register, identifier.getName());
        }
        return loadVariable(identifier, register);
    }
}
