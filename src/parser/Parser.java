package parser;
import java.io.*; 
import lexer.*; 
import symbols.*; 
import inter.*;

/**
 * Parses an input program in language based on from Appendix A in the
 * dragon book. 
 * 
 */
public class Parser {

    /** lexical analyzer for this parser */
   private static Lexer lex;
   
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
        look = lex.scan();
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
        Env savedEnv = top;
        top = new Env(top);
        Stmt s = new Stmt();
        try{
            decls();
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

        while(look.tag == Tag.BASIC && !Lexer.getInstance().isLastLine()) {
            
            /** call type() */
            Type p = type();

            //Move the lexer to check for an identifier
            //move();
            Token tok = look;

            if(check(Tag.ID)) {
                move();
                if (check(Tag.ASSIGNMENT)) {

                }
            }

            /*Create node in syntax tree*/
            Id id = new Id((Word)tok, p, used);
            top.put( tok, id );
            used = used + p.width;

            //Move the lexer to continue looking
            //move();
      }
    move();
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
        match(Tag.BASIC);

        /** basic type or array type */
        if( look.tag != '[' )
            return t;                  
        else 
            return dims(t);            
    }

    
    /**
     * EBNF: dims = "[" num "]" dims
     * @param t Basic type for the array. 
     * @return Type Array of either a base type or an array
     * @throws IOException  Error somewhere below dims
     */
    public Type dims(Type t) throws IOException 
    {
        match('[');  
        Token tok = look;
        match(Tag.NUM);  
        match(']');

        if( look.tag == '[' )
            t = dims(t);
        
        return new Array(((Num)tok).value, t); // t could be basic type or array, itself...
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
      
      /** save enclosing loop for breaks */
      Stmt savedStmt;         

      switch( look.tag ) {

      case ';':
         move();
         return Stmt.Null;

      case Tag.IF:
         x = bool();
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
         match('('); 
         x = bool(); 
         match(')');
         s1 = stmt();
         whilenode.init(x, s1);
         Stmt.Enclosing = savedStmt;    // reset Stmt.Enclosing
         return whilenode;              // Return a While node

      case Tag.DO:
         Do donode = new Do();
         savedStmt = Stmt.Enclosing; 
         Stmt.Enclosing = donode;
         match(Tag.DO);
         s1 = stmt();
         match(Tag.WHILE); 
         match('('); 
         x = bool(); 
         match(')'); 
         match(';');
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
      else {                            // S -> L = E ;
         Access x = offset(id);
         match('=');  
         stmt = new SetElem(x, bool()); // SetElem node
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
      
      while( look.tag == Tag.EQ || look.tag == Tag.NE ) {
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
      Expr n = expr();
      
      switch( look.tag ) {
        case '<': case Tag.LE: case Tag.GE: case '>':
           Token tok = look;
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
      
      while( look.tag == '+' || look.tag == '-' ) {
         Token tok = look;
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

        default:
           error("syntax error");
           return n;

        case Tag.ID:
           String s = look.toString();
           
           Id id = top.get(look);                   // Lookup in symbol table
           
           if( id == null )                         // Not found...
               error(s + " undeclared");
           
           move();
           if( look.tag != '[' )
               return id;                           // Return Id node
           else 
               return offset(id);                   // Return Access node
      }
   }

   /**
    * Create a node for accessing an array element (id,subtree for calculating location, and base type of elements)
    * EBNF:  offset =  "[" bool "]" { "[" bool "]" }
    * @param id Object of type Id
    * @return Access Node for accessing an array element (id,subtree for calculating location,and base type of elements)
    * @throws IOException Error creating a node for array element access
    */
    public Access offset(Id id) throws IOException {  
       
      Expr iExpr;                                   // Node (expr) for index
      Expr wExpr;                                   // Node (expr) for width
      Expr t1, t2; 
      Expr loc;  

      Type type = id.type;
      match('[');                                   // first index, I -> [ E ]
      iExpr = bool();                               // Expression for index
      match(']');     
      
      type = ((Array)type).of;                      // Get type for array elements (may be an array)
      wExpr = new Constant(type.width);             // width of base type
      t1 = new Arith(new Token('*'), iExpr, wExpr); // Node for calculation of offset in array (number x width)
                                                    // of array element
      loc = t1;
      
      while( look.tag == '[' ) {                    // multi-dimensional I -> [E] I
         match('['); 
         iExpr = bool();                            // Expression for next index
         match(']');
         type = ((Array)type).of;                   // Get base type for array elements (may be an array)
         wExpr = new Constant(type.width);          // Get width
         t1 = new Arith(new Token('*'),iExpr,wExpr); // Node for calculation of offset in array (number x width)
         t2 = new Arith(new Token('+'),loc,t1);     // Node to add offset for this dimension to running offset
         loc = t2;                                  
      }

      return new Access(id, loc, type);             // Node for accessing an array element (id,subtree for calculating 
                                                    // location, and base type of elements)
   }
}
