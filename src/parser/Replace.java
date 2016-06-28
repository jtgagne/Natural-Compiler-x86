package parser;

import lexer.Tag;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to replaces tags in strings.
 * Created by gagnej3 on 6/27/16.
 */
public class Replace {

    /**
     * Put the identifiers back into the String for their respective identifier tags
     * @param parseString the string be altered to be used in the parser
     * @return the updated String
     */
    public static String replaceIdentifiers(String parseString, ArrayList<String> identifiers){

        String updated = parseString;

        int idNum = 0;

        //No identifiers to be replaced
        if(identifiers.size() == 0){
            return updated;
        }

        //Pattern for the identifier tag inside the input string
        Pattern idPattern = Pattern.compile("("+Tag.ID+")");

        //Match the string for identifiers
        Matcher id = idPattern.matcher(updated);

        while(id.find()){
            updated = id.replaceFirst(identifiers.get(idNum));      //Replace an identifier with it's name
            idNum++;
            id = idPattern.matcher(updated);                        //Re-match for more identifiers
        }

        return updated;
    }

    /**
     * Accounts for  nested paren groups. Each group of
     * matching parentheses are given corresponding tags and should be evaluated in their groups.
     * This greatly improves the the accuracy of the expression
     * evaluation since RegEx matches towards the center of the string (start --> middle <--end).
     * @param input the string to be grouped
     * @return the updated string
     */
    public static String setUniqueGroups(String input){
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
    public static String simplifyPhrases(String expression){
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
}
