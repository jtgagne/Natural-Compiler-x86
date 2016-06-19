package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Handle syntax parsing of a for loop
 * Created by gagnej3 on 6/18/16.
 */
public class For {

    private static int count = 1;
    private static final Pattern parentheses =
            Pattern.compile(40+"(?s)(.*)"+41);

    //TODO: we need to add tags for data types besides just 'BASIC' and limit this pattern to solely ints
    private static final Pattern standard =
            Pattern.compile("(("+Tag.BASIC+")\\s("+Tag.ID+")\\s("+Tag.ASSIGNMENT+")\\s("+Tag.NUM+")\\s("+Tag.TO+")\\s("+Tag.NUM+")\\s(("+Tag.INCREASE+")|("+Tag.DECREASE+"))\\s("+Tag.NUM+"))");

    public static void evaluateSyntax() throws IOException{
        String _expression = ReadGroup.readExpression(ReadType.FOR_LOOP);
        evaluateSyntax(_expression);
    }

    private static void evaluateSyntax(String _expression){
        String expression = _expression;

        Matcher p  = parentheses.matcher(expression);

        //No parentheses enclosing the for loop
        if(!p.find()){
            throw new Error("Expected parentheses after for near line: " + Lexer.getInstance().lineCount);
        }

        expression = p.group(1);
        //Match with the inside of the parentheses
        Matcher s = standard.matcher(expression);


        //If not in the correct format
        if(!s.find()){
            //if the loop isn't calling for an infinite loop an error has occurred.
            if(expression.trim().length() != 0){
                String error = "For loop syntax error near line: " + Lexer.getInstance().lineCount;
                throw new Error(error);
            }
        }


    }
}
