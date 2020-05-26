package gui.menu;

import gui.StatusLabel;
import gui.XL;
import model.Sheet;
import model.XLBufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;

class LoadMenuItem extends OpenMenuItem {

    public LoadMenuItem(XL xl, StatusLabel statusLabel) {
        super(xl, statusLabel, "Load");
    }

    protected void action(String path) throws FileNotFoundException {
         xl.load(path);
    }

    protected int openDialog(JFileChooser fileChooser) {
        xl.clearAll();
        return fileChooser.showOpenDialog(xl);
    }
}