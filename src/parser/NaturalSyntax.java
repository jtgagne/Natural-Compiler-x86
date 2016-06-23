package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import java.io.File;
import java.io.IOException;

/**
 * Class to evaluate proper syntax of various control statements
 * Created by gagnej3 on 6/19/16.
 */
public class NaturalSyntax {

    /**
     * Checks the name of the control statement and decides the type of expression expected next
     */
    public static void evaluateControl() throws IOException{
        Token token = Lexer.getInstance().scan();

        int tag = token.tag;

        switch (tag){
            case Tag.FOR:
                For.evaluateSyntax();                       //Read from the lexer in the For class and check for proper syntax
                For.printOutput();
                break;
            case Tag.IF:
                BooleanExpression.evaluateExpression();     //Read from the lexer in the BooleanExpression class and confirm proper syntax
                BooleanExpression.printOutput();
                break;
        }
    }
}
