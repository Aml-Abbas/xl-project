package gui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;

public class SheetPanel extends BorderPanel {
    private SlotLabels slotLabels;

    public SheetPanel(int rows, int columns) {
        slotLabels= new SlotLabels(rows,columns);
        add(WEST, new RowLabels(rows));
        add(CENTER, new SlotLabels(rows, columns));
    }
    public void setText(String name , String text){
        slotLabels.setText(name, text);
    }
    public String getNameOfSelected(){
        return slotLabels.getNameOfSelected();
    }
}