package model.slot;

import expr.Environment;
import expr.ExprParser;
import util.XLCircularException;

import java.io.IOException;

public class CircleSlot implements Slot {

    public CircleSlot(String value) throws IOException {
        ExprParser parser = new ExprParser();
        parser.build(value);
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

}
