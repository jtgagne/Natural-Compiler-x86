package code_generation;

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
        addToHeader("\n\t.text\n\n");
        addToHeader("\n\t.globl main\n\n");
        addToMain("main:\n\n");
        addData("\t.data\n\n");
        setFile(mFileName);
    }

    private void setFile( String fileName){
        try{
            mFileName = fileName;
            String path = mOutputDirectory + "/" + mFileName;
            mFile = new File(path);
            _writer = new PrintWriter(mFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addData(String data){
        if(data == null) return;
        mData.append(data);
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
        mMain.append("\tli $v0, 10\t\t#Load system call to exit\n");
        mMain.append("\tsyscall\n\n");
        _writer.write(mHeader.toString());
        _writer.write(mMain.toString());
        _writer.write(mData.toString());
        _writer.close();
    }
}
