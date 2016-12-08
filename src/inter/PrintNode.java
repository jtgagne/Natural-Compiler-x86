package inter;
import code_generation.ASMGen;
import code_generation.AsmPrint;
import code_generation.AssemblyFile;
import lexer.Print;
import symbols.Type;

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
    private String mMessage;

    public PrintNode(){
        mIdentifier = null;
        mPrintVal = null;
    }

    @Override
    public boolean isPrintNode() {
        return true;
    }

    /**
     * PrintNode to print a String
     * @param print the print token obtained from the lexer
     */
    public PrintNode(Print print) {
        mPrintVal = print;
        mMessage = genMessage();       //Set the print label
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
        mMessage = genMessage();       //Set the print label
    }

    public void setIdentifier(Id identifier){
        mIdentifier = identifier;
    }

    @Override
    public void gen(int b, int a) {
        emit("\n");
        super.gen(b, a);
        //emit(genLabel(a) + ":");
        AssemblyFile.addStrings(this.toAsmData());
        AssemblyFile.addToMain(this.toAsmMain());
    }

    @Override
    public String toAsmConstants() {
        return super.toAsmConstants();
    }

    /**
     * Logs the variables of the print value in the data section of an assembly file.
     * @return the string variable to be pointed to.
     */
    @Override
    public String toAsmData(){
        if(mPrintVal != null){
            return String.format("%s\t BYTE \'%s\', 0 \n", mMessage, mPrintVal.value);
        }
        return null;
    }

    /**
     * Returns the assembly code that will call the print value.
     * @return a string containing the appropriate assembly code to be added to the main
     */
    @Override
    public String toAsmMain(){

        // print a string and then and identifier:
        if(mIdentifier != null && mPrintVal != null){
            return String.format("%s %s %s", AsmPrint.genPrintString(this), AsmPrint.genPrintIdentifier(this),
                    AsmPrint.genNewLine());
        }
        else if(mIdentifier != null){
            return genPrintAsm();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(AsmPrint.genPrintString(this));
        sb.append(AsmPrint.genNewLine());
        return sb.toString();
    }

    /**
     * TODO: it might be possible to delete this method
     * Generate the assembly assignment statement for printing a variable of a certain data type
     * @return the assembly code for the variable assignment.
     */
    private String genPrintAsm(){
        StringBuilder sb = new StringBuilder();

        switch (mIdentifier.getTypeStr()){
            //case "int":
            case "long":
                return String.format(
                        "\tli\t $v0, 1\t\t#AsmLoad system call to print long\n" +
                        "\tld\t $a0, %s\t\t#AsmLoad the long into a0\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName());  //Store the value in the appropriate var name
            case "float":
                return String.format(
                        "\tli\t $v0, 2\t\t#AsmLoad system call to print float\n" +
                        "\tl.s\t $f12, %s\t\t#AsmLoad the float from f12 to %s\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName(), mIdentifier.getName());
            case "double":
                return String.format(
                        "\tli\t $v0, 3 \t\t#AsmLoad system call to print double\n" +
                        "\tl.d\t$f12, %s \t\t#AsmLoad the float from f12 to %s\n" +
                        "\tsyscall\n\n",
                        mIdentifier.getName(), mIdentifier.getName());
            case "char":
                return String.format(
                        "\tli\t $v0, 11\t\t#System call for printing a char\n" +
                        "\tlb\t $a0, %s\t\t#AsmLoad the char to a0\n" +
                        "\tsyscall\n\n", mIdentifier.getName());
            case "boolean":
                String label1 = genLabel();
                String label2 = genLabel();
                String label3 = genLabel();
                String register = "";//RegisterManager.getTempReg();
                sb.append(String.format(
                        "\tlb\t %s, %s\t\t#AsmLoad the value to be compared\n" +
                        "\tbeqz\t %s, %s\t\t#Goto %s if greater than 0\n\n"
                         , register, mIdentifier.getName(), register, label2, label2));
                //sb.append(ASMGen.genBooleanPrint(label1, label2, label3));
                return sb.toString();
        }

        return AsmPrint.genPrintIdentifier(this);


        //return "TYPE ERROR: PrintNode\n";
    }

    public String getMessageLabel(){
        return mMessage;
    }

    public Id getIdentifier(){
        return mIdentifier;
    }

}
