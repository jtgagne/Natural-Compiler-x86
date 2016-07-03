package parser;

import lexer.Lexer;

import java.io.IOException;

/**
 * Just a file to test the boolean expression class and the For class
 * Created by gagnej3 on 6/14/16.
 */
public class Test {
    private static final String FILE_NATURAL_PROGRAM = "natural_program.nat";

    public static void main(String[] args) throws IOException{

        Lexer lex = Lexer.getInstance();

        for(int i = 0; i < args.length; i++){

            lex.openReader(args[i]);

            if(args[i].contains(FILE_NATURAL_PROGRAM)){
                System.out.printf("\n\nTesting: %s\n", FILE_NATURAL_PROGRAM);
                Parser parser = new Parser();
                parser.runParser();
            }

            lex.closeReader();
        }
    }


}