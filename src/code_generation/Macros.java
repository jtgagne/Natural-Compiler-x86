package code_generation;

import symbols.Type;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class will be used to generate and update all macros to be used by the natural compiler
 * Created by gagnej3 on 11/18/16.
 */
public class Macros {

    private static final String FILE_NAME = "naturalMacros.asm";
    private static PrintWriter _writer;
    private static int macroLabel = 0;

    private static String getNewLabel(){
        return String.format("L%d", ++macroLabel);
    }

    public static final String
        PRINT = "mPrint",       // Print a string
        PRINTLN = "mPrintln",   // Print a string on a new line
        PRINT_BOOL = "mPrintBoolean",
        PRINT_FLOAT = "mPrintFloat",
        REL_LESS_THAN = "mLessThanToBool",
        REL_GREATER_THAN = "mGreaterThanToBool",
        REL_GREATER_THAN_EQ = "mGreaterThanEqualToBool",
        REL_LESS_THAN_EQ = "mLessThanEqualToBool",
        REL_EQUAL_TO = "mEqualToBool",
        REL_NOT_EQUAL_TO = "mNotEqualToBool"
    ;

    /**
     * This main method will be run to regenerate the naturalMacros.asm file when needed
     * @param args
     */
    public static void main(String args[]){
        setFile();  // Open the file reader and the set the output file
        _writer.write(printBoolean());
        _writer.write("\n\n");
        _writer.write(printFloat());
        _writer.write("\n\n");
        _writer.write(relationalToBoolean(REL_LESS_THAN, "JNL"));
        _writer.write("\n\n");
        _writer.write(relationalToBoolean(REL_GREATER_THAN, "JNG"));
        _writer.write("\n\n");
        _writer.write(relationalToBoolean(REL_GREATER_THAN_EQ, "JNGE"));
        _writer.write("\n\n");
        _writer.write(relationalToBoolean(REL_LESS_THAN_EQ, "JNLE"));
        _writer.write("\n\n");
        _writer.write(relationalToBoolean(REL_EQUAL_TO, "JNE"));
        _writer.write("\n\n");
        _writer.write(relationalToBoolean(REL_NOT_EQUAL_TO, "JE"));
        _writer.write("\n\n");
        _writer.close();
    }

    /**
     * Open the writer and set the file to be printed to
     */
    private static void setFile(){
        Path currentRelativePath = Paths.get("");                           // Get the path to the project directory
        String path = currentRelativePath.toAbsolutePath().toString();      // Convert the path to a string
        String outputAsmPath = path + "/src/output";                        // This is the folder the file will be printed to

        try{
            path = outputAsmPath + "/" + FILE_NAME;
            File file = new File(path);
            _writer = new PrintWriter(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private static final String
        COMMENT = ";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

    public static String macroPrint(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s MACRO text\n\n", PRINT));
        sb.append(".data\n\n");
        sb.append("\tstring BYTE text, 0\n\n");
        sb.append(".code\n\n");
        sb.append("\tPUSH edx\n");
        sb.append("\tMOV edx, OFFSET string\n");
        sb.append("\tCALL WriteString\n");
        sb.append("\tPOP edx\n\n");
        sb.append("ENDM\n");
        return sb.toString();
    }

    /**
     * Find the result of a boolean expression for less than. Return the value on the stack.
     * @return the string of the assembly code
     */
    public static String relationalToBoolean(String macroName, String compareType){
        String sb = "";
        Register register1 = Register.EAX;
        Register register2 = Register.EBX;
        String label1 = getNewLabel();
        String label2 = getNewLabel();

        sb += COMMENT + "\n";
        sb += ";; This macro makes a less than comparison and returns" +
                "\n;; a boolean value to the stack\n";
        sb += COMMENT + "\n";
        sb += String.format("%s MACRO\n\n", macroName); //var1, var2\n\n", macroName);
        sb += String.format("LOCAL %s\n", label1);
        sb += String.format("LOCAL %s\n", label2);
        sb += ".data\n\n";
        sb += ".code\n";
        sb += "\tPOP eax\n\tPOP ebx\n";
        sb += String.format("\tCMP %s, %s\n", register1, register2) +
                String.format("\t%s %s\n", compareType, label1) +
                String.format("\tMOV al, %s\n", AsmConstants.BOOLEAN_TRUE) +
                String.format("\tJMP %s\n", label2) +
                String.format("%s:\n", label1) +
                String.format("\tMOV al, %s\n", AsmConstants.BOOLEAN_FALSE) +
                String.format("%s:\n", label2) +
                "\tPUSH eax" +
                "\nENDM";

        return sb;
    }

    public static String printFloat(){
        StringBuilder sb = new StringBuilder();
        String printValue = "printValue";

        //Append the header comment
        sb.append(String.format("%s\n", COMMENT));
        sb.append(";; Print a value of a floating point variable\n" +
                  ";; Input: a real variable  \n" +
                  ";; Output: value to console\n");
        sb.append(String.format("%s\n", COMMENT));

        //Macro declaration
        sb.append(String.format("%s MACRO value:REQ\n\n", PRINT_FLOAT));
        sb.append(String.format("LOCAL %s\n", printValue));

        // Declare the local string variables to print either true or false.
        sb.append(".data\n");
        sb.append(String.format("\t%s REAL4 value\n\n", printValue));

        // Begin the code section
        sb.append(".code\n");
        sb.append(String.format("\tFLD %s\t; Load the variable into ST(0)\n", printValue));
        sb.append("\tCALL WriteFloat\n");
        sb.append(String.format("\tFSTP %s\t; Remove the value from FPU stack\n\n", printValue));

        sb.append("ENDM\n");

        return sb.toString();
    }

    /**
     * @return the string to print a boolean value
     */
    public static String printBoolean(){
        StringBuilder sb = new StringBuilder();
        String strTrue = "strTrue";
        String strFalse = "strFalse";

        final String
                LABEL_PRINT_FALSE = "PrintFalse",
                LABEL_END = "EndPrintFalse";

        //Append the header comment
        sb.append(String.format("%s\n", COMMENT));
        sb.append(";; Print the string value corresponding to Natural boolean values\n" +
                ";; Input of 0FFh --> True\n" +
                ";; Otherwise --> False\n");
        sb.append(String.format("%s\n", COMMENT));

        //Macro declaration
        sb.append(String.format("%s MACRO value:REQ\n\n", PRINT_BOOL));

        // Must declare the tags as local to allow the MACRO to be reusable
        sb.append(String.format("LOCAL %s\n", strTrue));
        sb.append(String.format("LOCAL %s\n", strFalse));
        sb.append(String.format("LOCAL %s\n", LABEL_PRINT_FALSE));
        sb.append(String.format("LOCAL %s\n\n", LABEL_END));

        // Declare the local string variables to print either true or false.
        sb.append(".data\n");
        sb.append(String.format("\t%s BYTE 'True', 0\n", strTrue));
        sb.append(String.format("\t%s BYTE 'False', 0\n\n", strFalse));

        // Begin the code section
        sb.append(".code\n");
        sb.append("\t;; Push EAX and EDX to the stack in case they are in use\n");
        sb.append("\tPUSH eax\n");
        sb.append("\tPUSH edx\n\n");

        sb.append("\tMOVZX eax, value\n");      // Load the parameter value into eax with sign 0 sign extension
        sb.append("\tCMP eax, 0FFh\n");            // Check if eax is equal to 1
        sb.append(String.format("\tJNE %s\n", LABEL_PRINT_FALSE));    // If not 1, print false.
        sb.append(String.format("\tMOV edx, OFFSET %s\n",strTrue));   // Load the string into EDX
        sb.append("\tCALL WriteString\n");                            // Print the String
        sb.append(String.format("\tJMP %s\n", LABEL_END));            // Jump to the end
        sb.append(String.format("\n%s:\n", LABEL_PRINT_FALSE));
        sb.append(String.format("\tMOV edx, OFFSET %s\n", strFalse));
        sb.append("\tCALL WriteString\n");
        sb.append(String.format("\n%s:\n", LABEL_END));
        sb.append("\tPOP edx\n");
        sb.append("\tPOP eax\n\n");
        sb.append("ENDM\n");
        return sb.toString();
    }

}
