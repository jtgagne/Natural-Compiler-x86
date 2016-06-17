package parser;

import lexer.Lexer;
import java.io.IOException;

/**
 * Just a file to test the boolean expression class
 * Created by gagnej3 on 6/14/16.
 */
public class BooleanTest {

    public static void main(String[] args) throws IOException{

        Lexer lex = Lexer.getInstance();
        lex.openReader(args[0]);
        BooleanExpression booleanExpression = new BooleanExpression();

        //Evaluate the 7 boolean expressions from the src/test/boolean_expr_test.nat file
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();
        booleanExpression.evaluateExpression();


        lex.closeReader();
    }
}
