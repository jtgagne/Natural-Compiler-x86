package inter;
import code_generation.ASMGen;
import code_generation.AssemblyFile;
import code_generation.Registers;
import lexer.Print;

/**
 * Node used to generate print statements
 * Created by gagnej3 on 8/4/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class PrintNode extends Stmt {

    private Print mPrintVal;
    private Id mIdentifier;
    private int msgNumber = -1;

    public PrintNode(){
        mIdentifier = null;
        mPrintVal = null;
    }

    /**
     * PrintNode to print a String
     * @param print the print token obtained from the lexer
     */
    public PrintNode(Print print) {
        mPrintVal = print;
        msgNumber = newPrintLabel();       //Set the print label
    }

    /**
     * Print node to print an identifier
     * @param identifier the identifier value to be displayed
     */
    public PrintNode(Id identifier){
        mIdentifier = identifier;
    }

    public void setPrintToken(Print print){
        mPrintVal = print;
        msgNumber = newPrintLabel();       //Set the print label
    }

    public void setIdentifier(Id identifier){
        mIdentifier = identifier;
    }

    @Override
    public void gen(int b, int a) {
        super.gen(b, a);
        AssemblyFile.addStrings(this.toAsmData());
        AssemblyFile.addToMain(this.toAsmMain());
    }

    /**
     * Logs the variables of the print value in the data section of an assembly file.
     * @return the string variable to be pointed to.
     */
    @Override
    public String toAsmData(){
        if(mPrintVal != null){
            return String.format("msg%d:\t.asciiz \"%s\"\n", msgNumber, mPrintVal.value);
        }
        return null;
    }

    /**
     * Returns the assembly code that will call the print value.
     * @return a string containing the ppropriate assembly code to be added to the main
     */
    @Override
    public String toAsmMain(){

        if(mIdentifier != null && mPrintVal != null){
            return String.format(
                "\tli\t $v0,4\t\t#Load the system call to print a string\n" +
                "\tla\t $a0, msg%d\t\t#Load the String to be printed\n" +
                "\tsyscall\n\n" +
                "%s\n", msgNumber, genPrintAsm());
        }
        else if(mIdentifier != null){
            return genPrintAsm();
        }

        return String.format(
            "\tli\t $v0,4\t\t#Load the system call to print a string\n" +
            "\tla\t $a0, msg%d\t\t#Load the String to be printed\n" +
            "\tsyscall\n\n",msgNumber);
    }

    /**
     * Generate the assembly assignment statement for printing a variable of a certain data type
     * @return the assembly code for the variable assignment.
     */
    private String genPrintAsm(){
        StringBuilder sb = new StringBuilder();

        switch (mIdentifier.type.lexeme){
            case "int":
                 sb.append(String.format(
                        "\tli\t $v0, 1\t\t#Load system call for to print integer\n" +
                        "\tld\t $a0, %s\t\t#Load the integer into a0\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName()));  //Store the value in the appropriate var name
            case "long":
                return String.format(
                        "\tli\t $v0, 1\t\t#Load system call to print long\n" +
                        "\tld\t $a0, %s\t\t#Load the long into a0\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName());  //Store the value in the appropriate var name
            case "float":
                return String.format(
                        "\tli\t $v0, 2\t\t#Load system call to print float\n" +
                        "\tl.s\t $f12, %s\t\t#Load the float from f12 to %s\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName(), mIdentifier.getName());
            case "double":
                return String.format(
                        "\tli\t $v0, 3 \t\t#Load system call to print double\n" +
                        "\tl.d\t$f12, %s \t\t#Load the float from f12 to %s\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName(), mIdentifier.getName());
            case "char":
                return String.format(
                        "\tli\t $v0, 11\t\t#System call for printing a char\n" +
                        "\tlb\t $a0, %s\t\t#Load the char to a0\n" +
                        "\tsyscall\n\n", mIdentifier.getName());
            case "boolean":
                String label1 = ASMGen.genLabel();
                String label2 = ASMGen.genLabel();
                String label3 = ASMGen.genLabel();
                String register = Registers.getTempReg();
                sb.append(String.format(
                        "\tlb\t %s, %s\t\t#Load the value to be compared\n" +
                        "\tbeqz\t %s, %s\t\t#Goto %s if greater than 0\n\n"
                         , register, mIdentifier.getName(), register, label2, label2));
                sb.append(ASMGen.genBooleanPrint(label1, label2, label3));
                return sb.toString();
        }

        return "TYPE ERROR: PrintNode\n";
    }

}
