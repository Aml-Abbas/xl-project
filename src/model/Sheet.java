package model;

import expr.Environment;
import expr.Expr;
import expr.ExprParser;
import model.slot.*;
import util.XLCircularException;
import util.XLException;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
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
    public double value(String address) {
        Slot slot = sheet.get(address);
        if (slot == null) {
            throw new NoSuchElementException("There is no element at the given location.");
        }
        return slot.getValue(this);
    }

    public void clear(String address) {
        sheet.remove(address);
        setChanged();
        notifyObservers();
    }

    public void clearAll() {
        sheet = new HashMap<>();
        setChanged();
        notifyObservers();
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
                // om det går att räkna ut värdet.
                sheet.put(address, new CircleSlot());

                ExprParser parser = new ExprParser();
                Expr expression = parser.build(value);
                sheet.put(address, new ExprSlot(expression));
            }catch (Exception e){
             //KAStA VIDARE (editor )
                e.getMessage();
            }
            }

        setChanged();
        notifyObservers();
    }

    public void save(String path) throws FileNotFoundException {
        XLPrintStream xlPrintStream= new XLPrintStream(path);
        xlPrintStream.save(sheet.entrySet());
    }

    public void load(String path) throws FileNotFoundException {
        XLBufferedReader xlBufferedReader= new XLBufferedReader(path);
        xlBufferedReader.load(this);

    }
}