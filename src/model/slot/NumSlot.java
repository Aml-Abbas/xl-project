package model.slot;

import expr.Environment;

public class NumSlot implements ValueSlot{
    private final double value;
    public NumSlot(double value) {
        this.value = value;
    }

    @Override
    public double getValue(Environment environment) {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
