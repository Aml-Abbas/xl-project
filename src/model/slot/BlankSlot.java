package model.slot;

import expr.Environment;

public class BlankSlot implements ValueSlot, Slot{
    public String toString() {
        return "";
    }

    @Override
    public double getValue(Environment environment) {
        return 0;
    }
}
