package model.slot;

import expr.Environment;
import util.XLCircularException;

public class CircleSlot implements ValueSlot {

    @Override
    public double getValue(Environment environment) {
        throw new XLCircularException();
    }
}
