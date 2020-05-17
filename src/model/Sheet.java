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
    public final int ROWS, COLUMNS;
    private String currentSlot;

    Map<String, Slot> sheet;

    /**
     * Creates a sheet of the given number of rows and columns
     *
     * @param ROWS as rows
     * @param COLUMNS as columns
     */
    public Sheet(int ROWS, int COLUMNS) {
        this.ROWS = ROWS;
        this.COLUMNS = COLUMNS;
        currentSlot = "A1";

        sheet = new HashMap<>();
        for (char ch = 'A'; ch < 'A' + COLUMNS; ch++) {
            for (int row = 1; row <= ROWS; row++) {
                String name = ch + String.valueOf(row);
                sheet.put(name, new BlankSlot());
            }
        }
    }

    /**
     * Gets the value of a slot at the given location.
     * @param name as the slotName e.g. A1
     */
    @Override
    public double value(String name) {
        Slot slot = sheet.get(name);
        if (slot == null) {
            throw new NoSuchElementException("There is no element at the given location.");
        }
        return slot.getValue(this); // Gets the value using the environment.
    }

    /**
     * Clears the specified cell
     *
     * @param name name of the slot e.g. A1
     */
    public void clear(String name) {
        sheet.put(name, new BlankSlot());
        setChanged();
        notifyObservers();
    }

    /**
     * Clears all the cells
     */
    public void clearAll() {
        for (String name : sheet.keySet()) {
            clear(name);
        }
    }

    /**
     * Sets a slot to current slot
     * @param name name of the slot e.g. A1
     */
    public void setCurrentSlot(String name) {
        this.currentSlot = name;
        setChanged();
        notifyObservers();
    }

    public String getCurrentSlot() {
        return currentSlot;
    }

    /**
     * Returns a string that represents what the slot's value should be in the editor.
     * @param name name of slot
     * @return
     */
    public String getSlotText(String name) {
        return sheet.get(name).toString();
    }

    /**
     * Returns a string that represents what the slot's value should be in a cell.
     * @param name as the name of slot
     * @return
     */
    public String getValue(String name) {
        return sheet.get(name).StringValue(this);
    }

    public void setSlotValue(String name, String value) throws Exception{
        if (value == null || value.length() == 0) {
            setSlot(name, new BlankSlot());
            return;
        }
        char firstCharacter = value.charAt(0);
        if (firstCharacter == '#') {
            setSlot(name, new CommentSlot(value));
        } else {
            setSlot(name, new CircleSlot());
            ExprParser parser = new ExprParser();
            Expr expression = parser.build(value);
            expression.value(this);

            setSlot(name, new ExprSlot(expression));
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Puts a slot at the given location
     * @param name name of the slot e.g. A9
     * @param slot the given data in slot
     */
    private void setSlot(String name, Slot slot) {
        sheet.put(name, slot);

    }

    public Map<String, Slot> getMap() {
        return sheet;
    }

}