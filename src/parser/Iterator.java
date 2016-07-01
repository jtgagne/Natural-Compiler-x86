package parser;

import inter.Id;
import lexer.Word;
import symbols.Type;

/**
 * Class to reference the previous identifier for declarations followed by assignents
 * Created by gagnej3 on 6/28/16.
 */
public class Iterator {

    static Id id;
    private static int initialValue;
    private static int maxValue;
    private static int opTag;
    private static int iterateBy;

    public Iterator(){

    }

    public Iterator(Word name, Type dataType){
        id = new Id(name, dataType, dataType.width);
    }

    public static Id getIdentifier() {
        return id;
    }

    public static void setIdentifier(Id iterator){
        id = iterator;
    }

    public static int getIterateBy() {
        return iterateBy;
    }

    public static void setIterateBy(int iterateBy) {
        Iterator.iterateBy = iterateBy;
    }

    public static int getOpTag() {
        return opTag;
    }

    public static void setOpTag(int opTag) {
        Iterator.opTag = opTag;
    }

    public static int getMaxValue() {
        return maxValue;
    }

    public static void setMaxValue(int maxValue) {
        Iterator.maxValue = maxValue;
    }

    public static int getInitialValue() {
        return initialValue;
    }

    public static void setInitialValue(int initialValue) {
        Iterator.initialValue = initialValue;
    }

}
