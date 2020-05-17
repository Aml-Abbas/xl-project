package model.slot;

import expr.Environment;

public class CommentSlot implements Slot{
    String text;
    public CommentSlot(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }

    @Override
    public double getValue(Environment environment) {
        return 0;
    }

    @Override
    public String StringValue(Environment env) {
        return text;
    }
}
