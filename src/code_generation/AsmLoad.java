package code_generation;

import inter.Constant;
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
}
