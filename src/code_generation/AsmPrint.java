package code_generation;

import inter.Id;
import inter.PrintNode;
import symbols.Type;

/**
 * Natural allows for stuff in print statements to be printed as well as variable values
 * Created by gagnej3 on 11/15/16.
 */
public class AsmPrint {


    /**
     * Method for generating asm to print a string
     * @return the string to be appended to the asm file.
     */
    public static String genPrintString(PrintNode print){
        StringBuilder sb = new StringBuilder();
        String pop = "";
        Register edx = Register.EDX;
        if(RegisterManager.isInUse(edx)){
            sb.append(String.format("\tPUSH\t%s\n", edx));
            pop = String.format("\tPOP\t%s\n", edx);        // set the string to retrieve edx from the stack
        } else {
           edx = RegisterManager.getRegister(Register.EDX);
        }
        sb.append(String.format("\tMOV %s, OFFSET %s\n", edx, print.getMessageLabel()));
        sb.append("\tCALL WriteString\n");

        if(!pop.equals("")){
            sb.append(pop);
        } else {
            RegisterManager.freeRegister(edx);
        }
        AssemblyFile.addProto("WriteString PROTO\n");
        return sb.toString();
    }

    /**
     * Generate x86 to print a new line on the console.
     * @return the x86 code
     */
    public static String genNewLine(){
        AssemblyFile.addProto("Crlf PROTO\n");
        return "\tCALL Crlf\n";
    }

    public static String genPrintIdentifier(PrintNode printNode){
        StringBuilder sb = new StringBuilder();
        Id id = printNode.getIdentifier();
        switch (id.getTypeStr()){
            // Ints are stored as a SWORD
            case "int":
                return genIntPrint(id);
            default:
                return "";
        }
    }

    private static String genIntPrint(Id id){
        StringBuilder sb = new StringBuilder();
        Register eax = Register.EAX;
        String pop = "";
        if(RegisterManager.isInUse(eax)){
            sb.append(String.format("\tPUSH %s\n", eax));
            pop = String.format("\tPOP %s", eax);
        }
        sb.append(String.format(
            "\tMOVSX %s, %s\n", eax, id.getName()
        ));
        sb.append("\tCALL WriteInt\n");
        sb.append(pop);
        AssemblyFile.addProto("WriteInt PROTO\n");
        return sb.toString();
    }
}
