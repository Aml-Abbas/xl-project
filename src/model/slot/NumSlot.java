package model.slot;

import expr.Environment;
import util.NumberAdjustment;

public class NumSlot implements ValueSlot{
    private Double num;

    public NumSlot(Double value){
    this.num=value;
    }
    @Override
    public Double getValue(Environment environment) {
        return num;
    }
    @Override
    public String toString() {
        return String.valueOf(num);
    }
}
