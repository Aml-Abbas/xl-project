package gui.menu;

import gui.XL;
import model.Sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

class ClearMenuItem extends JMenuItem implements ActionListener {
    private XL xl;

    public ClearMenuItem(XL xl) {
        super("Clear");
        addActionListener(this);
        this.xl=xl;
    }

    public void actionPerformed(ActionEvent e) {
        xl.clear();
    }
}