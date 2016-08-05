package inter;
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

    public PrintNode(Print print) {
        printVal = print;
    }

    @Override
    public void gen(int b, int a) {
        super.gen(b, a);
        emit(printVal.toString());
    }

}
