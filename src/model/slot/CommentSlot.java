package model.slot;

public class CommentSlot implements Slot{
    String text;
    public CommentSlot(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}
