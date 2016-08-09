package inter;
import code_generation.AssemblyFile;
import lexer.Print;


/**
 * Node to contain content to be printed to the console
 * Created by gagnej3 on 8/4/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class PrintNode extends Stmt {

    private Print printVal;
    private Id identifier;
    private boolean isPrintingId;
    private int msgNumber;

    public PrintNode(){
        msgNumber = newPrintLabel();       //Set the print label
    }

    /**
     * PrintNode to print a String
     * @param print the print token obtained from the lexer
     */
    public PrintNode(Print print) {
        printVal = print;
        msgNumber = newPrintLabel();       //Set the print label
        isPrintingId = false;
    }

    /**
     * Print node to print an identifier
     * @param identifier the identifier value to be displayed
     */
    public PrintNode(Id identifier){
        this.identifier = identifier;
        msgNumber = newPrintLabel();       //Set the print label
        isPrintingId = true;
    }

    public void setPrintToken(Print print){
        this.printVal = print;
        isPrintingId = false;
    }

    public void setPrintIdentifier(Id identifier){
        this.identifier = identifier;
        isPrintingId = true;
    }

    @Override
    public void gen(int b, int a) {
        super.gen(b, a);
        //emit(this.toAsmMain());
        AssemblyFile.addData(this.toAsmData());
        AssemblyFile.addToMain(this.toAsmMain());
    }

    /**
     * Logs the variables of the print value in the data section of an assembly file.
     * @return the string variable to be pointed to.
     */
    @Override
    public String toAsmData(){
        if(!isPrintingId){
            return String.format("msg%d:\t.asciiz \"%s\"\n", msgNumber, printVal.value);
        }
        return null;
    }

    /**
     * Returns the assembly code that will call the print value.
     * @return a string containing the ppropriate assembly code to be added to the main
     */
    @Override
    public String toAsmMain(){
        if(isPrintingId){
            return getSystemCall();
        }

        return String.format(
                "\tli\t $v0,4\t\t#Load the system call to print a string\n" +
                "\tla\t $a0, msg%d\t\t#Load the String to be printed\n" +
                "\tsyscall\n\n", msgNumber);
    }

    /**
     * Generate the assembly assignment statement for printing a variable of a certain data type
     * @return the assembly code for the variable assignment.
     */
    private String getSystemCall(){
        switch (identifier.type.lexeme){
            case "int":
                return String.format(
                        "\tli\t $v0, 1\t\t#Load system call for to print integer\n" +
                        "\tld\t $a0, %s\t\t#Load the integer into a0\n" +
                        "\tsyscall\n\n",
                        identifier.getName());  //Store the value in the appropriate var name
            case "long":
                return String.format(
                        "\tli\t $v0, 1\t\t#Load system call to print long\n" +
                        "\tld\t $a0, %s\t\t#Load the long into a0\n" +
                        "\tsyscall\n\n",
                        identifier.getName());  //Store the value in the appropriate var name
            case "float":
                return String.format(
                        "\tli\t $v0, 2\t\t#Load system call to print float\n" +
                        "\tl.d\t$f12, %s\t\t#Load the float from f12 to %s\n" +
                        "\tsyscall\n\n",
                        identifier.getName(), identifier.getName());
            case "double":
                return String.format(
                        "\tli\t $v0, 3 \t\t#Load system call to print double\n" +
                        "\tl.d\t$f12, %s \t\t#Load the float from f12 to %s\n" +
                        "\tsyscall\n\n",
                        identifier.getName(), identifier.getName());
            case "char":
                return String.format(
                        "\tli\t $v0, 11\t\t#System call for printing a char\n" +
                        "\tld\t $a0, %s\t\t#Load the char to a0\n" +
                        "\tsyscall\n\n",
                        identifier.getName());
        }

        return "TYPE ERROR: InputNode\n";
    }

}
