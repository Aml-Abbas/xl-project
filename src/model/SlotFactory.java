package model;

import expr.Expr;
import expr.ExprParser;
import model.slot.*;

import java.io.IOException;

public class SlotFactory {
    private static Slot slot;

    public static Slot create(Sheet sheet, String value){
        if (value == null || value.length() == 0) {
           slot= new BlankSlot();
        }
        char firstCharacter = value.charAt(0);
        try {
            switch (firstCharacter) {
                case '#':
                  slot= new CommentSlot(value);
                    break;
                case '=':
                    slot= new CircleSlot();

                    String expressionString = value.substring(1);
                    ExprParser parser = new ExprParser();
                    Expr expression = parser.build(expressionString);
                    expression.value(sheet);

                    slot= new ExprSlot(expression);
                    break;
                default:
                    slot= new NumSlot(Double.valueOf(value));
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return slot;
    }
}
