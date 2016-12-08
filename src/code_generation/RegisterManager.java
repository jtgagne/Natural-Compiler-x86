package code_generation;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Editing for x86 generation
 * Created by gagnej3 on 8/8/16.
 */
public class RegisterManager {

    public static RegisterManager _instance;
    public static Hashtable<Register, Boolean> _reg8;
    public static Hashtable<Register, Boolean> _reg16;
    public static Hashtable<Register, Boolean> _reg32;
    private static Stack<Register> mFPUAvailabe;        // Stack of available registers
    private static Stack<Register> mFPUInUse;           // Stack of in use registers
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

        initFPUStack(); // Initialize the FPU stack of registers
        _instance = this;
    }

    /**
     * Get the next available 8 bit register
     * @return a register.
     */
    public static Register getGeneralPurpose8(){
        for(Register register: reg8GP){
            if(!isInUse(register)){
                _active.add(register);
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
                _active.add(register);
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

    public static void freeAllRegisters(){
        for(Register register: reg32GP){
            Register[] children = register.children;
            setUsage(register, false);
        }
    }

    public static void freeRegister(String register){
        try{
            freeRegister(_regNames.get(register));
        } catch (Exception e){
            System.out.printf("Null pointer exception\n");
        }
    }

    public static void clearAllRegisters(){
        try{
            for(Register register: _active){
                setUsage(register, false);
            }
        }catch (Exception e){
            System.out.printf("No registers in use");
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
            Register reg = register.children[i];
            if (reg.getSize() == Register.Size.REG16){
                setUsage16(register.children[i], inUse);
            } else if (reg.getSize() == Register.Size.REG8){
                setUsage8(register.children[i], inUse);
            }
        }
    }

    private static void setUsage8(Register register, boolean inUse){
        _reg8.replace(register, inUse);
        if(inUse){
            _active.add(register);
        }else{
            _active.remove(register);
        }
    }

    private static void setUsage16(Register register, boolean inUse){
        _reg16.replace(register, inUse);
        if(inUse){
            _active.add(register);
        }else{
            _active.remove(register);
        }
    }

    private static void setUsage32(Register register, boolean inUse){
        _reg32.replace(register, inUse);
        if(inUse){
            _active.add(register);
        }else{
            _active.remove(register);
        }
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

    /***************************************************************************
    ** Below here is all the code for managing the Floating point registers
    ***************************************************************************/

    /**
     * Initialize the stack to track where values are to be stored when on the FPU stack
     */
    private static void initFPUStack(){
        mFPUAvailabe = new Stack<>();
        mFPUInUse = new Stack<>();

        mFPUAvailabe.push(Register.ST7);
        mFPUAvailabe.push(Register.ST6);
        mFPUAvailabe.push(Register.ST5);
        mFPUAvailabe.push(Register.ST4);
        mFPUAvailabe.push(Register.ST3);
        mFPUAvailabe.push(Register.ST2);
        mFPUAvailabe.push(Register.ST1);
        mFPUAvailabe.push(Register.ST0);        // ST0 will be the first one used.
    }

    /**
     * Get the next available floating point register
     * @return
     */
    public static Register getFPURegister(){
        Register fpRegister = mFPUAvailabe.pop();   // Get the next available register
        if(fpRegister == null){
            fpRegister = Register.ST0;
        }
        mFPUInUse.push(fpRegister);         // Set this register to be in use
        return fpRegister;
    }

    /**
     * Free a register and add it to the available stack
     */
    public static void freeFPURegister(){
        if(mFPUInUse.size() != 0){
            Register register = mFPUInUse.pop();
            mFPUAvailabe.push(register);
        }
    }
}
