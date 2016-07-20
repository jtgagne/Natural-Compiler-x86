package information;
import inter.Id;
import lexer.Lexer;
import lexer.Token;

import java.io.*;

/**
 * Print stream occurs out of order due to different thread printing
 * Created by gagnej3 on 7/19/16.
 */
public class Printer {

    private static String mOutputDirectory;
    private static String mFileName;
    private static File mFile;
    //private static BufferedWriter _writer;
    private static PrintWriter _writer;
    private static StringBuilder _builder;

    public static void setOutput(String outputLocation){
        mOutputDirectory = outputLocation;
    }

    public static void setFileName(String fileName){
        try{
            mFileName = fileName;
            String path = mOutputDirectory + "/" + mFileName;
            mFile = new File(path);
            _writer = new PrintWriter(mFile);
            _builder = new StringBuilder();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void close(){
        _writer.close();
    }

    public static void printFilePath(String filePath) throws Exception{
        _writer.printf("Path: %s\n", filePath);
        _writer.flush();
    }

    public static void printFileName(String fileName) throws Exception {
        _writer.printf("Evaluating: %s\n\n", fileName);
        _writer.flush();
    }

    public static void printLexerLine(String line, int number) {
        _writer.printf("Lexer line %d: %s\n", (number + 1), line);
        _writer.flush();
    }

    public static void printIdentifier(Id id){
        _writer.printf("\t%s\n", id.toString());
        _writer.flush();
    }

    public static void printToken(Token token) {
        _writer.printf("\tToken: %s\n", token.toString());
        _writer.flush();
    }

    public static void printEnvInfo() {
        _writer.printf("\nNew environment instantiated.\n");
        _writer.flush();
    }

    public static void printRuntimeError(String fileName, String message) {
        _writer.printf("\nERROR IN FILE: %s:\n%s\n", fileName, message);       //Print the file name and the error encountered
        _writer.flush();
    }

}
