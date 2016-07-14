package semantics;

import inter.Expr;
import inter.Id;
import lexer.Token;
import symbols.Type;

/**
 * Casts types of nodes / expressions accordingly.
 * Created by gagnej3 on 7/14/16.
 * Justin Gagne and Zack Farrer
 * Professor Assiter
 * Wentworth Institute of Technology
 * Compiler Design - Summer 2016
 */
public class TypeCasting {

    /**
     * Handle the various casting types
     */
    public static Expr updateAssignmentTypes(Expr id, Expr expr){
        Token token = expr.op;

        if(id.type.equals(expr.type)){
            return expr;
        }

        //Cast an expression type to int
        if(id.type.equals(Type.Int)){
            if(expr.type.equals(Type.Long)){
                Warnings.narrowing("long", "int");           //Casting long to int may result in data loss
            } else if (expr.type.equals(Type.Float)){
                Warnings.floatingToWhole("float", "int");    //Casting long to int may result in data loss
            } else if (expr.type.equals(Type.Double)){
                Warnings.floatingToWhole("double", "int");   //Casting long to int may result in data loss
            }
            expr = new Expr(token, Type.Int);
        }

        //Assigning a type to a long value.
        else if(id.type.equals(Type.Long)){
            if (expr.type.equals(Type.Float)){
                Warnings.floatingToWhole("float", "long");  //Casting long to int may result in data loss
            } else if (expr.type.equals(Type.Double)){
                Warnings.floatingToWhole("double", "long");  //Casting long to int may result in data loss
            }
            expr = new Expr(token, Type.Long);
        }

        //Assigning a type to a double value.
        else if(id.type.equals(Type.Double)){
            if (expr.type.equals(Type.Float)){
                Warnings.widening("float", "double");        //Casting long to int may result in data loss
            }
            expr = new Expr(token, Type.Double);
        }

        //Assigning a type to a float value.
        else if(id.type.equals(Type.Float)){
            if(expr.type.equals(Type.Int)){
                Warnings.floatingToWhole("int", "float");          //Casting long to int may result in data loss
            } else if (expr.type.equals(Type.Double)){
                Warnings.narrowing("double", "float");        //Casting long to int may result in data loss
            } else if (expr.type.equals(Type.Long)){
                Warnings.floatingToWhole("long", "float");  //Casting long to int may result in data loss
                Warnings.narrowing("long", "float");        //Casting long to int may result in data loss
            }
            expr = new Expr(token, Type.Float);
        }

        //Assigning a type to a char value.
        else if(id.type.equals(Type.Char)){
            if (expr.type.equals(Type.Float)){
                Warnings.incompatibleTypes("float", "char");        //Casting long to int may result in data loss
                return null;
            } else if (expr.type.equals(Type.Double)){
                Warnings.incompatibleTypes("double", "char");           //Casting long to int may result in data loss
                return null;
            } else if (expr.type.equals(Type.Bool)){
                Warnings.incompatibleTypes("boolean", "char");        //Casting long to int may result in data loss
                return null;
            }
            expr = new Expr(token, Type.Char);
        }

        //Assigning a type to a boolean value.
        else if(id.type.equals(Type.Bool) && !expr.type.equals(Type.Bool)){
            Warnings.incompatibleTypes(expr.type.toString(), "boolean");

        }

        return expr;
    }
}
