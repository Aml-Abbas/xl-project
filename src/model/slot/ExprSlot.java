package model.slot;

import expr.Environment;
import expr.Expr;

public class ExprSlot implements ValueSlot{
    Expr expression;

    public ExprSlot(Expr expression) {
        this.expression = expression;
    }

    @Override
    public double getValue(Environment environment) {
        return expression.value(environment);
    }

    public String toString() {
        return "="+expression.toString();
    }
}
