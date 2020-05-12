package model.slot;

import util.XLException;

public class ExceptionSlot implements StringSlot {
    @Override
    public String toString() {
        throw new XLException("Circular dependency detected!");
    }
}
