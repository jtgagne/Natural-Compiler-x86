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

    public Print printVal;
    private int msgNumber;

    public PrintNode(Print print) {
        printVal = print;
        msgNumber = newPrintLabel();       //Set the print label
    }

    @Override
    public void gen(int b, int a) {
        super.gen(b, a);
        emit(this.toAsmMain());
        AssemblyFile.addData(this.toAsmData());
        AssemblyFile.addToMain(this.toAsmMain());
    }

    /**
     * Logs the variables of the print value in the data section of an assembly file.
     * @return the string variable to be pointed to.
     */
    private String toAsmData(){
        return String.format("msg%d:\t.asciiz \"%s\\n\"\n", msgNumber, printVal.value);
    }

    /**
     * Returns the assembly code that will call the print value.
     * @return
     */
    private String toAsmMain(){
        return String.format(
                "\tli $v0,4\n" +
                "\tla $a0, msg%d\n" +
                "\tsyscall\n\n", msgNumber);
    }

}
