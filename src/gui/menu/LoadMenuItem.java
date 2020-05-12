package gui.menu;

import gui.StatusLabel;
import gui.XL;
import model.Sheet;
import model.XLBufferedReader;

import java.io.FileNotFoundException;
import javax.swing.JFileChooser;

class LoadMenuItem extends OpenMenuItem {
    private XLBufferedReader xlBufferedReader;

    public LoadMenuItem(XL xl, StatusLabel statusLabel) {
        super(xl, statusLabel, "Load");
    }

    protected void action(String path) throws FileNotFoundException {
        xlBufferedReader= new XLBufferedReader(path);
        xlBufferedReader.load((Sheet)xl.getEnvironment());
    }

    protected int openDialog(JFileChooser fileChooser) {
        return fileChooser.showOpenDialog(xl);
    }
}