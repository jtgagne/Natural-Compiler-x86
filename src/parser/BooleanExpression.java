package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import java.io.IOException;

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
    private static boolean hasSyntaxError;

    /**
     * Required public constructor.
     */
    BooleanExpression(){

    }


    /**
     * Evaluate boolean expressions when they are expected by the parser.
     * @throws IOException
     */
    public static void evaluateExpression() {
        if(!Lexer.getInstance().hasNext()){
            return;
        }

        try{
            _expression = ReadGroup.readExpression(ReadType.BOOLEAN_EXPRESSION);   //Read a supposed grouped boolean expression to be evaluated

            _expression = setUniqueGroups(_expression); //Set unique values for inner groupings of parentheses

            _output = BooleanEvaluate.evaluateBooleanExpression(_expression);
            BooleanEvaluate.resetGroups();

            int out = Integer.parseInt(_output);

            if(out != Tag.TRUE && out != Tag.FALSE) {    //If the value was reduced to a single boolean tag, the _expression was successful.
                hasSyntaxError = true;
            }

        } catch (Exception e){
            //throw new Error(ERROR_DEFAULT);
            hasSyntaxError = true;
            return;
        }
    }

    public static void printOutput(){
        if(hasSyntaxError){
            printError();
            hasSyntaxError = false;
        }
        else{
            System.out.printf("%d. Input: %s\t Output Tag: %d\n", Lexer.getInstance().lineCount, _expression, Integer.parseInt(_output));
        }
    }

    private static void printError(){
        System.err.printf("%s %s near line: %d\n", ERROR_DEFAULT, Lexer.getInstance().getLine(), Lexer.getInstance().lineCount);
    }


    /**
     * Accounts for extremely nested paren groups. A little unnecessary but hey why not. Each group of
     * matching parentheses are given corresponding tags. This greatly improves the the accuracy of the expression
     * evaluation since RegEx matches towards the center of the string (start --> middle <--end).
     * @param input the string to be grouped
     * @return the updated string
     */
    private static String setUniqueGroups(String input){
        String[] split = input.split("\\s");
        int tag = Tag.PAREN_GROUP;                      //Start at first paren group value.

        //By default the first and last values must be parentheses
        split[0] = Integer.toString(tag);
        split[split.length - 1] = Integer.toString(tag);

        tag++;                                  //The tag of the unique parentheses group
        int open = 1;                           //The count of open parentheses
        int close = 1;                          //The count of closing parentheses
        int difference = open - close;          //The difference in opening and closing parentheses

        //Iterate through the array
        for (int i = 1; i < split.length - 1; i++){
            int currentTag = Integer.parseInt(split[i]);            //Get the tag of the current value

            if(currentTag == (int)'('){                             //Change the value of the opening paren
                split[i] = Integer.toString(tag);
                tag++;
                open++;
                difference = open - close;
            }
            else if(currentTag == (int)')'){                        //Change the value of the closing paren
                split[i] = Integer.toString(tag - difference );
                close++;
                difference = open - close;
            }
        }

        StringBuilder sb = new StringBuilder();      //Build a new string
        for(String string: split){
            sb.append(string);
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * Throw an error if one is encountered
     * @param error the string containing the error message
     */
    private static void error(String error){
        throw new Error(error + Lexer.lineCount);
    }

}
