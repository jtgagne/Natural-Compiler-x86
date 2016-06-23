package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Handle syntax parsing of a for loop, allows infinite for loops using for()
 * Created by gagnej3 on 6/18/16.
 */
public class For {

    private static boolean hasSyntaxError = false;
    private static final Pattern parentheses =
            Pattern.compile("40(?s)(.*)41");

    //TODO: we need to add tags for data types besides just 'BASIC' and limit this pattern to solely ints
    private static final Pattern standard =
            Pattern.compile("("+Tag.BASIC+")\\s("+Tag.ID+")\\s("+Tag.ASSIGNMENT+")\\s("+Tag.NUM+")\\s("+Tag.TO+")\\s("+Tag.NUM+")\\s(("+Tag.INCREASE+")|("+Tag.DECREASE+"))\\s("+Tag.NUM+")");

    public static void evaluateSyntax(){
        try{
            String _expression = ReadGroup.readExpression(ReadType.FOR_LOOP);
            evaluateSyntax(_expression);
        } catch (Exception e){
            hasSyntaxError = true;
        }
    }

    private static void evaluateSyntax(String _expression){
        String expression = _expression;

        Matcher p  = parentheses.matcher(expression);

        //No parentheses enclosing the for loop
        if(!p.find()){
            hasSyntaxError = true;
            return;
        }

        expression = p.group(1);

        //Match with the inside of the parentheses
        Matcher s = standard.matcher(expression);

        //If not in the correct format
        if(!s.find()){
            //if the loop isn't calling for an infinite loop an error has occurred.
            if(expression.trim().length() != 0){
                hasSyntaxError = true;
            }
        }
    }

    public static void printOutput(){
        if(hasSyntaxError){
            System.err.printf("For loop syntax error: %s near line: %d\n", Lexer.getInstance().getLine(), Lexer.getInstance().lineCount);
        }else{
            System.out.printf("%d. For loop input: %s  syntax is valid\n", Lexer.getInstance().lineCount, Lexer.getInstance().getLine());
        }
        hasSyntaxError = false;
    }
}
