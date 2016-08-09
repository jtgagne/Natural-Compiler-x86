package code_generation;

/**
 * Created by gagnej3 on 8/8/16.
 */
public class Registers {
    static boolean[] functionCalls = {false, false};             //$v0 and $v1,  regs 2-3

    static boolean[] functionArgs = {false,false,false,false};   //$a0-$a3       regs 4-7

    static boolean[] temporary = {false,false,false,false,
                                  false,false,false,false,
                                  false, false};                 //$t0-$t9       regs 8-15, 24-25

    static boolean[] savedTemporary = {false,false,false,false,
                                       false,false,false,false}; //$s0-$s7        regs 16-23

    public static String getFunctionCallReg(){
        String regName = "$v";
        for(int i = 0; i < functionCalls.length; i++){
            if(!functionCalls[i]){
                return String.format("%s%d", regName, i);
            }
        }
        return "$v0";
    }

    public static String getFunctionArgsReg(){
        String regName = "$a";
        for(int i = 0; i < functionArgs.length; i++){
            if(!functionArgs[i]){
                return String.format("%s%d", regName, i);
            }
        }
        return "$a0";
    }

    public static String getTempReg(){
        String regName = "$t";
        for(int i = 0; i < temporary.length; i++){
            if(!temporary[i]){
                return String.format("%s%d", regName, i);
            }
        }
        return "$t0";
    }

    public static String getSavedTempReg(){
        String regName = "$s";
        for(int i = 0; i < savedTemporary.length; i++){
            if(!savedTemporary[i]){
                return String.format("%s%d", regName, i);
            }
        }
        return "$s0";
    }

    public static void clearAllFunctionCallRegs(){
        for(int i = 0; i< functionCalls.length; i++){
            functionCalls[i] = false;
        }
    }

    public static void clearAllFunctionArgsRegs(){
        for(int i = 0; i< functionArgs.length; i++){
            functionArgs[i] = false;
        }
    }

    public static void clearAllTempRegs(){
        for(int i = 0; i< temporary.length; i++){
            temporary[i] = false;
        }
    }

    public static void clearAllSavedTempRegs(){
        for(int i = 0; i< savedTemporary.length; i++){
            savedTemporary[i] = false;
        }
    }

    public static void clearAllRegs(){
        clearAllFunctionArgsRegs();
        clearAllFunctionCallRegs();
        clearAllTempRegs();
        clearAllSavedTempRegs();
    }

    public static void clearTempReg(int register){
        temporary[register] = false;
    }

}
