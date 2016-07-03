package parser;
import java.io.*;
import lexer.*;
import symbols.*;
import inter.*;

/**
 * Parses an input program in language based on from Appendix A in the
 * dragon book.
 *
 *
 * AUTHORS: Justin Gagne and Zack Farrer
 */
public class Parser {

    /** lexical analyzer for this parser */
    private static Lexer lex;

    //For loop iterator
    private static Id _forId;

    /** lookahead  */
    private static Token look;

    /** current or top symbol table */
    Env top = null;

    /** storage used for declarations */
    int used = 0;

    /**
    * Sets the lexer (from the input parameter) and calls move to get the first token
    * @see Lexer
    * @throws IOException Compiler errors
    */
    public Parser() throws IOException {

    }

    public void runParser() throws IOException{
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
    private static void move() throws IOException {
        look = Lexer.getInstance().scan();
    }


    /**
     * Throw the specific error
     * @param s the string with more details about the error
     */
   void error(String s) {
       throw new Error("near lineCount "+Lexer.lineCount +": "+s);
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
            //move();
            s = stmts();
        }catch (Exception e){
            System.err.printf("Null");
        }
        //s.gen(1,2);
        top = savedEnv;
        /*
        int begin = s.newlabel();
        int after = s.newlabel();
        s.emitlabel(begin);
        s.gen(begin, after);
        s.emitlabel(after);
        */

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
        //move();
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

        while(Tag.isDataType(look.tag) && !Lexer.getInstance().isLastLine()) {

            /** call type() */
            Type p = type();

            Token tok = look;
            match(Tag.ID);
            //move();

            /*Create node in syntax tree*/
            Id id = new Id((Word)tok, p, used);
            top.put( tok, id );
            used = used + p.width;

        }
    }


    /**
     * Returns an assignment node for a for loop, tested and works
     * @return assignment node for the for loop iterator
     * @throws IOException
     */
    public Stmt fordecls() throws IOException{
        Stmt stmt;
        Type p = null;
        Token tok = look;                   //Get the current token

        if(!check(Tag.INT)){                //Check for declaration
            if(check(Tag.ID)){
                p  = top.get(tok).type;     //Set the type of the identifier
            }
            match(Tag.ID);
        }

        else {                            //Type declaration was made
            p = type();                     //Set the type
            match(Tag.ID);                  //Match for identifier
        }

        if(p == null){
            error("For loop iterator was not initialized near line: " + Lexer.lineCount);
        }

        //Set the _forId iterator variable to be accessed when making the other expressions
        _forId = new Id((Word)tok, p, used);

        top.put(tok, _forId);     //Add the iterator to the top enviornment
        match(Tag.ASSIGNMENT);  //Ensure assignment operator
        if(!check(Tag.NUM)){    //Check for a number
            error("Expected an assignment value near line: " + Lexer.lineCount);
        }

        stmt = new Set(_forId, factor());    // Set the iterator and the number

        return stmt;
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
      if ( look.tag == '}' || look.tag == Tag.END)
          return Stmt.Null;
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
          assignment = fordecls();   //Assignment node
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
         //match(Tag.DO);
         s1 = stmt();
         match(Tag.WHILE);
         match('(');
         x = bool();
         match(')');
         //match(';');
         donode.init(s1, x);
         Stmt.Enclosing = savedStmt;    // reset Stmt.Enclosing
         return donode;                 // Return a Do node

      case Tag.BREAK:
         match(Tag.BREAK);
         match(';');
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

      else if(look.tag == Tag.INCREASE){
          //Update statement
          move();                                       // Get next token
          Token addition = new Token('+');              // Create an addition token corresponding to increase tag
          Token num = look;                             // Access current token which should be a number
          match(Tag.NUM);                               // Match for a whole number after an increase by tag
          Expr variable = new Expr(id.op, id.type);     // New expression containing the previously found variable
          Expr number = new Expr(num, Type.Int);        // New expression containing the number that was found
          Arith arith = new Arith(addition, variable, number);           // New Arithmetic node: variable addition number
          stmt = new Set(id, arith);                    // id = id + number
      }

      else {                            // S -> L = E ;
          error("Syntax error");
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
            }

            else if(look.tag == Tag.DECREASE){
                tok = new Token((int)'-');
            }

            else{
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

            case Tag.NUM:
                n = new Constant(look, Type.Int);        // Return Constant node
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

            case Tag.TO:
                move();
                if(!check(Tag.NUM)){
                    error("Missing to statement near line: " + Lexer.lineCount);
                }
                n = new Rel(new Token(Tag.TO), _forId, factor());
                return n;

            default:
                error("syntax error");
                return n;

            case Tag.ID:
                String s = look.toString();

                Id id = top.get(look);                   // Lookup in symbol table

                if( id == null )                         // Not found...
                error(s + " undeclared");

                move();
                return id;              // Return Id node


          /**
           *        !!!!!   OLD ARRAY STUFF, REMOVE ONCE DOESN'T BREAK THINGS   !!!!!
           *
           * //if( look.tag != '[' )
                //else
           //       return offset(id);     // Return Access node
           *
           *
           */

      }
   }




    /**
     * ====================================================================================
     * !!!!!         ARRAY STUFF, REMOVE ONCE KNOWN TO BE SAFE          !!!!!
     *
     * ------------------------------------------------------------------------------------
     */


//   /**
//    * Create a node for accessing an array element (id,subtree for calculating location, and base type of elements)
//    * EBNF:  offset =  "[" bool "]" { "[" bool "]" }
//    * @param id Object of type Id
//    * @return Access Node for accessing an array element (id,subtree for calculating location,and base type of elements)
//    * @throws IOException Error creating a node for array element access
//    */
//    public Access offset(Id id) throws IOException {
//
//      Expr iExpr;                                   // Node (expr) for index
//      Expr wExpr;                                   // Node (expr) for width
//      Expr t1, t2;
//      Expr loc;
//
//      Type type = id.type;
//      match('[');                                   // first index, I -> [ E ]
//      iExpr = bool();                               // Expression for index
//      match(']');
//
//      type = ((Array)type).of;                      // Get type for array elements (may be an array)
//      wExpr = new Constant(type.width);             // width of base type
//      t1 = new Arith(new Token('*'), iExpr, wExpr); // Node for calculation of offset in array (number x width)
//                                                    // of array element
//      loc = t1;
//
//      while( look.tag == '[' ) {                    // multi-dimensional I -> [E] I
//         match('[');
//         iExpr = bool();                            // Expression for next index
//         match(']');
//         type = ((Array)type).of;                   // Get base type for array elements (may be an array)
//         wExpr = new Constant(type.width);          // Get width
//         t1 = new Arith(new Token('*'),iExpr,wExpr); // Node for calculation of offset in array (number x width)
//         t2 = new Arith(new Token('+'),loc,t1);     // Node to add offset for this dimension to running offset
//         loc = t2;
//      }
//
//      return new Access(id, loc, type);             // Node for accessing an array element (id,subtree for calculating
//                                                    // location, and base type of elements)
//   }
}
