package code_generation;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Class to define the register's names and values. creates initial hashtables for each 8bit, 16bit, and 32 bit
 * x86 registers.
 * Created by gagnej3 on 11/15/16.
 */
public class Register {

    public enum Size{REG32, REG16, REG8, FLOATING}
    public final String name;
    public final Size size;
    public Register[] children;
    private Register lower;

    public Register(String name, Size size){
        this.name = name;
        this.size = size;
        children = new Register[] {};
        lower = null;
    }

    public Register(String name, Size size, Register c1){
        this.name = name;
        this.size = size;
        children = new Register[] {c1};
        lower = null;
    }

    public Register(String name, Size size, Register c1, Register c2){
        this.name = name;
        this.size = size;
        children = new Register[] {c1, c2};
        lower = null;
    }

    public Register(String name, Size size, Register c1, Register c2, Register c3){
        this.name = name;
        this.size = size;
        children = new Register[] {c1, c2, c3};
        lower = c3;
    }

    /**
     * Get the lower eight bit register from a GPR
     * @return
     */
    public Register getLower(){
        return lower;
    }

    public static final Register
        AH = new Register("ah", Size.REG8),
        AL = new Register("al", Size.REG8),
        BH = new Register("bh", Size.REG8),
        BL = new Register("bl", Size.REG8),
        CH = new Register("ch", Size.REG8),
        CL = new Register("cl", Size.REG8),
        DH = new Register("dh", Size.REG8),
        DL = new Register("dl", Size.REG8);

    public static final Register
        AX = new Register("ax", Size.REG16, AH, AL),
        BX = new Register("bx", Size.REG16, BH, BL),
        CX = new Register("cx", Size.REG16, CH, CL),
        DX = new Register("dx", Size.REG16, DH, DL),
        DI = new Register("di", Size.REG16),
        SI = new Register("si", Size.REG16),
        BP = new Register("bp", Size.REG16),
        SP = new Register("sp", Size.REG16);

    public static final Register
        EAX = new Register("eax", Size.REG32, AX, AH, AL),
        EBX = new Register("ebx", Size.REG32, BX, BH, BL),
        ECX = new Register("ecx", Size.REG32, CX, CH, CL),
        EDX = new Register("edx", Size.REG32, DX, DH, DL),
        EDI = new Register("edi", Size.REG32, DI),
        ESI = new Register("esi", Size.REG32, SI),
        EBP = new Register("ebp", Size.REG32, BP),
        ESP = new Register("esp", Size.REG32, SP);

    /**
     * These are the floating point registers used by the FPU. there are 8 of them
     */
    public static final Register
        ST0 = new Register("ST(0)", Size.FLOATING),
        ST1 = new Register("ST(1)", Size.FLOATING),
        ST2 = new Register("ST(2)", Size.FLOATING),
        ST3 = new Register("ST(3)", Size.FLOATING),
        ST4 = new Register("ST(4)", Size.FLOATING),
        ST5 = new Register("ST(5)", Size.FLOATING),
        ST6 = new Register("ST(6)", Size.FLOATING),
        ST7 = new Register("ST(7)", Size.FLOATING);


    /**
     * Get the size value of a given register
     * @return the size
     */
    public Size getSize(){
        return size;
    }

    /**
     * @return the arraylist of registers included in a certain register
     */
    public Register[] getChildren(){
        return children;
    }

    public static void reserveReg8(Hashtable<Register, Boolean> table){
        table.put(AH, false);
        table.put(AL, false);
        table.put(BH, false);
        table.put(BL, false);
        table.put(CH, false);
        table.put(CL, false);
        table.put(DH, false);
        table.put(DL, false);
    }
    public static void reserveReg16(Hashtable<Register, Boolean> table){
        table.put(AX, false);
        table.put(BX, false);
        table.put(CX, false);
        table.put(DX, false);
        table.put(SI, false);
        table.put(DI, false);
        table.put(BP, false);
        table.put(SP, false);
    }
    public static void reserveReg32(Hashtable<Register, Boolean> table){
        table.put(EAX, false);
        table.put(EBX, false);
        table.put(ECX, false);
        table.put(EDX, false);
        table.put(EDI, false);
        table.put(ESI, false);
        table.put(EBP, false);
        table.put(ESP, false);
    }

    /**
     * Adds register name to register values
     */
    public static void reserveNames(Hashtable<String, Register> table){
        table.put(EAX.toString(), EAX);
        table.put(EBX.toString(), EBX);
        table.put(ECX.toString(), ECX);
        table.put(EDX.toString(), EDX);
        table.put(EDI.toString(), EDI);
        table.put(ESI.toString(), ESI);
        table.put(EBP.toString(), EBP);
        table.put(ESP.toString(), ESP);

        table.put(AX.toString(), AX);
        table.put(BX.toString(), BX);
        table.put(CX.toString(), CX);
        table.put(DX.toString(), DX);
        table.put(SI.toString(), SI);
        table.put(DI.toString(), DI);
        table.put(BP.toString(), BP);
        table.put(SP.toString(), SP);

        table.put(AH.toString(), AH);
        table.put(AL.toString(), AL);
        table.put(BH.toString(), BH);
        table.put(BL.toString(), BL);
        table.put(CH.toString(), CH);
        table.put(CL.toString(), CL);
        table.put(DH.toString(), DH);
        table.put(DL.toString(), DL);

        table.put(ST0.toString(), ST0);
        table.put(ST1.toString(), ST1);
        table.put(ST2.toString(), ST2);
        table.put(ST3.toString(), ST3);
        table.put(ST4.toString(), ST4);
        table.put(ST5.toString(), ST5);
        table.put(ST6.toString(), ST6);
        table.put(ST7.toString(), ST7);

    }

    @Override
    public String toString() {
        return this.name;
    }
}
