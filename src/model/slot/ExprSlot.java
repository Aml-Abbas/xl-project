package model.slot;

import expr.Environment;
import expr.Expr;
import util.NumberAdjustment;

public class ExprSlot implements Slot{
    Expr expression;

    public ExprSlot(Expr expression) {
        this.expression = expression;
    }

    @Override
    public double getValue(Environment environment) {
        return expression.value(environment);
    }

    @Override
    public String StringValue(Environment env) {
        NumberAdjustment adj = new NumberAdjustment(0,1);
        return adj.left(getValue(env)+"");
    }

    public String toString() {
        return expression.toString();
    }
}
