package parser;

import lexer.EndOfFile;
import lexer.Lexer;
import java.io.IOException;

/**
 * Just a file to test the boolean expression class
 * Created by gagnej3 on 6/14/16.
 */
public class BooleanTest implements EndOfFile{

    private static boolean isEOF = false;

    public static void main(String[] args) throws IOException{

        Lexer lex = Lexer.getInstance();
        lex.openReader(args[0]);
        BooleanExpression booleanExpression = new BooleanExpression();

        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();

        lex.closeReader();

    }

    @Override
    public void isEndOfFile(boolean b) {
        isEOF = b;
    }
}
