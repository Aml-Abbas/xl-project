package gui.menu;

import gui.XL;
import model.Sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

class ClearAllMenuItem extends JMenuItem implements ActionListener {
    private XL xl;

    public ClearAllMenuItem(XL xl) {
        super("Clear all");
        addActionListener(this);
        this.xl=xl;
    }

    public void actionPerformed(ActionEvent e) {
        Sheet sheet= xl.getSheet();
        sheet.clearAll();
    }
}