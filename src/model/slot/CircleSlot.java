package model.slot;

import expr.Environment;
import util.XLCircularException;

public class CircleSlot implements Slot {

    @Override
    public double getValue(Environment environment) {
        throw new XLCircularException();
    }

    @Override
    public String StringValue(Environment env) {
        return null;
    }
    public String toString(Environment env) {
        return null;
    }

}
