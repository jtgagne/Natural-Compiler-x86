package parser;
import java.io.*;
import java.util.ArrayList;

import code_generation.AssemblyFile;
import information.Printer;
import lexer.*;
import symbols.*;
import inter.*;

/**
 * Parser for natural
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Parser {

    private Lexer lex;          //Lexer to read in tokens
    private Token look;         //Next token from the Lexer
    Env top = null;             //Current or top symbol table
    int used = 0;               //Storage used for declarations

    private ArrayList<Stmt> _assignments = null;    //For assignments that occur during declaration
    private int _assignmentNum = 0;
    private int _count = 0;
    private String _line;

    /**
    * Sets the lexer (from the input_files parameter) and calls move to get the first token
    * @see Lexer
    * @throws IOException Compiler errors
    */
    public Parser() throws IOException {
        Stmt.Enclosing = Stmt.Null;
    }

    public void runParser() throws IOException{
        _assignments = new ArrayList<>(); // Tracks any assignments that occurred during declaration
        lex = Lexer.getInstance();
        move();
        program();
    }

    /**
     * Calls the scan method of the Lexer to get the next token and assigns the next
     * token to the class variable look.
     * @see Lexer
     * @throws IOException Error scanning in token from lexer.
     */
    private void move() throws IOException {
        look = lex.scan();
        if(look.tag != Tag.END){
            Printer.writeToken(look);
        }
    }


    /**
     * Throw the specific error
     * @param s the string with more details about the error
     */
   void error(String s) {
       throw new Error("near line " + Lexer.lineCount  +": " + s);
   }

    /**
    * Match the lookahead token returned by move() against what we are expecting
    * @param t Token that we expect to get next.
    * @throws IOException Mismatch between token expected and token found.
    */
    void match(int t) throws IOException
    {
      if( look.tag == t )
          move();
      else
          error("syntax error"); // call error method
    }


    /**
    * EBNF : program = block
    * Start symbol for the grammar
    * @throws IOException Error in program
    */
    public void program() throws IOException {

        /** block() returns node of type Stmt */
        //Stmt s = block();
        Env savedEnv = null;
        top = new Env(top);
        Stmt s = new Stmt();
        try{
            decls();
            savedEnv = top;
            s = stmts();
        }catch (Exception e){
            System.err.printf("Null");
        }
        int begin = s.newlabel();

        s.emitlabel(begin);
        s.gen(begin, begin);        //was: s.gen(begin, after);

        //int after = s.newlabel();
        //s.emitlabel(after);

        top = savedEnv;
        assert top != null;
        top.generateAsmData();                          //Add all variables in top to the data section
        AssemblyFile.addDataToFront("\n\t.data\n\n");   //Add '.data' to the beginning of the data string builder
    }


    /**
     * EBNF: block = { decls stmts }
     * @return Stmt root of syntax tree
     * @throws IOException Error somewhere below block
     */
    public Stmt block() throws IOException {

       match('{');

       Env savedEnv = top;
       top = new Env(top);

       decls();
       Stmt s = stmts();

       match('}');

       top = savedEnv;

       return s;
    }

    /**
    * EBNF: decls = type ID ; { type ID; }
    * @throws IOException Error somewhere below decls
    */
    public void decls() throws IOException {

        while(Tag.isDataType(look.tag)) {

            /** call type() */
            Type p = type();
            Id id = null;

            Token tok = look;
            if(check(Tag.ID)){
                /*Create node in syntax tree*/
                id = new Id((Word)tok, p, used);
                top.put( tok, id );
                used = used + p.width;
            }

            match(Tag.ID);

            if(check(Tag.ASSIGNMENT) || check(Tag.INCREASE) || check(Tag.DECREASE)){
                move();
                Stmt stmt;
                stmt = new Set(id,bool());
                _assignments.add(stmt);             //Add an assignment node to the ArrayList
            }
        }
    }


    private boolean check(int tag){
        if(look.tag == tag){
            return true;
        }
        return false;
    }

    /**
     * EBNF:  type = basic [ dims ]
     * @return
     * @throws IOException Error somewhere below type
     */
    Type type() throws IOException {
        Type t = (Type)look;

        if(Type.isType(look.tag)){
            move();
        } else {
            error("Not a valid type");
        }
            return t;

    }


    /**
     * EBNF stmts = stmt stmts
     * @return Stmt Seq object, which is of type Node, and holds a statement and then possibly a sequence of statements.
     * @throws IOException Error somewhere below stmts
     */
    public Stmt stmts() throws IOException
    {
        if(Tag.isDataType(look.tag)){
            decls();
        }

        if(_assignmentNum < _assignments.size()){           //Add nodes for all assignments that occurred during declarations
            Stmt stmt = _assignments.get(_assignmentNum);
            _assignmentNum++;
            if(_assignmentNum == _assignments.size()){
                _assignmentNum = 0;
                _assignments.clear();
            }
            return new Seq(stmt, stmts());
        }

        if (look.tag == '}' || look.tag == Tag.END ){
            return Stmt.Null;
        }
        else
            return new Seq(stmt(), stmts());
    }


    /**
     * BNF: Stmt =  L=E; | if(B) S | if(B) S else S | while(B) S
     *              | do S while (B); | break; | { DD SS }
     * @return Stmt A node for specific statement type that is parsed.
     * @throws IOException  Error below stmt
     */
   public Stmt stmt() throws IOException
   {
      Expr x;
      Stmt s, s1, s2;

       //For for loops
       Expr condition = null;
       Stmt assignment = null;
       Stmt update = null;
       Stmt loopThrough = null;

      /** save enclosing loop for breaks */
      Stmt savedStmt;

      switch( look.tag ) {

          case Tag.IF:
              move();
              match('(');
              x = bool();
              match(')');
              s1 = stmt();
              if( look.tag != Tag.ELSE )
                  return new If(x, s1);
              match(Tag.ELSE);
              s2 = stmt();
             return new Else(x, s1, s2);    // return an Else node

          case Tag.PRINT:
              PrintNode printNode = new PrintNode();
              move();
              match('(');                              //Match open paren
              if(check(Tag.ID)){                       //If a print tag was not found, check for an identifier.
                  Id id = top.get(look);
                  match(Tag.ID);
                  printNode.setIdentifier(id);
              }
              else{
                  Print print = (Print) look;
                  match(Tag.PRINT);
                  if(check(',')){                      //Allow for inclusion of a single variable in a print statement
                      move();
                      Id id = top.get(look);
                      match(Tag.ID);
                      printNode.setIdentifier(id);
                  }
                  printNode.setPrintToken(print);
              }
              match(')');                              //Check for open paren.
              return printNode;


          case  Tag.INPUT:
              move();
              match('(');
              Print prompt = (Print) look;
              match(Tag.PRINT);                 //prompt string to be printed to the user
              match(',');
              Id id = top.get(look);
              match(Tag.ID);
              match(')');
              return new InputNode(prompt, id);

          case Tag.WHILE:
             While whilenode = new While();
             savedStmt = Stmt.Enclosing;
             Stmt.Enclosing = whilenode;
             move();
             match('(');
             x = bool();
             match(')');
             s1 = stmt();
             whilenode.init(x, s1);
             Stmt.Enclosing = savedStmt;    // reset Stmt.Enclosing
             return whilenode;              // Return a While node

          case Tag.FOR:
              For fornode = new For();
              savedStmt = Stmt.Enclosing;
              Stmt.Enclosing = fornode;
              move();
              match('(');
              Env savedEnv = top;
              top = new Env(top);
              if(check(')')){
                  condition = new Expr(new Token(Tag.TRUE), Type.Bool);
                  loopThrough = stmt();
                  fornode.init(condition, assignment, update, loopThrough);
                  return fornode;
              }
              decls();
              if(_assignments.size() == 1){
                  assignment = _assignments.get(0);
                  _assignments.clear();
              }else{
                  assignment = assign();
              }
              match(';');
              condition = bool();
              match(';');
              update = assign();
              match(')');
              loopThrough = stmt();
              fornode.init(condition, assignment, update, loopThrough);
              top = savedEnv;
              Stmt.Enclosing = savedStmt;
              return fornode;

          case Tag.DO:
             Do donode = new Do();
             savedStmt = Stmt.Enclosing;
             Stmt.Enclosing = donode;
             move();
             s1 = stmt();
             match(Tag.WHILE);
             match('(');
             x = bool();
             match(')');
             donode.init(s1, x);
             Stmt.Enclosing = savedStmt;    // reset Stmt.Enclosing
             return donode;                 // Return a Do node

          case Tag.BREAK:
             match(Tag.BREAK);
             if(!check(';')){
                 error("missing semicolon after break statement");
             }
              move();
             return new Break();

          case '{':
             return block();

          default:
             return assign();               // Return either a Set or SetElem Node
      }
   }


   /**
    * EBNF: assign =  (id | L ) = bool;
    * @return Either a Set or SetElem (for array elements) Node
    * @throws IOException  Error below assign
    */
    public Stmt assign() throws IOException {
      Stmt stmt;
      Token t = look;

      match(Tag.ID);

      Id id = top.get(t);

      if( id == null )
          error(t.toString() + " undeclared");

      if( look.tag == Tag.ASSIGNMENT) {           // S -> id = E ;
         move();
         stmt = new Set(id, bool());    // Set node
      }

      else if(look.tag == Tag.INCREASE || look.tag == Tag.DECREASE){
          Token operator;
          if(look.tag == Tag.INCREASE){
              operator = new Token('+');
          } else{
              operator = new Token('-');
          }
          //Update statement
          move();                                       // Get next token
          Token num = look;                             // Access current token which should be a number
          match(Tag.NUM);                               // Match for a whole number after an increase by tag
          Id variable = id;     // New expression containing the previously found variable
          Constant number = new Constant(num, Type.Int);        // New expression containing the number that was found
          Arith arith = new Arith(operator, variable, number);           // New Arithmetic node: variable addition number
          stmt = new Set(id, arith);                    // id = id + number
      }

      else {                            // S -> L = E ;
          error("Syntax error, expected an identifier ");
          stmt = null;
      }

      //match(';');
      return stmt;
   }


   /**
     * Create node for logical OR operator
     *   EBNF bool = join { OR join }
     *   Lowest precedence (last to be calculated)
     * @return Or node
     * @throws IOException error below bool
     */
   public Expr bool() throws IOException {
      Expr n = join();
      while( look.tag == Tag.OR ) {
         Token tok = look;
         move();
         n = new Or(tok, n, join());    // OR node
      }
      return n;
   }


   /**
    * Create nodes for logical AND operator
    *   EBNF: join = equality { AND equality }
    * @return And node
    * @throws IOException  Error below join
    */
    public Expr join() throws IOException {
      Expr n = equality();
      while( look.tag == Tag.AND ) {
         Token tok = look;
         move();
         n = new And(tok, n, equality());
      }
      return n;
   }


   /**
    * Create nodes for equality operators (equal and not equal)
    * EBNF: equality =  rel {(equal to | Not equal to) rel}
    * @return Rel node or node return from rel()
    * @throws IOException Error below equality
    */
   public Expr equality() throws IOException {
      Expr n = rel();

      while( look.tag == Tag.EQ || look.tag == Tag.NE) {
          Token tok = look;
          move();
          n = new Rel(tok, n, rel());        // Rel node
      }
      return n;
   }


   /**
    * Create nodes for the relational operators
    * EBNF: rel = expr {(LT | LE | GE| GT) expr}
    * @return Rel node or node returned from expr
    * @throws IOException Error below rel
    */
   public Expr rel() throws IOException {
       Token tok;

       Expr n = expr();

       switch( look.tag ) {
          case Tag.LESS: case Tag.LE: case Tag.GE: case Tag.GREATER:
               tok = look;
               move();
               return new Rel(tok,n,expr());    // Rel node

        default:
           return n;
      }
   }


   /**
    * Parse and create a node for the arithmetic operators + and -
    * EBNF: expr = term {(+ | -) term}
    * @return Arith node or node returned from term()
    * @throws IOException Error below expr
    */
    public Expr expr() throws IOException {
        Expr n = term();
        Token tok;

        while( look.tag == '+' || look.tag == '-' || look.tag == Tag.INCREASE || look.tag == Tag.DECREASE) {

            if(look.tag == Tag.INCREASE){
                tok = new Token((int)'+');
            } else if(look.tag == Tag.DECREASE){
                tok = new Token((int)'-');
            } else{
                tok = look;
            }

            move();
            n = new Arith(tok, n, term());     // Arith node
      }

      return n;
   }




   /**
    * Parse and create a node for the arithmetic operators * and /
    * EBNF: term = unary {(* | /) unary}
    * @return Arith node or node returned from unary()
    * @throws IOException Error below term
    */
    public Expr term() throws IOException {
      Expr n = unary();
      while(look.tag == '*' || look.tag == '/' ) {
         Token tok = look;
         move();
         n = new Arith(tok, n, unary());    // Arith node
      }
      return n;
   }

   /**
    * Parse and create a node for the unary operators
    * EBNF: unary = {(- | !)} factor
    * @return Unary, Not nodes or node returned from factor()
    * @throws IOException Error below unary
    */
    public Expr unary() throws IOException {

      if( look.tag == Tag.MINUS) {
         move();
         return new Unary(Word.minus, unary());     // Return Unary node
      }
      else if( look.tag == Tag.NOT ) {
         Token tok = look;
         move();
         return new Not(tok, unary());              // Return Not node
      }
      else return factor();
   }

   /**
    * Parse for factor
    * EBNF: factor = (bool) | num | real | true | false | ID { "[" bool "]" }
    * @return Return node based on the type of factor that we have
    * @throws IOException Error below factor
    */
    public Expr factor() throws IOException {
        Expr n = null;

        switch( look.tag ) {

            case '(':
                move();
                n = bool();                              // Subexpression
                match(')');
                return n;

            case Tag.BASIC:
                n = new Constant(look, Type.Char);
                move();
                return n;

            case Tag.CHAR:
                n = new Constant(look, Type.Char);
                move();
                return n;

            case Tag.NUM:
                n = new Constant(look, Type.Int);              // Return Constant node
                move();
                return n;

            case Tag.REAL:
                n = new Constant(look, Type.Float);      // Return Constant node
                move();
                return n;

            case Tag.TRUE:
                n = Constant.True;                       // Return Constant node
                move();
                return n;

            case Tag.FALSE:
                n = Constant.False;
                move();
                return n;                                // Return Constant node

            case Tag.ID:
                String s = look.toString();
                Id id = top.get(look);                   // Lookup in symbol table

                if( id == null )                         // Not found...
                    error(s + " undeclared");

                move();
                return id;              // Return Id node

            default:
                error("syntax error");
                return n;

      }
   }
}
