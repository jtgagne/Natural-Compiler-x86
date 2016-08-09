package lexer;

import information.Printer;
import symbols.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;


/**
 * Scan in tokens for Natural
 * Created by Justin Gagne on 6/5/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Lexer {
    private static Lexer _lexer;                        // Current instance of the lexer
    public static int lineCount = 1;                    // Line count of the code
    char peek = ' ';                                    // Peek at next character

    public Hashtable words;    // Reserved,identifiers, multi-symbol operators
    public Hashtable phrases;  // Hash Table for keeping track of phrases / word groupings
    public ArrayList<Word> _phrase;                     // Multiple words entered that should be grouped
    private static BufferedReader _reader;              // Reader for getting File IO
    private static String _line;                        // Current line being scanned by the lexer
    private static String _nextline;                    // Next line of the file, null if at the end of the file
    private static int _location = 0;                   // Current char index of _line

    private static boolean lastLine = false;           // Used to know if the line being read is the final line in the program
    private static boolean hasNextLine = true;          // Check if the file has more lines to be scanned
    private static boolean isMakingPhrase = false;      // Track if a _phrase has been input_files appropriately
    private static boolean isMultiLineComment = false;  // Ignore input_files while true.
    private static ArrayList<String> _identifiers;
    private boolean isPrintReady = false;

    private Lexer(){
        words = new Hashtable();
        phrases = new Hashtable();
        Word.reserveWords(words);       //Reserve control statements, logical statements etc.
        Type.reserveTypes(words);       //Reserve data types.
        Phrase.reservePhrases(phrases); //Reserve the phrases.
        _phrase = new ArrayList<>();    //Initialize the input_files array to keep track of potential phrases
    }

    public static Lexer getInstance(){
        if(_lexer == null){
            _lexer = new Lexer();
        }
        return _lexer;
    }


    public void openReader(String file) throws IOException{
        Reader reader = new InputStreamReader(new FileInputStream(new File(file)));
        _reader = new BufferedReader(reader);
        _location = 0;
        _line = null;
        _nextline = null;
        _identifiers = new ArrayList<>();
        lineCount = 0;
        hasNextLine = true;
    }

    public void closeReader() throws IOException{
        _reader.close();
        _lexer = null;
    }

    public String readPrintContent() throws IOException{

        StringBuilder sb = new StringBuilder();

        readch();

        do{
            sb.append(peek);
            readch();
        }
        while (peek != '"');

        readch();

        return sb.toString();
    }

    // ******************************************************
    // Read from a file to be evaluated by the lexer. Read by lineCount
    // and then by char
    // ******************************************************
    void readch() throws IOException {
        //peek = (char) System.in.read();

        //If the line is null or we have arrived at the last index, update
        if( _line == null || _location == _line.length()){

            //Initial condition, set values for line and next line if we are at the first line in the file
            if(lineCount == 0){
                _line = _reader.readLine();
                _nextline = _reader.readLine();
                isPrintReady = true;
            }

            //If the Lexer has passed the first line of the file, update the values accordingly
            else {
                _line = _nextline;
                _nextline = _reader.readLine();
                _location = 0;
                isPrintReady = true;
            }
            if(hasNextLine == false){
                lastLine = true;
            }
            //The end of file is near
            if(_nextline == null){
                hasNextLine = false;
            }
        }

        //Get the next character
        if(_line != null) {
            if(_line.length()==0) {
                readch();
            }else if (_location < _line.length()) {
                peek = _line.charAt(_location);
                _location++;
            }
        }else{
            peek = Tag.END;
        }
    }

    public String getLine(){
        return _line;
    }

    boolean readch(char c) throws IOException {
        readch();
        if( peek != c ) {
            return false;
        }
        peek = ' ';
        return true;
    }

    /**
     * Since single line comments only ignore the remaining information on the current line, we can just
     * Skip everything after the comment symbol
     */
    public void skipComment() throws IOException{
        //Skip all tokens until new line
        _location = _line.length();

        //Read the next char
        readch();
        if(peek == '#'){
            if(readch('#')) {
                isMultiLineComment = !isMultiLineComment;                   //Switch the state of the boolean
                if (isMultiLineComment) {
                    skipMultiLineComment();                                 //Read until the end of a multiline comment
                }
            }else{
                skipComment();
            }
        }
    }

    // ******************************************************
    // Recognizes numbers, identifiers, reserved words, operators
    // and return associated Token to parser.
    // ******************************************************
    public Token scan() throws IOException
    {
        // ******************************************************
        // Eat whitespace
        // ******************************************************
        for( ; ; readch() ) {

            if(peek == '\n' ){
                lineCount = lineCount + 1;
                _location = 0;
                //return new Token('\n');
            }

            else if ( peek != ' ' && peek != '\t' )
                break;
        }

        if(isPrintReady){
            Printer.writeLexerLine(_line, lineCount);
            isPrintReady = false;
            lineCount++;
        }


        if(peek == '#'){
            if(readch('#')) {
                isMultiLineComment = !isMultiLineComment;                   //Switch the state of the boolean
                if(isMultiLineComment){
                    skipMultiLineComment();                                 //Read until the end of a multiline comment
                }
            } else{
                skipComment();                                              //Skip the rest of the line
            }
        }

        // ******************************************************
        // OPERATOR
        // ******************************************************
        switch( peek ) {
            case '"':
                return new Print(readPrintContent());
            case '-':
                if(!readch(' '))return Word.minus; else return new Token('-');
            case '&':
                if( readch('&') ) return Word.and;  else return new Token('&');
            case '|':
                if( readch('|') ) return Word.or;   else return new Token('|');
            case '=':
                if( readch('=') ) return Word.eq;   else return new Token('=');
            case '!':
                if( readch('=') ) return Word.ne;   else return new Token(Tag.NOT);
            case '<':
                if( readch('=') ) return Word.le;   else return new Token(Tag.LESS);
            case '>':
                if( readch('=') ) return Word.ge;   else return new Token(Tag.GREATER);
            case ':':
                if( readch('=') ) return Word.assign;   else return Word.error;
            case '\'':
                readch(); char c = peek; if(readch('\'')) return new Char(c); else return Word.error;
        }

        // ******************************************************
        // Numbers (Num or Real types)
        // ******************************************************
        if( Character.isDigit(peek) ) {
            int v = 0;
            do {
                v = 10*v + Character.digit(peek, 10);
                readch();
            } while( Character.isDigit(peek) );

            if( peek != '.' )
                return new Num(v);
            float x = v;
            float d = 10;

            for(;;) {
                readch();
                if( ! Character.isDigit(peek) || peek == Tag.END)
                    break;
                x = x + Character.digit(peek, 10) / d;
                d = d*10;
            }
            return new Real(x);
        }

        // ******************************************************
        // Identifiers
        // ******************************************************
        if( Character.isLetter(peek) ) {

            StringBuilder b = new StringBuilder();

            do {

                b.append(peek);

                //If we have already reached the last accessible index in the line it needs to break
                if(_location == _line.length() ){

                    //if next line is not null set peek to the next char in the new line
                    if(_nextline != null){
                        readch();
                    }else{
                        peek = Tag.END;
                    }
                    break;
                }
                readch();
            } while( Character.isLetterOrDigit(peek) && peek != '\n' && peek != '\t' && peek != ' ');

            String s = b.toString();

            Token token = (Token) words.get(s);                   //check the word against the hash table

            if(token != null && token.tag2 == Tag.INITIALIZER){                 //Read the phrase and return the phrase
                return readPhrase(token);
            }

            Word word = (Word) token;

            //hash table WILL return null if the lexeme does not exist
            if(word != null){
                if(word.tag == Tag.ID){
                    _identifiers.add(word.lexeme);
                }
                return word;
            }

            word = new Word(s, Tag.ID);
            words.put(s, word);

            _identifiers.add(word.lexeme);

            return word;
        }

        // Unidentified tokens
        Token tok = new Token(peek);
        if(peek != Tag.END) {
            peek = ' ';
        }

        return tok;
    }

    /**
     * Read the rest of a phrase and return the phrase value. Gets called when a phrase initializer token is found.
     * @return a phrase token.
     */
    private Token readPhrase(Token initialWord) throws IOException{
        Word initializer = null;
        Phrase phrase;

        if(initialWord.isWord()){ initializer = (Word) initialWord; }
        if(initializer == null){ phraseError();}

        StringBuilder builder = new StringBuilder();
        builder.append(initializer.lexeme);             //Add the initializer lexeme
        phrase = (Phrase) phrases.get(builder.toString().trim());

        if(phrase != null && phrase == Phrase.not){
            builder = makeLongPhrase(builder);
            phrase = (Phrase)phrases.get(builder.toString());
            return phrase;
        }

        builder.append(' ');                            //Add a space.
        //Read the next char.
        readch();

        if(peek == Tag.END){ phraseError(); }

        do {
            builder.append(peek);
            if(phrases.get(builder.toString()) != null){                                            //A phrase was found, it should break.
                phrase = (Phrase) phrases.get(builder.toString().trim());
                if(phrase == Phrase.lt || phrase == Phrase.gt || phrase == Phrase.not){             //Less than | greater than. check for "or equal to"
                    builder = makeLongPhrase(builder);
                }
                readch();
                break;
            }
            if(_location == _line.length()){             // Already reached the last accessible index, break
                if(_nextline != null){                   // Update peek if there is another line
                    readch();
                } else { peek = Tag.END; }
                break;
            }
            readch();

        } while(Character.isLetterOrDigit(peek) && peek != '\n' && peek != '\t' && peek != ' ');

        //Check for correct phrase.
        phrase = (Phrase) phrases.get(builder.toString());

        if(phrase == null){
            throw new Error("Incorrect phrase near line: " + Lexer.lineCount);
        }
        return phrase;
    }

    private StringBuilder makeLongPhrase(StringBuilder builder) throws IOException{

        String readAhead = _line.substring(_location);          // Read ahead of current location
        int offset = readAhead.length();                        // Offset to keep track of potential white space characters
        readAhead = readAhead.trim();
        offset = offset - readAhead.length();                   // Set the offset value after trimming

        String orEq = "or equal to";
        String eq = "equal to";
        int len;                                                // Length of the string being checked for

        if(readAhead.contains(orEq)){
            len = orEq.length();
        } else if(readAhead.contains(eq)){
            len = eq.length();
        } else{
            return builder;
        }

        readAhead = readAhead.substring(0, len);                // Find if the string has the desired substring

        if(!readAhead.contains(orEq) && !readAhead.contains(eq)){
            return builder;
        }

        builder.append(" ");
        builder.append(readAhead);
        _location = _location + len + offset;

        return builder;
    }

    /**
     * Scan until it is no longer a multiline comment
     * @throws IOException
     */
    private void skipMultiLineComment() throws IOException{
        while(isMultiLineComment){
            readch();
            if(peek == '#'){
                if(readch('#')){
                    isMultiLineComment = false;
                }
            }
        }
        readch();
    }

    public static boolean isMakingPhrase(){
        return isMakingPhrase;
    }


    // ******************************************************
    // MAIN : Test our lexer before the parser is written.
    // ******************************************************
    public static void main(String[] args) throws IOException {
        Lexer lex = new Lexer();
        while (true){
            Token token = lex.scan();
            if(token != null){
                System.out.printf("%s\n", token);
            }
        }
    }

    public static ArrayList<String> getIdentifiers(){
        return _identifiers;
    }

    public static void clearIdentifiers(){
        _identifiers.clear();
    }

    public static void phraseError(){
        throw new Error("Incorrect phrase near line: " + Lexer.lineCount);
    }
}