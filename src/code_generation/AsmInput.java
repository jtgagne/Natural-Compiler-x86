package code_generation;

import inter.Id;
import inter.PrintNode;

/**
 * Created by gagnej3 on 11/22/16.
 */
public class AsmInput {
    private static final String
        READ_INT = "ReadInt",
        READ_CHAR = "ReadChar";

    public static String getInputString(PrintNode print, Id identifier){
        StringBuilder sb = new StringBuilder();
        sb.append(AsmPrint.genPrintString(print)); // get the asm code to print a string
        sb.append(getInputByType(identifier));
        return sb.toString();
    }

    private static String getInputByType(Id identifier){

        switch (identifier.getTypeStr()){
            case "int":
                return genWholeNumberInput(identifier, Register.AX.name);
            case "long":
                return genWholeNumberInput(identifier, Register.EAX.name);
            case "char":
                return genCharInput(identifier, Register.AL.name);
        }

        return "";
    }

    /**
     * TODO: the charinput and the whole number input can be reduced to a single function
     * ReadChar stores the result in AL
     * @param identifier
     * @return
     */
    private static String genCharInput(Id identifier, String resultRegister){
        StringBuilder sb = new StringBuilder();
        String pop = "";
        AssemblyFile.addProto(String.format("%s PROTO\n", READ_CHAR));
        if(RegisterManager.isInUse(Register.EAX)){
            sb.append("\tPUSH eax\n");
            pop = "\tPOP eax\n";
        }
        Register eax = RegisterManager.getRegister(Register.EAX);   // set eax in use
        sb.append(String.format("\tCALL %s\n", READ_CHAR));
        sb.append(String.format("\tMOV %s, %s\n", identifier.getName(), resultRegister));
        if(pop.equals("")){
            RegisterManager.freeRegister(eax);
            sb.append("\n");
        }else{
            sb.append(pop);
        }
        sb.append(AsmPrint.genNewLine());
        return sb.toString();
    }

    /**
     * The only difference between int and long here will be getting the result from eax or ax
     * @param identifier the variable the result should be stored in
     * @param resultRegister the register the result if initially stored in
     * @return the string to make the assembly happen
     */
    private static String genWholeNumberInput(Id identifier, String resultRegister){
        StringBuilder sb = new StringBuilder();
        String pop = "";
        AssemblyFile.addProto(String.format("%s PROTO\n", READ_INT));
        if(RegisterManager.isInUse(Register.EAX)){
            sb.append("\tPUSH eax\n");
            pop = "\tPOP eax\n";
        }
        Register eax = RegisterManager.getRegister(Register.EAX);   // set eax in use
        sb.append(String.format("\tCALL %s\n", READ_INT));
        sb.append(String.format("\tMOV %s, %s\n", identifier.getName(), resultRegister));
        if(pop.equals("")){
            RegisterManager.freeRegister(eax);
            sb.append("\n");
        }else{
            sb.append(pop);
        }
        return sb.toString();
    }
}
