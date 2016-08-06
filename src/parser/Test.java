package parser;

import code_generation.AssemblyFile;
import information.Printer;
import lexer.Lexer;

import java.io.File;
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

    public static void main(String[] args) throws Exception{

        Path currentRelativePath = Paths.get("");                           // Get the path to the project directory
        String path = currentRelativePath.toAbsolutePath().toString();      // Convert the path to a string

        String inputPath = path + "/src/test";                              // Add the extension to the test files directory
        String outputAsmPath = path + "/src/generated_assembly";
        String outputPrintPath = path + "/src/output";
        File folder = new File(inputPath);                                  // Reference the test directory
        File[] testFiles = folder.listFiles();                              // Create an array of all files in the test directory.

        assert testFiles!=null;

        for(File file: testFiles){
            String filePath = file.getPath();                         // Get the path of the first file to be evaluated

            if(!filePath.contains("7")) continue;                     // Make sure it is a .nat file

            String contents[] = filePath.split("/");                  // State the name of the file being evaluated
            String fileName = contents[contents.length-1];

            AssemblyFile assemblyFile = new AssemblyFile(fileName, outputAsmPath);   //New Assembly File with the name of the file being evaluated

            Printer.setFile(fileName, outputPrintPath);          //Update the file to write to

            Lexer.getInstance().openReader(filePath);                 // Open the first file

            try{
                Parser parser = new Parser();
                parser.runParser();

            } catch (Error e){
                Printer.writeRuntimeError(fileName, e.toString());
            }

            Lexer.getInstance().closeReader();
            assemblyFile.generateAsmFile();
            Printer.close();
        }

    }
}