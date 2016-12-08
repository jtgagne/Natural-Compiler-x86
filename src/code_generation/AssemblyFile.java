package code_generation;

import inter.Node;
import inter.Stmt;

import java.io.File;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * A Class to handle the assembly file generation
 * Created by gagnej3 on 8/5/16.
 */
public class AssemblyFile {

    private static StringBuilder mHeader;
    private static StringBuilder mPrototypes;
    private static StringBuilder mData;
    private static StringBuilder mMain;
    private static StringBuilder mVariables;
    private static StringBuilder mStrings;
    private static StringBuilder mConstants;
    private static Hashtable<String, String> mRequiredProtos;
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
        mData = new StringBuilder();
        mPrototypes = new StringBuilder();
        mRequiredProtos = new Hashtable<>();
        mMain = new StringBuilder();
        mVariables = new StringBuilder();
        mStrings = new StringBuilder();
        mConstants = new StringBuilder();
        initHeader();
        initMain();
        setFile(mFileName);
    }

    /**
     * Prote strings should be in the following format: ProtoName PROTO
     * @param proto the prototype function to be called
     */
    public static void addProto(String proto){
        if(!mRequiredProtos.contains(proto)){
            mRequiredProtos.put(proto, proto);
            mPrototypes.append(proto);
        }
    }

    private static void initHeader(){
        addToHeader("INCLUDE C:\\masm32\\include\\masm32rt.inc\n");
        addToHeader("INCLUDE C:\\masm32\\include\\Irvine32.inc\n");
        addToHeader("INCLUDELIB C:\\masm32\\lib\\Irvine32.lib\n");
        addToHeader("INCLUDE C:\\masm32\\include\\debug.inc\n");
        addToHeader("INCLUDELIB C:\\masm32\\lib\\debug.lib\n");
        addToHeader("INCLUDE C:\\masm32\\naturalMacros\\naturalMacros.asm");
        addToHeader("\n\n\n.data\n\n");
    }

    private static void initMain(){
        mMain.append("\n\n.code\n\n");
        mMain.append("\tmain PROC\n\n");
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
        mMain.append("\n\n\tinkey\n");
        mMain.append("\tINVOKE ExitProcess, 0\n");
        mMain.append("\tmain ENDP\n");              // End the main procedure
        //mMain.append("END main\n\n");
        _writer.write(mHeader.toString());
        _writer.write(mData.toString());
        _writer.write(mVariables.toString());
        _writer.write(mConstants.toString());
        _writer.write(mStrings.toString());
        _writer.write("\n\n;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;\n");
        _writer.write(";; Function prototypes\n");
        _writer.write(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;\n");
        _writer.write(mPrototypes.toString());
        _writer.write(mMain.toString());
        _writer.write("\n\nEND main");
        _writer.close();
        mHeader = null;
        mData = null;
        mPrototypes = null;
        mVariables = null;
        mConstants = null;
        mStrings = null;
        mMain = null;
        Node.resetLabels();
        ASMGen.resetLabelCount();
    }
}
