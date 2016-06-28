package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import java.io.IOException;
import java.util.ArrayList;
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

            _expression = setUniqueGroups(_expression);                            // Set unique values for inner groupings of parentheses

            _expression = replaceIdentifiers(_expression);                         // Combine the compound phrases before evaluation

            _parse = replaceIdentifiers(_expression);                              // Set the String to be passed to the parser containing tags and variables

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
            System.out.printf("%d. Parse String: %s\n", Lexer.getInstance().lineCount, _parse);
        }
    }

    private static void printError(){
        System.err.printf("%s %s near line: %d\n", ERROR_DEFAULT, Lexer.getInstance().getLine(), Lexer.getInstance().lineCount);
        System.err.printf("%d. Parse String: %s\n", Lexer.getInstance().lineCount, _parse);
    }


    /**
     * Accounts for  nested paren groups. Each group of
     * matching parentheses are given corresponding tags and should be evaluated in their groups.
     * This greatly improves the the accuracy of the expression
     * evaluation since RegEx matches towards the center of the string (start --> middle <--end).
     * @param input the string to be grouped
     * @return the updated string
     */
    private static String setUniqueGroups(String input){
        String[] split = input.split("\\s+");
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
     * looks for the following phrases: "less than or equal to" and "greater than or equal to" and replaces their tags
     * with LE or GE.
     * @return a string with all long phrases simplified to a single tag
     */
    private static String simplifyPhrases(String expression){
        //Looks for (less than | greater than) or equal to
        Pattern longPhrase = Pattern.compile("((("+Tag.LESS+")|("+Tag.GREATER+"))\\s("+Tag.OR+")\\s("+Tag.EQ+"))");

        //Match for the pattern
        Matcher l = longPhrase.matcher(expression);

        while (l.find()){
            //Get the first pattern found.
            String group = l.group(1);

            //Split the group on spaces
            String [] split = group.split("\\s+");
            assert split.length >= 3;

            //Replace the group with a GE tag
            if(Integer.parseInt(split[0]) == Tag.GREATER && Integer.parseInt(split[1]) == Tag.OR &&
                    Integer.parseInt(split[2]) == Tag.EQ){
                group = Integer.toString(Tag.GE);
            }

            //replace the group with a LE Tag
            else if(Integer.parseInt(split[0]) == Tag.LESS && Integer.parseInt(split[1]) == Tag.OR &&
                    Integer.parseInt(split[2]) == Tag.EQ){
                group = Integer.toString(Tag.LE);
            }

            //Replace the first f
            expression = l.replaceFirst(group);

            //Rematch the phrase
            l = longPhrase.matcher(expression);
        }

        return expression;
    }

    /**
     * Put the identifiers back into the String for their respective identifier tags
     * @param parseString the string be altered to be used in the parser
     * @return the updated String
     */
    private static String replaceIdentifiers(String parseString){
        String updated = parseString;

        //Get the identifiers that were found in the code
        ArrayList<String> identifiers = new ArrayList<>();

        identifiers.addAll(ReadGroup.getIdentifiers());

        //Clear the ArrayList from the lexer and the read group class
        ReadGroup.clearIdentifiers();

        int idNum = 0;

        //No identifiers to be replaced
        if(identifiers.size() == 0){
            return updated;
        }

        //Pattern for the identifier tag inside the input string
        Pattern idPattern = Pattern.compile("(("+Tag.ID+"))");

        //Match the string for identifiers
        Matcher id = idPattern.matcher(updated);

        while(id.find()){
            updated = id.replaceFirst(identifiers.get(idNum));      //Replace an identifier with it's name
            idNum++;

            id = idPattern.matcher(updated);                        //Re-match for more identifiers
        }

        return updated;
    }

}
