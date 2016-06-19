package parser;

import lexer.Lexer;
import java.io.IOException;

/**
 * Just a file to test the boolean expression class and the For class
 * Created by gagnej3 on 6/14/16.
 */
public class Test {

    public static void main(String[] args) throws IOException{

        Lexer lex = Lexer.getInstance();
        lex.openReader(args[0]);
        BooleanExpression booleanExpression = new BooleanExpression();

        //Evaluate the 15 boolean expressions from the src/test/boolean_expr_test.nat file
        for(int i = 0; i < 15; i++){
            BooleanExpression.evaluateExpression();
        }

        lex.closeReader();

        lex.openReader(args[1]);

        int count = 1;
        //The last value will throw an exception
        while(lex.hasNext()){
            For.evaluateSyntax();
            System.out.printf("%d. Valid Syntax\n", count);
            count++;
        }

        lex.closeReader();

    }
}
