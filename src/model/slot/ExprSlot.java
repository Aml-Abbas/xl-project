package model.slot;

import expr.Environment;
import expr.Expr;

public class ExprSlot implements ValueSlot{
    private Expr expr;

    public ExprSlot(Expr expr){
        this.expr=expr;
    }
    @Override
    public Double getValue(Environment environment) {
        return expr.value(environment);
    }
    @Override
    public String toString() {
        return "="+expr.toString();
    }
}
