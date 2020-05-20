package model;

import expr.Expr;
import expr.ExprParser;
import model.slot.CircleSlot;
import model.slot.ExprSlot;
import model.slot.Slot;

import java.io.IOException;

public class SlotFactory {
    public static Slot make(String value) throws IOException {
        ExprParser parser = new ExprParser();
        try {
            Expr expr = parser.build(value);
            return new ExprSlot(expr);
        } catch (IOException e) {
            return new CircleSlot(value);
        }
    }
}
