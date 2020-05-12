package gui.menu;

import gui.StatusLabel;
import gui.XL;
import model.Sheet;
import model.XLPrintStream;

import java.io.FileNotFoundException;
import javax.swing.JFileChooser;

class SaveMenuItem extends OpenMenuItem {
    private XLPrintStream xlPrintStream;

    public SaveMenuItem(XL xl, StatusLabel statusLabel) {
        super(xl, statusLabel, "Save");
    }

    protected void action(String path) throws FileNotFoundException {
        xlPrintStream= new XLPrintStream(path);
        xlPrintStream.save(((Sheet)xl.getEnvironment()).getMap().entrySet());
    }

    protected int openDialog(JFileChooser fileChooser) {
        return fileChooser.showSaveDialog(xl);
    }
}