package code_generation;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class will be used to generate all macros to be used by the natural compiler
 * Created by gagnej3 on 11/18/16.
 */
public class Macros {

    private static final String FILE_NAME = "naturalMacros.asm";
    private static PrintWriter _writer;

    /**
     * This main method will be run to regenerate the naturalMacros.asm file when needed
     * @param args
     */
    public static void main(String args[]){
        setFile();  // Open the file reader and the set the output file
        _writer.write(printBoolean());
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


    public static final String
            PRINT = "mPrint",       // Print a string
            PRINTLN = "mPrintln",   // Print a string on a new line
            PRINT_BOOL = "mPrintBoolean";

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
     * Works as a proc, need to figure out how to properly implement as a macro through.
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
                ";; Input of 1 --> True\n" +
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
        sb.append("\tCMP eax, 1\n");            // Check if eax is equal to 1
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
