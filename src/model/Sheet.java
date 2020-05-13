package model;

import expr.Environment;
import expr.Expr;
import expr.ExprParser;
import model.slot.*;
import util.XLException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Sheet extends Observable implements Environment {


    public final int ROWS, COLUMNS;
    private String currentSlotName;
    private Map<String, Slot> slotMap;

    public Sheet(int ROWS, int COLUMNS) {
        this.ROWS = ROWS;
        this.COLUMNS = COLUMNS;
        currentSlotName = "A1";

        slotMap = new HashMap<>();
        for (char ch = 'A'; ch < 'A' + COLUMNS; ch++) {
            for (int row = 1; row <= ROWS; row++) {
                String name = ch + String.valueOf(row);
                slotMap.put(name, new BlankSlot());
            }
        }
    }

    @Override
    public double value(String name) {
        Slot slot = slotMap.get(name);
        if (slot instanceof ValueSlot) {
            return ((ValueSlot) slot).getValue(this);
        }
        throw new XLException("Must contain a numeric value or expression");
    }


    public void clear(String name) {
        slotMap.put(name, new BlankSlot());
        setChanged();
        notifyObservers();
    }

    public void clearAll() {
        for (String name : slotMap.keySet()) {
            clear(name);
        }
    }

    public void setCurrentSlotName(String currentSlotName) {
        this.currentSlotName = currentSlotName;
    }

    public String getCurrentSlotName() {
        return currentSlotName;
    }

    public String getSlotText(String name) {
        return slotMap.get(name).toString();
    }

    private Slot getSlot(String name) {
        return slotMap.get(name);
    }

    public boolean isBlank(String name) {
        return getSlot(name) instanceof BlankSlot;
    }

    public Map<String, Slot> getMap() {
        return slotMap;
    }



    public void setSlot(String name, Slot slot) {
        slotMap.put(name, slot);
    }

    public void setSlotValue(String name, String value) {
        if (value == null || value.length() == 0) {
            setSlot(name, new BlankSlot());
            setChanged();
            notifyObservers();
            return;
        }
        //The first character denotes the type of slot
        char firstCharacter = value.charAt(0);
        try {
            switch (firstCharacter) {
                case '#':
                    setSlot(name, new CommentSlot(value));
                    break;
                case '=':
                    setSlot(name, new CircleSlot()); //The edited slot is set to an exceptionslot
                    String expressionString = value.substring(1); // Removes the first character ("=")
                    ExprParser parser = new ExprParser();
                    Expr expression = parser.build(expressionString);
                    expression.value(this);

                    //Test first with exceptionslot
                    setSlot(name, new ExprSlot(expression));
                    break;
                default:
                    setSlot(name, new NumSlot(Double.valueOf(value)));
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

}