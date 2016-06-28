package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;
import symbols.Array;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Read a grouping of information that is expected to be grouped in parentheses.
 * Created by gagnej3 on 6/18/16.
 */
public class ReadGroup {

    private static final String ERROR_DEFAULT = "Invalid boolean expression near line: ";
    private static final String ERROR_EMPTY_EXPR = "Empty boolean expression near line: ";
    private static final String ERROR_BOOLEAN_JOIN = "Invalid boolean expression ending with an operator near line: ";
    private static final String ERROR_MISSING_PAREN = "Missing parenthesis near line: ";
    private static final String ERROR_FILE = "File ended unexpectedly near line: ";
    private static String _expression = "";
    private static ArrayList<Token> _expr;         //An array list containing a list of tokens supposed to be a boolean expression
    private static Lexer _lexer;
    private static Token _look;
    private static String _line;
    private static ReadType _type;
    private static ArrayList<String> _identifiers;

    /**
     * Read a value of grouped tokens from the lexer, return the values in a string seperated by spaces
     * @return string of tokens
     * @throws IOException
     */
    public static String readExpression(ReadType type) throws IOException{
        _type = type;
        _lexer = Lexer.getInstance();
        _expr = new ArrayList<>();
        _identifiers = new ArrayList<>();

        boolean isLastParen = false;
        int open = 0;                   //Count of open parentheses
        int close = 0;                  //Count of closing parentheses

        move();                         //Get the first token from the lexer

        _line = Lexer.getInstance().getLine();

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

        //Get the identifiers found in the boolean expression
        _identifiers.addAll(Lexer.getIdentifiers());

        //Clear the ArrayList of identifiers
        //Lexer.clearIdentifiers();

        if(_type == ReadType.FOR_LOOP){
            return concatFor();
        }else{
            return concatExpr();
        }
    }



    /**
     * TODO: this can probably be combined with setUniqueGroups eventually.
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
            sb.append(" ");
        }

        String input = sb.toString();
        return input.trim();
    }

    private static String concatFor(){
        StringBuilder sb = new StringBuilder();         //Build a string from the acquired tokens
        for(int i = 0; i < _expr.size(); i++){
            sb.append(_expr.get(i).tag);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Get the next token from the lexer. If the lexer is making a phrase, continue getting tokens until the phrase is
     * complete
     * @throws IOException exception thrown by the lexer.
     */
    private static void move() throws IOException {

        try{
            _look = _lexer.scan();
            while(_lexer.isMakingPhrase()){
                _look = _lexer.scan();
            }
        }catch (Exception e){
            throw new Error(ERROR_FILE + Lexer.lineCount);
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
                if(_type == ReadType.BOOLEAN_EXPRESSION) //Throw an error for empty boolean but not empty for loops
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

    public static String getLine(){
        return _line;
    }

    public static ArrayList<String> getIdentifiers(){
        return _identifiers;
    }

    public static void clearIdentifiers(){
        _identifiers.clear();
        Lexer.clearIdentifiers();
    }

}
