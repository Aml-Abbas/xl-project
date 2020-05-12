package model.slot;

import expr.Environment;

public class CommentSlot implements ValueSlot{
    String comment;

    public CommentSlot(String comment){
        this.comment=comment;
    }
    @Override
    public Double getValue(Environment environment) {
        return Double.valueOf(0);
    }
    @Override
    public String toString() {
        return comment;
    }
}
