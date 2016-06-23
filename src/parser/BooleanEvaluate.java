package parser;

import lexer.Lexer;
import lexer.Tag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Justin Gagne and Zach Farrer
 * Professor Assiter
 * Compiler Design -- Summer 2016
 * Wentworth Institute of Technology
 *
 * Class to be used for evaluating the syntactic correctness of boolean expressions. It checks for grouping of parentheses,
 * proper grouping of expressions, and proper usage of comparison operators. The the parameter for evaluateBooleanExpression
 * is a string of token tags concatenated with spaces in between each tag.
 * Created by gagnej3 on 6/15/16.
 */
public class BooleanEvaluate {

    private static final String ERROR_NEGATION = "Incorrect boolean negation near line: ";

    private static int parenGroup = Tag.PAREN_GROUP;   //Dynamically change the grouping identifiers

    //Look for anything that is in between 40 and 41, these are the int values of '(' and ')' respectively
    private static Pattern parentheses =
            Pattern.compile(parenGroup+"(?s)(.*)"+ parenGroup);

    //Pattern to look for a boolean expression ie: false and true
    private static final Pattern boolExpr =
            Pattern.compile("((("+Tag.FALSE+")|("+(Tag.TRUE)+"))\\s(("+Tag.AND+")|("+Tag.OR+"))\\s(("+ Tag.FALSE+")|("+Tag.TRUE+")))");

    //Looks for (less than | greater than) or equal to
    private static final Pattern longPhrase =
            Pattern.compile("((("+Tag.LESS+")|("+Tag.GREATER+"))\\s("+Tag.OR+")\\s("+Tag.EQ+"))");

    //Pattern to look for comparative statements
    private static final Pattern comparativeExpr =
            Pattern.compile("(("+Tag.NUM+")\\s(("+Tag.EQ+")|("+Tag.GE+")|("+Tag.GREATER+")|("+Tag.LE+")|("+Tag.LESS+")|("+Tag.NE+"))\\s("+Tag.NUM+"))");

    private static final Pattern negation =
            Pattern.compile("(("+Tag.NOT+")\\s(("+Tag.FALSE+")|("+(Tag.TRUE)+")))");

    /**
     * Recursive method to evaluate boolean expressions. Accounts for the order of operations dictated by parentheses grouping.
     * Reduces the expression down to its single boolean meaning and returns the Tag value in the form of a string upon completion.
     * @param input the string to be evaluated
     * @return a simplified string containing Lexer Tag values
     * @throws Exception upon incorrect boolean expression format
     */
    public static String evaluateBooleanExpression(String input) throws Exception{

        //int groups = parenCount;
        String output = input;

        //Recompile for the new input string that is coming in
        parentheses = Pattern.compile(parenGroup+"(?s)(.*)"+ parenGroup);

        //Instantiate the initial matcher objects
        Matcher p = parentheses.matcher(output);
        Matcher lp = longPhrase.matcher(output);
        Matcher be = boolExpr.matcher(output);
        Matcher ce = comparativeExpr.matcher(output);
        Matcher n = negation.matcher(output);

        //Look for groups of parentheses and replace them with a simplified value
        while(p.find()){
            //Increase the grouping here. If there are parentheses remaining, we will want the next group in the recursive stack
            if(Tag.containsParentheses(output)){
                parenGroup++;
            }
            output = p.replaceFirst(evaluateBooleanExpression(p.group(1)));      //Call the recursive method using the group that was found
            parentheses = Pattern.compile(parenGroup+"(?s)(.*)"+ parenGroup);
            p = parentheses.matcher(output);                                     //Re-Match for all patterns
            lp = longPhrase.matcher(output);
            be = boolExpr.matcher(output);
            ce = comparativeExpr.matcher(output);
            n = negation.matcher(output);
        }

        while(lp.find()){
            output = lp.replaceFirst(simplifyPhrase(lp.group(1)));      //Simplify the long phrase and replace with a single tag
            lp = longPhrase.matcher(output);                            //Re-Match for all patterns
            be = boolExpr.matcher(output);
            ce = comparativeExpr.matcher(output);
            n = negation.matcher(output);
        }

        //Look for and solve all comparative boolean expressions
        while(ce.find()){
            output = ce.replaceFirst(solveBooleanExpression(ce.group(1)));   //Solve and replace with a simplified boolean expression
            ce = comparativeExpr.matcher(output);                            //Re-Match for all patterns
            be = boolExpr.matcher(output);
            n = negation.matcher(output);
        }

        //Look for a boolean expression pattern
        while(be.find()){
            output = be.replaceFirst(solveBooleanExpression(be.group(1)));  //Solve and replace with a simplified boolean expression
            be = boolExpr.matcher(output);                                  //Re-Match for all patterns
            n = negation.matcher(output);
        }

        while (n.find()){
            output = n.replaceFirst(getBooleanNegation(n.group(1)));        //Negate a boolean value and replace in the string
            n = negation.matcher(output);                                   //Re-Match for all patterns
        }

        return output.trim();
    }

    /**
     * Method called by the BooleanExpression class to reset the initial numbering of parentheses.
     */
    public static void resetGroups(){
        parenGroup = Tag.PAREN_GROUP;
    }

    /**
     * Method that is called to simplify a compound phrase
     * @param input string containing a compound phrase
     * @return the simplified string
     */
    private static String simplifyPhrase(String input){
        String[] split = input.split("\\s+");
        assert split.length >= 3;

        if(Integer.parseInt(split[0]) == Tag.GREATER && Integer.parseInt(split[1]) == Tag.OR &&
            Integer.parseInt(split[2]) == Tag.EQ){
            input = Integer.toString(Tag.GE);
        }

        else if(Integer.parseInt(split[0]) == Tag.LESS && Integer.parseInt(split[1]) == Tag.OR &&
                Integer.parseInt(split[2]) == Tag.EQ){
            input = Integer.toString(Tag.LE);
        }

        else{
            throw new Error("Incorrect comparison operator format");
        }

        return input;
    }

    /**
     * Method to be called when simplifying input
     * @param input the string to be simplified
     * @return the simplified tag value in the form of a string
     * @throws Exception if values are incorrect
     */
    private static String solveBooleanExpression(String input) throws Exception{
        String[] splitInput = input.split("\\s+");      //Create a string array, broken on all white space

        int values[] = new int[splitInput.length];      //create a new int array, same size of the string array

        for(int i = 0; i < splitInput.length; i++){         //Convert the strings to int values
            values[i] = Integer.parseInt(splitInput[i]);
        }

        assert values.length >=3;   //Make sure the array is long enough to contain a boolean expression

        int simplified = 0;         //Variable to store a the simplified tag number

        //Confirm the expression is in the correct format
        if(Tag.isBoolean(values[0]) && Tag.isJoiningOperator(values[1]) && Tag.isBoolean(values[2])){

            //Get the boolean value of the tags and the value of the join operator
            boolean a = getBooleanValue(values[0]);
            boolean b = getBooleanValue(values[2]);
            int c = values[1];

            //Get a simplified value of the boolean expression
            if(c == Tag.AND){
                simplified = getBooleanTag(a&&b);
            }else if (c == Tag.OR){
                simplified = getBooleanTag(a||b);
            }
        }

        else if(Tag.isNumber(values[0]) && Tag.isComparisonOperator(values[1]) && Tag.isNumber(values[2])){
            simplified = getBooleanTag(compareNumbers(values[0], values[1], values[2]));
        }

        //Update the string to be returned
        input = Integer.toString(simplified);

        //Add the remaining values if applicable
        if(values.length > 3){
            for(int i = 3; i < values.length; i++){
                input += " " + Integer.toString(values[i]);       //Add the remaining numbers to the string keep them spaced
            }
        }

        return input;
    }

    /**
     * Returns a boolean value given a 2 numbers and a comparison expression
     * @param num1 first number to be compared
     * @param compareOp the tag of the comparison operator being used
     * @param num2 the second number to be compared
     * @return true or false depending on the outcome
     */
    private static boolean compareNumbers(int num1, int compareOp, int num2){
        switch (compareOp){
            case Tag.EQ:
                return num1 == num2;

            case Tag.GE:
                return num1 >= num2;

            case Tag.GREATER:
                return num1 > num2;

            case Tag.LE:
                return num1 <= num2;

            case Tag.LESS:
                return num1 < num2;

            case Tag.NE:
                return num1 != num2;
        }

        throw new Error("Error comparing values near lineCount: " + Lexer.lineCount);
    }

    /**
     * Returns a boolean true or false given a supposed Boolean Tag
     * @param value the Tag of a Lexer Token
     * @return true or false
     */
    private static boolean getBooleanValue(int value){
        switch (value){
            case Tag.FALSE:
                return false;
            case Tag.TRUE:
                return true;
            default:
                throw new Error("No corresponding boolean value near lineCount: " + Lexer.lineCount);
        }
    }

    /**
     * Negates a boolean value
     * @param input a string containing at least Tag.NOT and Tag.TRUE | Tag.False
     * @return the s
     * @throws Exception
     */
    private static String getBooleanNegation(String input) throws Exception{
        input = input.trim();
        String[] split = input.split("\\s+");
        assert split.length >=2;
        int neg = Integer.parseInt(split[0]);
        int bool = Integer.parseInt(split[1]);

        if(neg == Tag.NOT && bool == Tag.FALSE){
            return Integer.toString(Tag.TRUE);
        }
        else if(neg == Tag.NOT && bool == Tag.TRUE){
            return Integer.toString(Tag.FALSE);
        }

        //Throw an error for incorrect negation
        else {
            String error = ERROR_NEGATION + Lexer.lineCount;
            throw new Error(error);
        }
    }


    private static int getBooleanTag(boolean bool){
        if(bool){
            return Tag.TRUE;
        }
        return Tag.FALSE;
    }

}
