package code_generation;

import symbols.Type;

/**
 * Static use to get various useful strings
 * Created by gagnej3 on 8/9/16.
 */
public class ASMGen {

    public static String getStoreType(Type type, String error){
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
                return "la";
        }
        return error;
    }

    public static String getLoadType(Type type, String error){
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
                return "ld";
        }
        return error;
    }

    public static String loadVar(Type type, String register, String identifier, String error){
        switch (type.lexeme){
            case "int":
                return String.format(
                        "\tlw\t %s, %s", register, identifier); //Load int into temp register
            case "long":
                return String.format(
                        "\tlw\t %s, %s", register, identifier); //Load int into temp register
            case "float":
                return String.format(
                        "\tl.d\t %s, %s", register, identifier); //Load float into temp register
            case "double":
                return String.format(
                        "\tl.d\t %s, %s", register, identifier); //Load double into temp register
            case "char":
                return String.format(
                        "\tld\t %s, %s", register, identifier); //Load char into temp register
            case "boolean":
                return String.format(
                        "\tld\t %s, %s", register, identifier); //Load boolean into temp register
        }
        return error;
    }
}
