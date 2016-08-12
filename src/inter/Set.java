package inter;
import code_generation.ASMGen;
import code_generation.AssemblyFile;
import code_generation.Registers;
import information.Printer;
import lexer.Num;
import lexer.Real;
import lexer.Token;
import semantics.TypeCasting;
import symbols.*;

/**
 * Updates the types of the assignments
 * creates a node for variable assignment
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class Set extends Stmt {

    public Id mIdentifier;
    public Expr expr;
    private static final String ERROR = "ERROR SET: generating assignment";

    public Set(Id identifier, Expr x) {
        mIdentifier = identifier;
        expr = x;

        //Cast the value of a token to real if the expression is constant
        if(!expr.mToken.isReal() && expr.isConstant() && mIdentifier.mType.isFloatingPoint()){
            expr = TypeCasting.castConstantToReal(mIdentifier, (Constant) expr);
        }
        else if(mIdentifier.mType != expr.mType){
            expr = TypeCasting.updateAssignmentTypes(mIdentifier, expr);
        }

        if (check(mIdentifier.mType, expr.mType) == null ) error("type error");

    }

    @Override
    public boolean isSet() {
        return true;
    }

    public String toString(){
        return "Assign: (" + mIdentifier.getTypeStr() + ") " + mIdentifier.getName() +
                " = (" + expr.mType.lexeme + ") " + expr.getValue();
    }

    public Type check(Type p1, Type p2) {
        if ( Type.numeric(p1) && Type.numeric(p2) )
            return p2;
        else if ( p1 == Type.Bool && p2 == Type.Bool )
            return p2;
        else if(p1 == Type.Char && p2 == Type.Char){
            return p2;
        }
        else return null;
    }

    /**
     * Add the generated code to the AssemblyFile object that will be used to generate the file
     * @param b starting point (used for loops)
     * @param a ending point (used for loops)
     */
    @Override
    public void gen(int b, int a) {
        emit( this.toAsmMain() );
        AssemblyFile.addVariables(this.toAsmData());
        AssemblyFile.addConstant(this.toAsmConstants());
    }

    /**
     * get the generated assembly assignment and clear all registers
     * @return the assembly code to be added to the main function
     */
    @Override
    public String toAsmMain() {
        String code = genAssignment();   //Generate assembly
        Registers.clearAllRegs();        //Clear the used registers
        return code;
    }

    @Override
    public String toAsmConstants() {
        return expr.toAsmConstants();
    }

    /**
     * For any floating point values, they need to be declared initially as a global variable (MIPS problem)
     * To account for this, any floating point variables are added to the data section from here.
     * @return line to be added to the data section of the assembly file
     */
    @Override
    public String toAsmData() {
        return expr.toAsmData();
    }

    /**
     * Generate assembly code to assign a variable to a register value
     * id = expr
     * @return generated assembly
     */
    private String genAssignment(){
        StringBuilder sb = new StringBuilder();

        sb.append(expr.toAsmMain());

        //Get the register of the saved operation
        String savedReg = expr.getResultRegister();

        sb.append(ASMGen.storeVar(mIdentifier, savedReg));

        return sb.toString();
    }

}
