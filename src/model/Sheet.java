package model;

import expr.Environment;
import expr.Expr;
import expr.ExprParser;
import model.slot.*;
import util.XLCircularException;
import util.XLException;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Sheet extends Observable implements Environment {
    private Map<String, Slot> sheet;

    public Sheet() {
        sheet = new HashMap<>();
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
        sheet.remove(name);
        setChanged();
        notifyObservers();
    }

    public void clearAll() {
        for (String address : sheet.keySet()) {
            clear(address);
        }
    }

    public String getString(String address) {
        if (sheet.containsKey(address)) {
            return sheet.get(address).toString();
        }
        return "";
    }

    public String getValue(String address) {
        if (sheet.containsKey(address)) {
            return sheet.get(address).StringValue(this);
        }
        return "";
    }

  public void insertExpression(String address, String value) throws IOException {
        // value can be
        // Number
        // Comment
        // Expression
        if (value.equals("") || value.isEmpty()) {
            sheet.remove(address);
        }

        else if (value.charAt(0) == '#') {
                sheet.put(address, new CommentSlot(value));
            }
        else {
            try {
                sheet.put(address, new CircleSlot());

                ExprParser parser = new ExprParser();
                Expr expression = parser.build(value);
                sheet.put(address, new ExprSlot(expression));
            }catch (Exception e){
             e.getMessage();
            }
            }

        setChanged();
        notifyObservers();
    }

    public Map<String, Slot> getMap() {
        return sheet;
    }

}