package model.slot;

import expr.Environment;

public interface Slot {
    String toString();
    double getValue(Environment environment);
    String StringValue(Environment env);
}
