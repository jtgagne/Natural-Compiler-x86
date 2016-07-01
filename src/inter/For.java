package inter;
import symbols.*;

public class For extends Stmt
{
    Expr breakCondition;
    Stmt assignment;
    Stmt updateCondition;
    Stmt loopContent;

    public For()
    {
        assignment = null;
        breakCondition = null;
        updateCondition = null;
        loopContent = null;
    }


    /**
     *
     * @param condition The boolean break condition of the for loop.
     * @param assign the assignment of the loop variable to and initial value
     * @param update updating the iterator
     */
    public void init(Expr condition, Stmt assign, Stmt update, Stmt loop)
    {
        assignment = assign;
        breakCondition = condition;
        updateCondition = update;
        loopContent = loop;
        if( breakCondition.type != Type.Bool )
            breakCondition.error("boolean required in while");
    }

    @Override
    public void gen(int b, int a)
    {
        after = a;                                // save label a
        breakCondition.jumping(0, a);
        int label = newlabel();                   // label for stmt
        emitlabel(label);
        loopContent.gen(label, b);
        emit("goto L" + b);
    }
}
