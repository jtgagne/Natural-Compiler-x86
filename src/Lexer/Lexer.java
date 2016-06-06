package Lexer;

/**
 * Provides command line application for testing reserved words for the lexer of Natural
 * Created by Justin Gagne on 6/5/16.
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;



// ******************************************************
// Read in next lexeme and return it's associated token
// ******************************************************
public class Lexer {
    public static int line = 1;         // Line of code
    char peek = ' ';                    // Peek at next character

    public static Hashtable words = new Hashtable();    // Reserved,identifiers, multi-symbol operators
    public static Hashtable phrases = new Hashtable();  // Hash Table for keeping track of phrases / word groupings
    public ArrayList<Word> _phrase;                     // Multiple words entered that should be grouped
    public static boolean isValidPhrase = false;        // Track if a _phrase has been input appropriately


    // ******************************************************
    //Add reserve words to HashTable
    // ******************************************************
    public Lexer() {
        Word.reserveWords(words);       //Reserve control statements, logical statements etc.
        Type.reserveTypes(words);       //Reserve data types.
        Phrase.reservePhrases(phrases); //Reserve the phrases.

        _phrase = new ArrayList<>();    //Initialize the input array to keep track of potential phrases
    }

    // ******************************************************
    // ******************************************************
    void readch() throws IOException {
        peek = (char)System.in.read();
    }

    // ******************************************************
    // ******************************************************
    boolean readch(char c) throws IOException {
        readch();
        if( peek != c ) return false;
        peek = ' ';
        return true;
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
            if( peek == '\n' )
                line = line + 1;
            else if ( peek != ' ' && peek != '\t' )
                break;
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
                if( readch('=') ) return Word.ne;   else return new Token('!');
            case '<':
                if( readch('=') ) return Word.le;   else return new Token('<');
            case '>':
                if( readch('=') ) return Word.ge;   else return new Token('>');
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
                readch();
            } while( Character.isLetterOrDigit(peek) );

            String s = b.toString();

            Word w = (Word) words.get(s); //check the word against the hash table

            try{
                //If tag 2 is not NULL, the word is part of a phrase
                if(w.tag2 != Tag.NULL){
                    w = concatPhrases(w); //Try to make a phrase from the previous words

                    //A phrase is being made, don't print until complete
                    if(w == null){
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
        peek = ' ';

        return tok;
    }


    /**
     * Keep track of words that are keywords of compound phrases. Once a terminal word is reached, the group
     * of words should be concatenated into a single word and output to the console. If the phrase entered is
     * not in correct form, this should be printed to the console as well.
     * @param word
     * @return
     */
    public Word concatPhrases(Word word){

        //Once a terminal is reached, check the phrases hash table for correctness.
        if(word.tag2 == Tag.TERMINAL){
            _phrase.add(word);
            String checkPhrase = "";

            //Get all strings from the words to make a _phrase
            for(Word w : _phrase){
                checkPhrase += w.lexeme + " ";
            }

            checkPhrase = checkPhrase.trim();

            isValidPhrase = false;  //reset the valid phrase variable.
            _phrase.clear();        //Reset the phrase input

            try{
                Phrase phrase = (Phrase) phrases.get(checkPhrase);
                return new Word(phrase.getLexeme(), phrase.getTag());

            }catch (Exception e){
                return new Word("Incorrect Phrase Format", Tag.ERROR);
            }
        }

        //If the word is an initializer that doesn't follow another initializer
        else if(word.tag2 == Tag.INITIALIZER && !isValidPhrase){
            _phrase.add(word);
            isValidPhrase = true; //User started _phrase with a valid keyword
        }

        //If the first value of a _phrase is not an initializer, return an error
        else if(!isValidPhrase){
            return new Word("Incorrect Phrase Format", Tag.ERROR);
        }

        //Can have "PHRASE" values given the _phrase was initialized properly
        else if( word.tag2 == Tag.PHRASE){
            _phrase.add(word);
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
