package information;
import inter.Id;
import inter.Set;
import lexer.Lexer;
import lexer.Token;

import java.io.*;

/**
 * Class for writing parser information to files in the output directory. Each file is named with the same name as the
 * file that the output was produced from.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Printer {

    private static String mOutputDirectory;
    private static String mFileName;
    private static File mFile;
    private static PrintWriter _writer;
    private static StringBuilder _builder;

    public static void setFile(String fileName, String fileDirectory){
        try{
            mFileName = fileName;
            mOutputDirectory = fileDirectory;
            String path = mOutputDirectory + "/" + mFileName;
            mFile = new File(path);
            _writer = new PrintWriter(mFile);
            _builder = new StringBuilder();
            writeFilePath(fileDirectory);
            writeFileName(fileName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void close(){
        _writer.close();
    }

    public static void writeFilePath(String filePath) throws Exception{
        _writer.printf("Path: %s\n", filePath);
        _writer.flush();
    }

    public static void writeFileName(String fileName) throws Exception {
        _writer.printf("Evaluating: %s\n\n", fileName);
        _writer.flush();
    }

    public static void printFileName() {
        System.err.printf("Evaluating: %s\n\n", mFileName);
        System.err.flush();
    }

    public static void writeAssignment(Set assignment){
        _writer.printf("\t%s\n", assignment.toString());
        _writer.flush();
    }

    public static void writeLexerLine(String line, int number) {
        _writer.printf("\nLexer line %d: %s\n", (number + 1), line);
        _writer.flush();
    }

    public static void writeIdentifier(Id id){
        _writer.printf("\t%s\n", id.toString());
        _writer.flush();
    }

    public static void writeToken(Token token) {
        _writer.printf("\tToken: %s\n", token.toString());
        _writer.flush();
    }

    public static void writeEnvInfo() {
        _writer.printf("\tNew environment instantiated.\n");
        _writer.flush();
    }

    public static void writeRuntimeError(String fileName, String message) {
        _writer.printf("\nERROR IN FILE: %s:\n%s\n", fileName, message);       //Print the file name and the error encountered
        _writer.flush();
    }

    /**
     * @param fromType initial data type of something
     * @param toType the data type being converted to.
     */
    public static void writeNarrowing(String fromType, String toType){
        _writer.printf("\nTYPE WARNING: converting %s to %s may result in data loss. Near line: %d\n", fromType, toType, Lexer.lineCount);
        _writer.flush();
    }

    /**
     * @param initialType initial data type of something
     * @param castTo the data type being converted to.
     */
    public static void writeWidening(String initialType, String castTo){
        _writer.printf("\nTYPE WARNING:  converting %s to %s may result in extra memory allocation. Near line: %d\n", initialType, castTo, Lexer.lineCount);
        _writer.flush();
    }


    /**
     * Converting a floating point value to an int
     * @param fromType initial data type of something
     * @param toType the data type being converted to.
     */
    public static void writeFloatingToWhole(String fromType, String toType){
        _writer.printf("\nTYPE WARNING: converting floating point: %s to whole value: %s may result in data loss. Near line: %d\n", fromType, toType, Lexer.lineCount);
        _writer.flush();
    }

    public static void writeIncompatibleTypes(String fromType, String toType){
        _writer.printf("\nTYPE WARNING: inconvertible types: can't convert %s to %s Near line: %d\n", fromType, toType, Lexer.lineCount);
        _writer.flush();
    }

}
