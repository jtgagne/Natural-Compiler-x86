package code_generation;

import inter.Node;
import inter.Stmt;

import java.io.File;
import java.io.PrintWriter;

/**
 * A Class to handle the assembly file generation
 * Created by gagnej3 on 8/5/16.
 */
public class AssemblyFile {

    private static StringBuilder mHeader;
    private static StringBuilder mMain;
    private static StringBuilder mData;
    private static StringBuilder mVariables;
    private static StringBuilder mStrings;
    private static StringBuilder mConstants;
    private static String mOutputDirectory;
    private static String mFileName;
    private static File mFile;
    private static PrintWriter _writer;

    /**
     * Creates a new assembly file with the same name as the input_files file
     * @param fileName name of the file
     */
    public AssemblyFile(String fileName, String outputPath){
        String[] split = fileName.split("\\.");
        mFileName = split[0] + ".asm";
        mOutputDirectory = outputPath;
        mHeader = new StringBuilder();
        mMain = new StringBuilder();
        mData = new StringBuilder();
        mVariables = new StringBuilder();
        mStrings = new StringBuilder();
        mConstants = new StringBuilder();
        addToHeader("\n\t.text\n\n");
        addToHeader("\n\t.globl main\n\n");
        addToMain("main:\n\n");
        setFile(mFileName);
    }

    private void setFile( String fileName ){
        try{
            mFileName = fileName;
            String path = mOutputDirectory + "/" + mFileName;
            mFile = new File(path);
            _writer = new PrintWriter(mFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Only to be used for the data header
     * @param data the string to be added
     */
    private static void addData(String data){
        if(data == null) return;
        mData.append(data);
    }

    /**
     * Add all variables to be listed in the global variables section
     * @param variable assembly variable declaration
     */
    public static void addVariables(String variable){
        if(variable == null) return;
        mVariables.append(variable);
    }

    /**
     * Add all string messages to be listed in the global variables section
     * @param message a message to be displayed
     */
    public static void addStrings(String message){
        if(message == null) return;
        mStrings.append(message);
    }


    public static void addDataToFront(String data){
        if(data == null) return;
        mData.insert(0, data);
    }

    public static void addConstant(String constant){
        if(constant == null) return;
        mConstants.append(constant);
    }

    public static void addToMain(String data){
        mMain.append(data);
    }

    public static void addToHeader(String data){
        mHeader.append(data);
    }

    /**
     * Write all the string builder objects to the new .asm file
     */
    public void generateAsmFile(){
        String endLabel = Stmt.labelAfter;
        mMain.append("\tli $v0, 10\t\t#Load system call to exit\n");
        mMain.append("\tsyscall\n\n");
        mConstants.append(ASMGen.genBooleanTrue());
        mConstants.append(ASMGen.genBooleanFalse());
        mConstants.append(ASMGen.genBooleanTrueStr());
        mConstants.append(ASMGen.genBooleanFalseStr());
        _writer.write(mHeader.toString());
        _writer.write(mMain.toString());
        _writer.write(mData.toString());
        _writer.write(mVariables.toString());
        _writer.write(mConstants.toString());
        _writer.write(mStrings.toString());
        _writer.close();
        mHeader = null;
        mMain = null;
        mData = null;
        mVariables = null;
        mConstants = null;
        mStrings = null;
        Node.resetLabels();
        ASMGen.resetConstantCount();
        ASMGen.resetLabelCount();
    }
}
