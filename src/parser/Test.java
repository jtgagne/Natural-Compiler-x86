package parser;

import information.Printer;
import lexer.Lexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Just a file to test the parser
 * Created on 6/14/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Test {

    private static final String EXTENSION = ".nat";
    private static final String IF = "if_statement";
    private static boolean _eof = false;

    public static void main(String[] args) throws Exception{

        Path currentRelativePath = Paths.get("");                           // Get the path to the project directory
        String path = currentRelativePath.toAbsolutePath().toString();      // Convert the path to a string
        Printer.setOutput(path + "/src/output");

        path += "/src/test";                                                // Add the extension to the test files directory
        File folder = new File(path);                                       // Reference the test directory
        File[] testFiles = folder.listFiles();                              // Create an array of all files in the test directory.

        assert testFiles!=null;

        for(File file: testFiles){
            String filePath = file.getPath();                               // Get the path of the first file to be evaluated

            if(!filePath.contains("1")) continue;                     // Make sure it is a .nat file

            String contents[] = filePath.split("/");                        // State the name of the file being evaluated
            String fileName = contents[contents.length-1];

            Printer.setFileName(fileName);                                  //Update the file to write to

            Printer.printFilePath(filePath);
            Printer.printFileName(fileName);

            Lexer.getInstance().openReader(filePath);                       // Open the first file

            try{
                Parser parser = new Parser();
                parser.runParser();

            } catch (Error e){
                Printer.printRuntimeError(fileName, e.toString());
            }

            Lexer.getInstance().closeReader();
            Printer.close();
        }

    }
}