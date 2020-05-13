package model.slot;

import expr.Environment;

public interface ValueSlot extends Slot {
    double getValue(Environment environment);
}
