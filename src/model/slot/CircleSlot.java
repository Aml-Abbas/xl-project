package model.slot;

import expr.Environment;
import expr.Expr;
import expr.ExprParser;
import model.Sheet;
import util.XLCircularException;

import java.io.IOException;

public class CircleSlot implements Slot {
    private Expr expr;

    public CircleSlot(String expr) throws IOException {
        ExprParser parser = new ExprParser();
        this.expr = parser.build(expr);
    }

    @Override
    public double getValue(Environment environment) {
        throw new XLCircularException();
    }

    @Override
    public String StringValue(Environment env) {
        throw new XLCircularException();
    }

    public String toString(Environment env) {
        throw new XLCircularException();
    }

    public void eval(Sheet sheet){
        expr.value(sheet);
    }
}
