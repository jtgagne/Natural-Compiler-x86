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
        return "\tCALL Crlf\n\n";
    }

    public static String genPrintIdentifier(PrintNode printNode){
        Id id = printNode.getIdentifier();
        switch (id.getTypeStr()){
            // Ints are stored as a SWORD
            case "int":
                return genIntPrint(id);
            case "long":
                return genIntPrint(id);
            case "char":
                return genPrintChar(id);
            case "boolean":
                return printBool(id);
            case "float":
                return printFloat(id);
            case "double":
                return printFloat(id);
            default:
                return "";
        }
    }

    private static String printFloat(Id id){
        StringBuilder sb = new StringBuilder();
        AssemblyFile.addProto("WriteFloat\tPROTO\n");
        sb.append(String.format("\t%s <%s>\n", Macros.PRINT_FLOAT, id.getName()));
        return sb.toString();
    }


    /**
     * Generate the code to call the mPrintBoolean macro function
     * @param id the identifier of the boolean variable
     * @return the string containing the assembly code
     */
    private static String printBool(Id id){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\t%s <%s>\n", Macros.PRINT_BOOL, id.getName()));
        return sb.toString();
    }

    /**
     * The char to be printed must be loaded into AL.
     * @param id the identifier of the variable to be printed
     * @return the x86 code.
     */
    private static String genPrintChar(Id id){
        StringBuilder sb = new StringBuilder();
        Register al = Register.AL;
        String pop = "";

        //Push to stack, set the value for pop
        if(RegisterManager.isInUse(al)){
            sb.append(String.format("\tPUSH %s\n", Register.EAX.toString()));   // must push all of eax
            pop = String.format("\tPOP %s\n", Register.EAX.toString());           // pop the value back into eax.
        }
        sb.append(String.format("\tMOV %s, %s\n", al, id.getName()));
        sb.append("\tCALL WriteChar\n");
        sb.append(pop);
        AssemblyFile.addProto("WriteChar PROTO\n");
        return sb.toString();
    }

    /**
     * Print a whole number. Will work for Natural types long and int.
     * @param id the identifier of a variable
     * @return the assembly code to print the number
     */
    private static String genIntPrint(Id id){
        StringBuilder sb = new StringBuilder();
        Register eax = Register.EAX;
        String pop = "";

        //Push to stack, set the value for pop
        if(RegisterManager.isInUse(eax)){
            sb.append(String.format("\tPUSH %s\n", eax));
            pop = String.format("\tPOP %s\n", eax);
        }

        // Sign extend since ints are stored as an SWORD
        if(id.getType() == Type.Int){
            sb.append(String.format("\tMOVSX %s, %s\n", eax, id.getName()));
        }else{
            sb.append(String.format("\tMOV %s, %s\n", eax, id.getName()));
        }
        sb.append("\tCALL WriteInt\n");
        sb.append(pop);
        AssemblyFile.addProto("WriteInt PROTO\n");
        return sb.toString();
    }
}
