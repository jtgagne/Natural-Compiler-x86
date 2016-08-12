package semantics;

import inter.Constant;
import inter.Expr;
import lexer.*;
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
     * Cast the values of a constant to a real value
     * @param id the identifier the constant is being assigned to
     * @param constant a constant-node
     * @return
     */
    public static Constant castConstantToReal(Expr id, Constant constant){
        Type type = id.getType();
        Real real;
        int value;

        Token token = constant.getToken();

        //obtain the int value from
        if(token.isNum()){
            Num num = (Num) token;
            value = num.value;
        }else if(token.isChar()){
            Char character = (Char) token;
            value = character.value;
        }else if(token.tag == Tag.TRUE){
            value = 1;
        }else { //Tag.FALSE
            value = 0;
        }

        real = new Real((float) value);
        constant = new Constant(real, type);
        return constant;
    }
    /**
     * Handle the various casting types
     */
    public static Expr updateAssignmentTypes(Expr id, Expr expr){
        Token token = expr.getToken();

        if(id.getType().equals(expr.getType())){
            return expr;
        }

        //Cast an expression type to int
        if(id.getType().equals(Type.Int)){
            if(expr.getType().equals(Type.Long)){
                Warnings.narrowing("long", "int");           //Casting long to int may result in data loss
            } else if (expr.getType().equals(Type.Float)){
                Warnings.floatingToWhole("float", "int");    //Casting long to int may result in data loss
            } else if (expr.getType().equals(Type.Double)){
                Warnings.floatingToWhole("double", "int");   //Casting long to int may result in data loss
            }
            expr.setType(Type.Int);
        }

        //Assigning a type to a long value.
        else if(id.getType().equals(Type.Long)){
            if (expr.getType().equals(Type.Float)){
                Warnings.floatingToWhole("float", "long");  //Casting long to int may result in data loss
            } else if (expr.getType().equals(Type.Double)){
                Warnings.floatingToWhole("double", "long");  //Casting long to int may result in data loss
            }
            expr.setType(Type.Long);
        }

        //Assigning a type to a double value.
        else if(id.getType().equals(Type.Double)){
            expr = new Expr(token, Type.Double);
        }

        //Assigning a type to a float value.
        else if(id.getType().equals(Type.Float)){
            if(expr.getType().equals(Type.Int)){
                Warnings.floatingToWhole("int", "float");          //Casting long to int may result in data loss
            } else if (expr.getType().equals(Type.Double)){
                Warnings.narrowing("double", "float");        //Casting long to int may result in data loss
            } else if (expr.getType().equals(Type.Long)){
                Warnings.floatingToWhole("long", "float");  //Casting long to int may result in data loss
                Warnings.narrowing("long", "float");        //Casting long to int may result in data loss
            }
            expr = new Expr(token, Type.Float);
        }

        //Assigning a type to a char value.
        else if(id.getType().equals(Type.Char)){
            if (expr.getType().equals(Type.Float)){
                Warnings.incompatibleTypes("float", "char");        //Casting long to int may result in data loss
                return null;
            } else if (expr.getType().equals(Type.Double)){
                Warnings.incompatibleTypes("double", "char");           //Casting long to int may result in data loss
                return null;
            } else if (expr.getType().equals(Type.Bool)){
                Warnings.incompatibleTypes("boolean", "char");        //Casting long to int may result in data loss
                return null;
            }
            expr = new Expr(token, Type.Char);
        }

        //Assigning a type to a boolean value.
        else if(id.getType().equals(Type.Bool) && !expr.getType().equals(Type.Bool)){
            Warnings.incompatibleTypes(expr.getType().toString(), "boolean");

        }
        return expr;
    }
}
