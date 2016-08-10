package code_generation;

/**
 * Created by gagnej3 on 8/8/16.
 */
public class Registers {
    private static boolean[] functionCalls = {false, false};             //$v0 and $v1,  regs 2-3

    private static boolean[] functionArgs = {false,false,false,false};   //$a0-$a3       regs 4-7

    private static boolean[] temporary = {false,false,false,false,
                                  false,false,false,false,
                                  false, false};                 //$t0-$t9       regs 8-15, 24-25

    private static boolean[] savedTemporary = {false,false,false,false,
                                       false,false,false,false}; //$s0-$s7        regs 16-23

    private static boolean[] floatingPoint = {false,false,false,false,
            false,false,false,false,false,false,false,false,false,false};

    public static String currentTemp;

    public static String getFunctionCallReg(){
        for(int i = 0; i < functionCalls.length; i++){
            if(!functionCalls[i]){
                functionCalls[i] = true;
                return String.format("$v%d", i);
            }
        }
        return "$v0";
    }

    public static String getFunctionArgsReg(){

        for(int i = 0; i < functionArgs.length; i++){
            if(!functionArgs[i]){
                functionArgs[i] = true;
                return String.format("$a%d", i);
            }
        }
        return "$a0";
    }

    public static String getTempReg(){
        for(int i = 0; i < temporary.length; i++){
            if(!temporary[i]){
                temporary[i] = true;
                return String.format("$t%d", i);
            }
        }
        return "$t0";
    }

    public static String getSavedTempReg(){

        for(int i = 0; i < savedTemporary.length; i++){
            if(!savedTemporary[i]){
                savedTemporary[i] = true;
                return String.format("$s%d", i);
            }
        }
        return "$s0";
    }

    /**
     * Get the next available floating point register
     * @return the register name
     */
    public static String getFloatingPointReg(){
        for(int i = 0; i < floatingPoint.length; i++){
            if(!floatingPoint[i]){
                floatingPoint[i] = true;
                return String.format("$f%d", i);
            }
        }
        return "$f0";
    }

    /**
     * Get the next available double register
     * @return the register name
     */
    public static String getDoubleReg(){
        for(int i = 0; i < floatingPoint.length; i++){
            if( i%2 != 0) continue;
            if(!floatingPoint[i]){
                floatingPoint[i] = true;
                floatingPoint[(i + 1)] = true;
                return String.format("$f%d", i);
            }
        }
        return "$f0";
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

    public static void clearFloatingPoint(){
        for(int i = 0; i< floatingPoint.length; i++){
            floatingPoint[i] = false;
        }
    }

    public static void clearAllRegs(){
        clearAllFunctionArgsRegs();
        clearAllFunctionCallRegs();
        clearAllTempRegs();
        clearAllSavedTempRegs();
        clearFloatingPoint();
    }

    public static void clearTempReg(int register){
        temporary[register] = false;
    }

}
