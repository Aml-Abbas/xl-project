package model;

import expr.Environment;
import expr.Expr;
import expr.ExprParser;
import model.slot.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Sheet extends Observable implements Environment {
    private final int ROWS, COLUMNS;
    private CurrentSlot currentSlot;
    private Map<String, Slot> sheet;

    public Sheet(int ROWS, int COLUMNS) {
        this.ROWS = ROWS;
        this.COLUMNS = COLUMNS;
        currentSlot=new CurrentSlot("A1");

        sheet = new HashMap<>();
        for (char ch = 'A'; ch < 'A' + COLUMNS; ch++) {
            for (int row = 1; row <= ROWS; row++) {
                String name = ch + String.valueOf(row);
                sheet.put(name, new BlankSlot());
            }
        }
    }


    @Override
    public double value(String name) {
        Slot slot = sheet.get(name);
        if (slot == null) {
            throw new NoSuchElementException("There is no element at the given location.");
        }
        return slot.getValue(this);
    }


    public void clear(String name) {
        sheet.put(name, new BlankSlot());
        setChanged();
        notifyObservers();
    }


    public void clearAll() {
        for (String name : sheet.keySet()) {
            clear(name);
        }
    }


  public void setNameOfCurrentSlot(String name) {
      currentSlot.setNameOfCurrentSlot(name);
        setChanged();
        notifyObservers();
    }

    public String getNameOfCurrentSlot() {
        return currentSlot.getNameOfCurrentSlot();
    }



    public String getString(String name) {
        return sheet.get(name).toString();
    }


    public String getValue(String name) {
        return sheet.get(name).StringValue(this);
    }

    public void insertExpression(String name, String value) throws Exception{
        if (value == null || value.length() == 0) {
            sheet.put(name, new BlankSlot());
            return;
        }
        char firstCharacter = value.charAt(0);
        if (firstCharacter == '#') {
            sheet.put(name, new CommentSlot(value));
        } else {
            sheet.put(name,new CircleSlot());
            ExprParser parser = new ExprParser();
            Expr expression = parser.build(value);
            expression.value(this);

            sheet.put(name, new ExprSlot(expression));
        }
        setChanged();
        notifyObservers();
    }

    public Map<String, Slot> getMap() {
        return sheet;
    }

}