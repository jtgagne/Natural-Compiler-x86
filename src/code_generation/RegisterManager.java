package code_generation;

import inter.Set;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Editing for x86 generation
 * Created by gagnej3 on 8/8/16.
 */
public class RegisterManager {

    public static RegisterManager _instance;
    public static Hashtable<Register, Boolean> _reg8;
    public static Hashtable<Register, Boolean> _reg16;
    public static Hashtable<Register, Boolean> _reg32;
    public static Hashtable<String, Register> _regNames;
    public static ArrayList<Register> _active;
    public static java.util.Set<Register> reg8Keys;
    public static java.util.Set<Register> reg16Keys;
    public static java.util.Set<Register> reg32Keys;
    public static Register[] reg8GP;
    public static Register[] reg16GP;
    public static Register[] reg32GP;


    /**
     * Default constructor to instantiate the hash table containing all the registers
     */
    public RegisterManager(){
        _reg8 = new Hashtable<>();
        _reg16 = new Hashtable<>();
        _reg32 = new Hashtable<>();
        _regNames = new Hashtable<>();
        _active = new ArrayList<>();

        //TODO: i think CL is not general purpose.
        reg8GP = new Register[] {Register.AH, Register.AL, Register.BL, Register.BH, Register.CH, Register.CL,
                                    Register.DH, Register.DL};
        reg16GP = new Register[] {Register.AX, Register.BX, Register.CX, Register.DX};
        reg32GP = new Register[] {Register.EAX, Register.EBX, Register.ECX, Register.EDX};
        Register.reserveReg8(_reg8);
        Register.reserveReg16(_reg16);
        Register.reserveReg32(_reg32);
        Register.reserveNames(_regNames);
        reg8Keys = _reg8.keySet();
        reg16Keys = _reg16.keySet();
        reg32Keys = _reg32.keySet();
        _instance = this;
    }

    public static Register getGeneralPurpose8(){
        for(Register register: reg8GP){
            if(!isInUse(register)){
                return getRegister(register);
            }
        }
        return getRegister(reg8GP[0]);
    }
    /**
     * TODO: handle registers that need to be pushed
     * @return
     */
    public static Register getGeneralPurpose16(){
        for(Register register: reg16GP){
            if(!isInUse(register)){
                return getRegister(register);
            }
        }
        return getRegister(reg16GP[0]);
    }

    public static Register getGeneralPurpose32(){
        for(Register register: reg32GP){
            if(!isInUse(register)){
                return getRegister(register);
            }
        }
        return getRegister(reg16GP[0]);
    }

    /**
     * TODO: this might need to return a string
     * Reserve a specific register
     * @return the desired register
     */
    public static Register getRegister(Register register){
        setUsage(register, true);
        _active.add(register);
        return register;
    }

    public static void freeRegister(Register register){
        setUsage(register, false);
    }

    public static void freeRegister(String register){
        freeRegister(_regNames.get(register));
    }

    public static void clearAllRegisters(){
        for(Register register: _active){
            setUsage(register, false);
        }
    }

    /**
     * Update the 'usage' value of a given register.
     * @param register the register that is going to be in user
     */
    public static void setUsage(Register register, boolean inUse){

        if(register.getSize() == Register.Size.REG32){
            setUsage32(register, inUse);
        } else if (register.getSize() == Register.Size.REG16){
            setUsage16(register, inUse);
        } else if(register.getSize() == Register.Size.REG8){
            setUsage8(register, inUse);
        }

        for(int i = 0; i < register.children.length; i++){
            if (register.children[i].getSize() == Register.Size.REG16){
                setUsage16(register.children[i], inUse);
            } else if (register.children[i].getSize() == Register.Size.REG8){
                setUsage8(register.children[i], inUse);
            }
        }
    }

    private static void setUsage8(Register register, boolean inUse){
        _reg8.replace(register, inUse);
    }

    private static void setUsage16(Register register, boolean inUse){
        _reg16.replace(register, inUse);
    }

    private static void setUsage32(Register register, boolean inUse){
        _reg32.replace(register, inUse);
    }

    public static boolean isInUse(Register register){
        if(register.size == Register.Size.REG8){
            return _reg8.get(register);
        }
        else if(register.size == Register.Size.REG16){
            return _reg16.get(register);
        }
        else{
            return _reg32.get(register);
        }
    }


//    /**
//     * @return the next available 32 bit register
//     */
//    public static String getReg32(){
//        for(int i = 0; i < reg32.length; i++){
//            if(!reg32[i]){
//
//            }
//        }
//        return String.format("");
//    }
//
//    /**
//     * Update the available registers if a 32 bit register is used.
//     * @param register the register being used.
//     */
//    private static void setUsage32(String register){
//        switch (register){
//
//        }
//    }
//
//    public static String getFunctionCallReg(){
//        for(int i = 0; i < functionCalls.length; i++){
//            if(!functionCalls[i]){
//                functionCalls[i] = true;
//                return String.format("$v%d", i);
//            }
//        }
//        return "$v0";
//    }
//
//    public static String getFunctionArgsReg(){
//
//        for(int i = 0; i < functionArgs.length; i++){
//            if(!functionArgs[i]){
//                functionArgs[i] = true;
//                return String.format("$a%d", i);
//            }
//        }
//        return "$a0";
//    }
//
//    public static String getTempReg(){
//        for(int i = 0; i < temporary.length; i++){
//            if(!temporary[i]){
//                temporary[i] = true;
//                return String.format("$t%d", i);
//            }
//        }
//        return "$t0";
//    }
//
//    public static String getSavedTempReg(){
//
//        for(int i = 0; i < savedTemporary.length; i++){
//            if(!savedTemporary[i]){
//                savedTemporary[i] = true;
//                return String.format("$s%d", i);
//            }
//        }
//        return "$s0";
//    }
//
//    /**
//     * Get the next available floating point register
//     * @return the register name
//     */
//    public static String getFloatingPointReg(){
//        for(int i = 2; i < floatingPoint.length; i++){
//            if(!floatingPoint[i]){
//                floatingPoint[i] = true;
//                return String.format("$f%d", i);
//            }
//        }
//        return "$f2";
//    }
//
//    /**
//     * Get the next available double register
//     * @return the register name
//     */
//    public static String getDoubleReg(){
//        for(int i = 2; i < floatingPoint.length; i++){
//            if( i%2 != 0) continue;
//            if(!floatingPoint[i]){
//                floatingPoint[i] = true;
//                floatingPoint[(i + 1)] = true;
//                return String.format("$f%d", i);
//            }
//        }
//        return "$f2";
//    }
//
//    public static void clearAllFunctionCallRegs(){
//        for(int i = 0; i< functionCalls.length; i++){
//            functionCalls[i] = false;
//        }
//    }
//
//    public static void clearAllFunctionArgsRegs(){
//        for(int i = 0; i< functionArgs.length; i++){
//            functionArgs[i] = false;
//        }
//    }
//
//    public static void clearAllTempRegs(){
//        for(int i = 0; i< temporary.length; i++){
//            temporary[i] = false;
//        }
//    }
//
//    public static void clearAllSavedTempRegs(){
//        for(int i = 0; i< savedTemporary.length; i++){
//            savedTemporary[i] = false;
//        }
//    }
//
//    public static void clearFloatingPoint(){
//        for(int i = 0; i< floatingPoint.length; i++){
//            floatingPoint[i] = false;
//        }
//    }
//
//    public static void clearAllRegs(){
//        clearAllFunctionArgsRegs();
//        clearAllFunctionCallRegs();
//        clearAllTempRegs();
//        clearAllSavedTempRegs();
//        clearFloatingPoint();
//    }
//
//    public static void clearTempReg(int register){
//        temporary[register] = false;
//    }

}
