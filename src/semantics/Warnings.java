package semantics;

import information.Printer;
import lexer.Lexer;

/**
 * Print and then write warnings encountered by the parser.
 * Created by gagnej3 on 7/13/16.
 */
public class Warnings {

    /**
     * @param fromType initial data type of something
     * @param toType the data type being converted to.
     */
    public static void narrowing(String fromType, String toType){
        Printer.printFileName();
        System.err.printf("Type Warning: converting %s to %s may result in data loss. Near line: %d\n", fromType, toType, Lexer.lineCount);
        Printer.writeNarrowing(fromType, toType);
    }

    /**
     * @param initialType initial data type of something
     * @param castTo the data type being converted to.
     */
    public static void widening(String initialType, String castTo){
        Printer.printFileName();
        System.err.printf("Type Warning: converting %s to %s may result in extra memory allocation. Near line: %d\n", initialType, castTo, Lexer.lineCount);
        Printer.writeWidening(initialType, castTo);
    }


    /**
     * Converting a floating point value to an int
     * @param fromType initial data type of something
     * @param toType the data type being converted to.
     */
    public static void floatingToWhole(String fromType, String toType){
        Printer.printFileName();
        System.err.printf("Type Warning: converting floating point: %s to whole value: %s may result in data loss. Near line: %d\n", fromType, toType, Lexer.lineCount);
        Printer.writeFloatingToWhole(fromType, toType);
    }

    public static void incompatibleTypes(String fromType, String toType){
        Printer.printFileName();
        System.err.printf("Type Warning: inconvertible types: can't convert %s to %s Near line: %d\n", fromType, toType, Lexer.lineCount);
        Printer.writeIncompatibleTypes(fromType, toType);
    }


}
