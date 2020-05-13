package gui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

import expr.Environment;
import gui.menu.XLMenuBar;
import model.Sheet;
import util.XLCircularException;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("deprecation")
public class XL extends JFrame implements Printable, Observer {
    private static final int ROWS = 10, COLUMNS = 8;
    private XLCounter counter;
    private StatusLabel statusLabel = new StatusLabel();
    private XLList xlList;
    JPanel statusPanel, sheetPanel;
    Sheet sheet;
    private Editor editor;

    public XL(XL oldXL) {
        this(oldXL.xlList, oldXL.counter);
    }

    public XL(XLList xlList, XLCounter counter) {
        super("Untitled-" + counter);
        this.xlList = xlList;
        this.counter = counter;
        xlList.add(this);
        counter.increment();
        statusPanel = new StatusPanel(statusLabel);
        sheetPanel = new SheetPanel(ROWS, COLUMNS);

        editor = new Editor();
        add(NORTH, statusPanel);
        add(CENTER, editor);
        add(SOUTH, sheetPanel);
        setJMenuBar(new XLMenuBar(this, xlList, statusLabel));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        sheetPanel.addMouseListener(new SheetPanelMouseAdapter());

        editor.addActionListener(new EditorActionListener());

        sheet = new Sheet(ROWS, COLUMNS);
        sheet.addObserver(this);
    }

    public int print(Graphics g, PageFormat pageFormat, int page) {
        if (page > 0)
            return NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        printAll(g2d);
        return PAGE_EXISTS;
    }

    public void rename(String title) {
        setTitle(title);
        xlList.setChanged();
    }

    public static void main(String[] args) {
        new XL(new XLList(), new XLCounter());
    }

    @Override
    public void update(Observable o, Object arg) {
        for (char ch = 'A'; ch < 'A' + COLUMNS; ch++) {
            for (int row = 1; row <= ROWS; row++) {
                String name = ch + String.valueOf(row);
                String text;
                try {
                    if (sheet.isBlank(name)) {
                        text = "";
                    } else {
                        text = String.valueOf(sheet.value(name));
                    }
                } catch (Exception e) {
                    text = sheet.getSlotText(name);
                }
                ((SheetPanel) sheetPanel).setText(name, text);
            }
        }
    }

    public Environment getEnvironment() {
        return sheet;
    }

    private class SheetPanelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            String nameOfSelected = ((SheetPanel) sheetPanel).getNameOfSelected();
            ((StatusPanel) statusPanel).setCurrentLabelText(nameOfSelected);
            editor.setText(sheet.getSlotText(nameOfSelected));

            sheet.setCurrentSlotName(nameOfSelected);
        }
    }

    private class EditorActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = ((SheetPanel) sheetPanel).getNameOfSelected();
            try {
                sheet.setSlotValue(name, editor.getText());
            } catch (XLCircularException e1) {
                statusLabel.setText("Circular dependency detected!");
            }
        }
    }
}