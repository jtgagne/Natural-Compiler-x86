package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import java.io.IOException;
import java.util.ArrayList;

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

    private static int exprCount = 1;
    private static final String ERROR_DEFAULT = "Invalid boolean expression near lineCount: ";
    private static final String ERROR_EMPTY_EXPR = "Empty boolean expression near lineCount: ";
    private static final String ERROR_INCOMPATIBLE_TYPE = "Incompatible type comparison near lineCount: ";
    private static final String ERROR_BOOLEAN_JOIN = "Invalid boolean expression ending with an operator near lineCount: ";
    private static final String ERROR_MISSING_COMPARISON = "Missing comparison operator near lineCount: ";
    private static final String ERROR_MISSING_PAREN = "Missing parenthesis near lineCount: ";
    private static ArrayList<Token> _expr;         //An array list containing a list of tokens supposed to be a boolean expression
    private static Lexer _lexer;
    private static Token _look;
    private static String _expression;
    private static int _groups;

    /**
     * Required public constructor.
     */
    BooleanExpression(){

    }


    /**
     * Evaluate boolean expressions when they are expected by the parser.
     * @throws IOException
     */
    public static void evaluateExpression() throws IOException {
        _groups = 0;
        _expression = readExpression(); //set _expr

        _expression = setUniqueGroups(_expression);

        String output;

        try{
            output = BooleanEvaluate.evaluateBooleanExpression(_expression);
            BooleanEvaluate.resetGroups();
        } catch (Exception e){
            throw new Error(ERROR_DEFAULT);
        }

        int out = Integer.parseInt(output);

        if(out == Tag.TRUE || out == Tag.FALSE){    //If the value was reduced to a single boolean tag, the _expression was successful.
            System.out.printf("%d. Input: %s\t Output Tag: %d\n", exprCount, _expression, out);
            exprCount++;
        } else{
            error(ERROR_DEFAULT);
        }
    }

    /**
     * Populates an array list of tokens obtained from the current instance of the Lexer. These tokens are to be
     * evaluated for proper format.
     */
    private static String readExpression() throws IOException{
        _lexer = Lexer.getInstance();
        _expr = new ArrayList<>();

        boolean isLastParen = false;
        int open = 0;                   //Count of open parentheses
        int close = 0;                  //Count of closing parentheses
        move();                         //Get the first token from the lexer

        if(check('(')){
            _expr.add(_look);
        }

        match('(');                     //Expressions should be contained within parentheses (calls move if successful throws error otherwise)
        open++;

        do{
            if(check('(')) {
                open++;
            }

            else if (check(')')){                   //update the state of the _expression given a group of parentheses has closed
                close++;
                if(close > open){                   // closing paren count is always going to be <= open paren count
                    error(ERROR_MISSING_PAREN);
                }
                if(close == open){
                    isLastParen = true;
                }
            }

            else if(check('\n')){
                continue;
            }

            _expr.add(_look);                       //Add the token to the cumulative list

            //Don't update if the boolean is complete, this will continue scanning and interfere with the next line
            if(!isLastParen){
                move();                                 //Set _look to the next token
            }

        }while(close != open);

        _groups = open;                                 //Keep track of the number of parentheses
        return concatExpr();
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
        int tag = Tag.PAR1;                      //Start at first paren group value.

        //By default the first and last values must be parentheses
        split[0] = Integer.toString(tag);
        split[split.length - 1] = Integer.toString(tag);
        String temp = split.toString();

        tag++;                                  //The tag of the unique parentheses group
        int open = 1;                           //The count of open parentheses
        int close = 1;                          //The count of closing parentheses
        int currentGroup = 1;                   //The count of groups that have been formed
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
                currentGroup++;
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
     * TODO: this can probably be combined with setUniqueGroups eventually, too lazy at the moment
     * @return the string to be evaluated
     */
    private static String concatExpr(){
        StringBuilder sb = new StringBuilder();         //Build a string from the acquired tokens

        for(int i = 0 ; i < _expr.size(); i++){

            Token token = _expr.get(i);
            sb.append(token.tag);

            //The last value before the closing paren
            if(i == _expr.size() - 2){
                int tag = token.tag;
                if(Tag.isComparisonOperator(tag) || Tag.isJoiningOperator(tag)){
                    error(ERROR_BOOLEAN_JOIN);
                }
            }

            if(i == _expr.size() - 1){
                continue;
            }

            sb.append(" ");
        }

        String input = sb.toString();

        return input;
    }

    /**
     * Get the next token from the lexer. If the lexer is making a phrase, continue getting tokens until the phrase is
     * complete
     * @throws IOException exception thrown by the lexer.
     */
    private static void move() throws IOException{
        _look = _lexer.scan();
        while(_lexer.isMakingPhrase()){
            _look = _lexer.scan();
        }
    }


    /**
     * Used to check various situations that could occur
     * @param tag the tag of the token
     * @return true if the token is what is required
     */
    private static boolean check(int tag){
        if(_look.tag == tag)
            return true;
        return false;
    }

    /**
     * match method to get the next input of the lexer
     * @param c a char to match
     * @throws IOException
     */
    private static void match(char c) throws IOException{
        Token previous = _look;

        if(_look.tag == c){
            move();

            //Make sure there isn't the following occurring: ()
            if(previous.tag == '(' && _look.tag == ')'){
                error(ERROR_EMPTY_EXPR);
            }
        }
        else{
            error(ERROR_DEFAULT);
        }
    }

    /**
     * Throw an error if one is encountered
     * @param error the string containing the error message
     */
    private static void error(String error){
        throw new Error(error + Lexer.lineCount);
    }

}
