package parser;

import lexer.Lexer;

import java.io.IOException;

/**
 * Just a file to test the boolean expression class and the For class
 * Created by gagnej3 on 6/14/16.
 */
public class Test {
    private static final String FILE_BOOLEAN_EXPR = "boolean_expr_test.nat";
    private static final String FILE_FOR = "for_loop_test.txt";
    private static final String FILE_NATURAL_SYNTAX = "natural_syntax.nat";
    private static final String FILE_NATURAL_PROGRAM = "natural_program.nat";

    public static void main(String[] args) throws IOException{

        Lexer lex = Lexer.getInstance();

        for(int i = 0; i < args.length; i++){

            lex.openReader(args[i]);
            if(args[i].contains(FILE_NATURAL_PROGRAM)){
                System.out.printf("\n\nTesting: %s\n", FILE_NATURAL_PROGRAM);
                Parser parser = new Parser(lex);
                parser.program();
            }
            if(args[i].contains(FILE_BOOLEAN_EXPR)){
                System.out.printf("\n\nTesting: %s\n", FILE_BOOLEAN_EXPR);
                testBooleanExpression();
            }

            else if(args[i].contains(FILE_FOR)){
                System.out.printf("\n\nTesting: %s\n", FILE_FOR);
                testForSyntax();
            }

            else if(args[i].contains(FILE_NATURAL_SYNTAX)){
                System.out.printf("\n\nTesting: %s\n", FILE_NATURAL_SYNTAX);
                testNaturalFile();
            }

            lex.closeReader();
        }
    }


    private static void testBooleanExpression(){
        while(Lexer.getInstance().hasNext()){
            BooleanExpression.evaluateExpression();
            BooleanExpression.printOutput();
        }
    }

    private static void testForSyntax() throws IOException{

        while(Lexer.getInstance().hasNext()){
            For.evaluateSyntax();
            For.printOutput();
        }

    }

    private static void testNaturalFile() throws IOException{
        int count = 1;
        while(Lexer.getInstance().hasNext()){
            NaturalSyntax.evaluateControl();
            count++;
        }
    }
}