package parser;

import lexer.Lexer;
import lexer.Tag;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Justin Gagne and Zach Farrer
 * Professor Assiter
 * Compiler Design -- Summer 2016
 * Wentworth Institute of Technology
 *
 * Class to handle reading in the tokens of a boolean expression from the lexer in Natural
 * A boolean expression can follow the following format:
 *
 *      [boolean expression] ([and | or] [boolean expression])*
 *
 *      boolean expression --> boolean expression | (true | false) | comparison
 *              comparison --> (number | identifier) comparison operator
 *
 * Created by gagnej3 on 6/14/16.
 */
public class BooleanExpression {

    private static final String ERROR_DEFAULT = "Invalid boolean expression: ";
    private static String _expression;
    private static String _output;
    private static String _parse;
    private static boolean hasSyntaxError;


    /**
     * Evaluate boolean expressions when they are expected by the parser. This method evaluates the correctness
     * of nested expressions and joins
     * @throws IOException
     */
    public static boolean evaluateExpression() {
        if(!Lexer.getInstance().hasNext()){
            return false;
        }

        try{
            _expression = ReadGroup.readExpression(ReadType.BOOLEAN_EXPRESSION);   // Read a supposed grouped boolean expression to be evaluated

            _expression = Replace.setUniqueGroups(_expression);                    // Set unique values for inner groupings of parentheses

            _expression = Replace.simplifyPhrases(_expression);                    // Combine the compound phrases before evaluation

            _parse = Replace.replaceIdentifiers(_expression,                       // Set the String to be passed to the parser containing tags and variables
                    ReadGroup.getIdentifiers());

            ReadGroup.clearIdentifiers();                                          //Clear the array list

            _output = BooleanEvaluate.evaluateBooleanExpression(_expression);      // Get the simplified output string, either Tag.TRUE or Tag.FALSE if the boolean expression is valid

            BooleanEvaluate.resetGroups();

            int out = Integer.parseInt(_output);                                   // Ensure there is only a single tag left.

            if(out != Tag.TRUE && out != Tag.FALSE) {    //If the value was reduced to a single boolean tag, the _expression was successful.
                hasSyntaxError = true;
            }

        } catch (Exception e){
            hasSyntaxError = true;
            return false;
        }

        return true;
    }

    public static String getParseString(){
        return _parse;
    }

    public static void printOutput(){
        if(hasSyntaxError){
            printError();
            hasSyntaxError = false;
        }
        else{
            System.out.printf("%d. Input: %s\t Output Tag: %d\n", Lexer.getInstance().lineCount, _expression, Integer.parseInt(_output));
            //System.out.printf("%d. Parse String: %s\n", Lexer.getInstance().lineCount, _parse);
        }
    }

    private static void printError(){
        System.err.printf("%s %s near line: %d\n", ERROR_DEFAULT, Lexer.getInstance().getLine(), Lexer.getInstance().lineCount);
        //System.err.printf("%d. Parse String: %s\n", Lexer.getInstance().lineCount, _parse);
    }


}
