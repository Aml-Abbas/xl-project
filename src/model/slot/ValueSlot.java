package model.slot;

import expr.Environment;

public interface ValueSlot extends StringSlot {
    Double getValue(Environment environment);
}
