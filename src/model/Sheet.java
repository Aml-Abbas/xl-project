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
    private static final int ROWS = 10, COLUMNS = 8;
    private StringSlot currentSlot;
    private String currentSlotName;
    private Map<String,StringSlot> map;

    public Sheet() {
        map= new HashMap<>();
        currentSlotName="A1";
        for (char ch='A';ch<'A'+COLUMNS;ch++){
            for (int i=1 ;i<= ROWS;i++){
                String name= ch+String.valueOf(i);
                map.put(name,new BlankSlot());
            }
        }
    }
    public Map<String, StringSlot> getMap(){
        return map;
    }

    @Override
    public double value(String name) {
        StringSlot stringSlot= map.get(name);
        if (stringSlot instanceof ValueSlot){
            return ((ValueSlot) stringSlot).getValue(this);
        }
        throw new XLException("no value");
    }
    public void clear(String name){
        map.put(name, new BlankSlot());
        setChanged();
        notifyObservers();
    }
    public void clearAll(){
        for (String s: map.keySet()){
        clear(s);
        }
    }
    public void setCurrentSlot(String name) {
        this.currentSlot = map.get(name);
        setChanged();
        notifyObservers();
    }
    public void setCurrentSlot(StringSlot slot) {
        this.currentSlot = slot;
    }

    public void setCurrentSlotName(String currentSlotName) {
        this.currentSlotName = currentSlotName;
    }

    public String getCurrentSlotName() {
        return currentSlotName;
    }

    public String getSlotText(String name) {
        return map.get(name).toString();
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
                    setSlot(name, new ExceptionSlot()); //The edited slot is set to an exceptionslot
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

    private void setSlot(String name, StringSlot slot) {
        map.put(name, slot);
    }

    private StringSlot getSlot(String name) {
        return map.get(name);
    }

    public boolean isBlank(String name) {
        return getSlot(name) instanceof BlankSlot;
    }
}
