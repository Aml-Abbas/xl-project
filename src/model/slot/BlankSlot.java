package model.slot;

import expr.Environment;

public class BlankSlot implements Slot{
    public String toString() {
        return "";
    }

    @Override
    public double getValue(Environment environment) {
        return 0;
    }

    @Override
    public String StringValue(Environment env) {
        return "";
    }
}
