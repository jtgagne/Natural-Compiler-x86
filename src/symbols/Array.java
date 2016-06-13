package symbols;
import lexer.*;

/**
 * Holds all information about an array.   
 * Multi-dimensional arrays are stored as
 * arrays of arrays. 
 * @author assiterk
 */
public class Array extends Type {
    
   public Type of;                  // array *of* type
   public int size = 1;             // number of elements
   
   /**
    * Constructor for class Array
    * @param sz Number of elements
    * @param t  Type of element stored in array (could be an Array)
    */
   public Array(int sz, Type t) {
       
      super("[]", Tag.INDEX, sz*t.width); // Call Type constructor with lexeme, token,space needed
      
      size = sz;  
      of = t;
   }
   @Override
   public String toString() 
   { 
       return "[" + size + "] " + of.toString(); 
   }
}
