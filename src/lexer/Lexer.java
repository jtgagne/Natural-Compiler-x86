package lexer;
//TODO: MATCH FIRST
/**
 * Provides command lineCount application for testing reserved words for the lexer of Natural
 * Created by Justin Gagne on 6/5/16.
 *
 */

import symbols.Type;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;


// ******************************************************
// Read in next lexeme and return it's associated token
// ******************************************************
public class Lexer {
    private static Lexer _lexer;                        // Current instance of the lexer
    public static int lineCount = 1;                    // Line count of the code
    char peek = ' ';                                    // Peek at next character

    public static Hashtable words = new Hashtable();    // Reserved,identifiers, multi-symbol operators
    public static Hashtable phrases = new Hashtable();  // Hash Table for keeping track of phrases / word groupings
    public ArrayList<Word> _phrase;                     // Multiple words entered that should be grouped
    private static BufferedReader _reader;              // Reader for getting File IO
    private static String _line;                        // Current line being scanned by the lexer
    private static String _nextline;                    // Next line of the file, null if at the end of the file
    private static int _location = 0;                   // Current char index of _line

    private static boolean lastLine = false;           // Used to know if the line being read is the final line in the program
    private static boolean hasNextLine = true;          // Check if the file has more lines to be scanned
    private static boolean isMakingPhrase = false;      // Track if a _phrase has been input appropriately
    private static boolean isMultiLineComment = false;  // Ignore input while true.
    private static boolean isComment = false;




    public static Lexer getInstance(){
        if(_lexer == null){
            _lexer = new Lexer();
        }
        return _lexer;
    }

    // ******************************************************
    //Add reserve words to HashTable
    // ******************************************************
    private Lexer() {
        Word.reserveWords(words);       //Reserve control statements, logical statements etc.
        Type.reserveTypes(words);       //Reserve data types.
        Phrase.reservePhrases(phrases); //Reserve the phrases.
        _phrase = new ArrayList<>();    //Initialize the input array to keep track of potential phrases
    }

    public void openReader(String file) throws IOException{
        Reader reader = new InputStreamReader(new FileInputStream(new File(file)));
        _reader = new BufferedReader(reader);
        _location = 0;
        _line = null;
        _nextline = null;
        lineCount = 0;
        hasNextLine = true;
    }

    public void closeReader() throws IOException{
        _reader.close();
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
                lineCount++;
            }

            //If the Lexer has passed the first line of the file, update the values accordingly
            else {
                _line = _nextline;
                _nextline = _reader.readLine();
                _location = 0;
                lineCount++;
                isComment = false;
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
        if(_location < _line.length()){
            peek = _line.charAt(_location);
            _location++;
        }
    }

    public String getLine(){
        return _line;
    }

    public boolean hasNext(){
        return hasNextLine;
    }

    public boolean isLastLine() {
        return lastLine;
    }
    // ******************************************************
    //
    // ******************************************************
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
                isComment = false;              //Single line comments are done at a new line
                //return new Token('\n');
            }

            else if ( peek != ' ' && peek != '\t' )
                break;
        }


        if(peek == '#'){
            if(readch('#')) {
                isMultiLineComment = !isMultiLineComment;                   //Switch the state of the boolean
                if(isMultiLineComment){
                    skipMultiLineComment();                                 //Read until the end of a multiline comment
                }
            } else{
                isComment = true;
                skipComment();                                              //Skip the rest of the line
            }
        }

        // ******************************************************
        // OPERATOR
        // ******************************************************
        switch( peek ) {
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
                if( ! Character.isDigit(peek) )
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

            Word w = (Word) words.get(s);                   //check the word against the hash table

            try{

                if(w.tag2 != Tag.NULL){                     //If tag 2 is not NULL, the word is part of a phrase
                    w = concatPhrases(w);                   //Try to make a phrase from the previous words
                    if(w == null){                          //A phrase is being made, don't print until complete
                        return w;
                    }
                }

            } catch (Exception e){
                System.out.printf("");
            }


            if( w != null )
                return w;

            w = new Word(s, Tag.ID);
            words.put(s, w);

            return w;
        }

        // Unidentified tokens
        Token tok = new Token(peek);
        if(peek != Tag.END) {
            peek = ' ';
        }

        return tok;
    }

    public boolean isNewLine(){
        return peek =='\n';
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


    /**
     * Keep track of words that are keywords of compound phrases. Once a terminal word is reached, the group
     * of words should be concatenated into a single Word object and output to the console. If the phrase entered is
     * not in correct form, this should be printed to the console as well.
     * @param word a Word object to be added to the phrase
     * @return null if a TERMINAL tag has not yet been reached, a Word object with the phrase otherwise
     */
    public Word concatPhrases(Word word) throws IOException{

        //Once a terminal is reached, check the phrases hash table for correctness.
        if(word.tag2 == Tag.TERMINAL){
            _phrase.add(word);
            String checkPhrase = "";

            //Get all strings from the words to make a _phrase
            for(Word w : _phrase){
                checkPhrase += w.lexeme + " ";
            }

            checkPhrase = checkPhrase.trim();

            _phrase.clear();        //Reset the phrase input

            isMakingPhrase = false;

            try{

                Phrase phrase = (Phrase) phrases.get(checkPhrase);
                return new Word(phrase.getLexeme(), phrase.getTag());

            }catch (Exception e){
                return new Word("Incorrect Phrase Format", Tag.ERROR);
            }
        }

        //If the word is an initializer that doesn't follow another initializer
        else if(word.tag2 == Tag.INITIALIZER ){
            _phrase.add(word);
            isMakingPhrase = true; //User started _phrase with a valid keyword
        }

        //If the first value of a _phrase is not an initializer, return an error
        else if(!isMakingPhrase){
            return new Word("Incorrect Phrase Format", Tag.ERROR);
        }


        return null;
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

}
