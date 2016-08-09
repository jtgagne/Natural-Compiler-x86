package inter;

import code_generation.AssemblyFile;
import lexer.Print;
import symbols.Type;

/**
 * Class used to create an InputNode to prompt user for data
 * Created by gagnej3 on 8/8/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class InputNode extends Stmt {
    private Print mPrint;
    private PrintNode mPrompt;
    private Id mIdentifier;
    private Type mType;

    public InputNode(){

    }

    /**
     * Input node takes a prompt value (Print-Token) and a data type.
     * @param prompt the Print-Token
     * @param identifier the identifier the value is to be stored in
     */
    public InputNode(Print prompt, Id identifier){
        mPrint = prompt;
        mIdentifier = identifier;
        mType = mIdentifier.type;
        mPrompt = new PrintNode(mPrint);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void gen(int b, int a) {
        super.gen(b, a);
        emit(this.toAsmMain());
        AssemblyFile.addData(this.toAsmData());
        AssemblyFile.addToMain(this.toAsmMain());
    }

    /**
     * Generate the assembly code to store the message as a pointer
     * @return the assembly code
     */
    @Override
    public String toAsmData() {
        return mPrompt.toAsmData();
    }

    /**
     * Generate the assembly code to be added to the main
     * @return the assembly code to complete the action
     */
    @Override
    public String toAsmMain() {
        return String.format("%s" +                                 //Print the input prompt
                "\t%s\t\t#System call to read in %s\n" +            //Get system call code to Read value
                "\tsyscall\n\n" +                                   //Run syscall to get the input
                "\t%s\t\t#Store the input in variable: '%s'\n\n",   //Store the value in a variable
                mPrompt.toAsmMain(),
                getSysCode(),
                mType.lexeme,
                getAssignment(),
                mIdentifier.getName());
    }

    /**
     * Generates the input ASM line based on the data type to be stored in
     * @return appropriate assembly code
     */
    private String getSysCode(){
        switch (mType.lexeme){
            case "int":
                return String.format("li\t $v0, 5");  //System call for reading int
            case "long":
                return String.format("li\t $v0, 5");  //System call for reading int
            case "float":
                return String.format("li\t $v0, 6");  //System call for reading float
            case "double":
                return String.format("li\t $v0, 7");  //System call for reading double
            case "char":
                return String.format("li\t $v0, 12"); //System call for reading char
        }
        return "TYPE ERROR: InputNode\n";
    }

    /**
     * Generate the assembly assignment statement
     * @return the assembly code for the variable assignment.
     */
    private String getAssignment(){
        switch (mType.lexeme){
            case "int":
                return String.format("sw\t $v0, %s", mIdentifier.getName());  //Store the value in the appropriate var name
            case "long":
                return String.format("sw\t $v0, %s", mIdentifier.getName());  //Store the value in the appropriate var name
            case "float":
                return String.format("s.d\t $f0, %s", mIdentifier.getName());  //Store the value in the appropriate var name
            case "double":
                return String.format("s.d\t $f0, %s", mIdentifier.getName());  //Store the value in the appropriate var name
            case "char":
                return String.format("sb\t $v0, %s", mIdentifier.getName()); //System call for reading char
        }

        return "TYPE ERROR: InputNode\n";
    }
}
