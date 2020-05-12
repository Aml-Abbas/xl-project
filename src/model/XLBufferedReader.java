package model;

import model.slot.BlankSlot;
import util.XLException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class XLBufferedReader extends BufferedReader {

    public XLBufferedReader(String name) throws FileNotFoundException {
        super(new FileReader(name));
    }

    public void load(Sheet sheet) {
        try {
            while (ready()) {
                String string = readLine();
                String[] split= string.split("=");
                if (split.length>1){
                    sheet.setSlotValue(split[0],split[1]);

                }else {
                    sheet.setSlotValue(split[0],"");
                }
            }
        } catch (Exception e) {
            throw new XLException(e.getMessage());
        }
    }
}
