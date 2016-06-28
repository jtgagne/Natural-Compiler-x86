package parser;

import lexer.Lexer;
import lexer.Tag;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Handle syntax parsing of a for loop, allows infinite for loops using for()
 * Created by gagnej3 on 6/18/16.
 */
public class For {

    private static String _parse;
    private static boolean hasSyntaxError = false;
    private static final Pattern parentheses =
            Pattern.compile(Tag.PAREN_GROUP+"(?s)(.*)"+Tag.PAREN_GROUP);

    private static final Pattern standard =
            Pattern.compile("(("+Tag.INT+"?)\\s("+Tag.ID+")\\s("+Tag.ASSIGNMENT+")\\s("+Tag.NUM+")\\s("+Tag.TO+")\\s("+Tag.NUM+")\\s(("+Tag.INCREASE+")|("+Tag.DECREASE+"))\\s("+Tag.NUM+"))");

    //For loop without declaration
    private static final Pattern noDeclaration =
            Pattern.compile("("+Tag.ID+")\\s("+Tag.ASSIGNMENT+")\\s("+Tag.NUM+")\\s("+Tag.TO+")\\s("+Tag.NUM+")\\s(("+Tag.INCREASE+")|("+Tag.DECREASE+"))\\s("+Tag.NUM+")");

    public static void evaluateSyntax(){
        try{
            String _expression = ReadGroup.readExpression(ReadType.FOR_LOOP);       //Set the expression from the ReadGroup class
            _expression = Replace.setUniqueGroups(_expression);
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
        Matcher nd = noDeclaration.matcher(expression);

        if(s.find()){
            expression = s.replaceFirst("");
        } else if(nd.find()){
            expression = nd.replaceFirst("");
        }

        //If not in the correct format
        if(!s.find() && !nd.find()){
            //if the loop isn't calling for an infinite loop an error has occurred.
            if(expression.trim().length() != 0){
                hasSyntaxError = true;
            }
        }

        _parse = Replace.replaceIdentifiers(_expression, ReadGroup.getIdentifiers());    //Set the parse string
        ReadGroup.clearIdentifiers();

    }

    public static String getParseString(){
        return _parse;
    }

    public static void printOutput(){
        if(hasSyntaxError){
            System.err.printf("For loop syntax error: %s near line: %d\n", Lexer.getInstance().getLine(), Lexer.getInstance().lineCount);
            System.err.printf("Parse String: %s\n", _parse);
        }else{
            System.out.printf("%d. For loop input: %s  syntax is valid\n", Lexer.getInstance().lineCount, Lexer.getInstance().getLine());
            System.out.printf("Parse String: %s\n", _parse);
        }
        hasSyntaxError = false;
    }
}
