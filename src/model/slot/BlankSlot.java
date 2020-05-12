package model.slot;

import expr.Environment;

public class BlankSlot implements ValueSlot{
    @Override
    public Double getValue(Environment environment) {
        return Double.valueOf(0);
    }
    @Override
    public String toString() {
        return "";
    }
}
