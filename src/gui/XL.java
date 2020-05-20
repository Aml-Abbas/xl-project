package gui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

import gui.menu.XLMenuBar;
import model.Sheet;

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
    private JPanel statusPanel, sheetPanel;
    private Sheet sheet;
    private Editor editor;
    public XL(XL oldXL) {
        this(oldXL.xlList, oldXL.counter);
    }

    /**
     * Initializes the window and structure. Adding an actionListener
     * Processes the text given in editor and sets slot value
     *
     * @param xlList as the list containing each instance
     * @param counter as the number of windows open
     */
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

    public Sheet getSheet(){
        return sheet;
    }


    public int print(Graphics g, PageFormat pageFormat, int page) {
        if (page > 0)
            return NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        printAll(g2d);
        return PAGE_EXISTS;
    }

    /**
     * Sets title to frame
     * @param title
     */
    public void rename(String title) {
        setTitle(title);
        xlList.setChanged();
    }

    public static void main(String[] args) {
        new XL(new XLList(), new XLCounter());
    }


    @Override
    public void update(Observable observable, Object object) {
        String nameOfSelected = ((SheetPanel) sheetPanel).getNameOfSelected();
        ((StatusPanel) statusPanel).setCurrentLabelText(nameOfSelected);

        editor.setText(sheet.getString(nameOfSelected));

        for (char ch = 'A'; ch < 'A' + COLUMNS; ch++) {
            for (int row = 1; row <= ROWS; row++) {
                String name = ch + String.valueOf(row);
                String text = sheet.getValue(name);
                ((SheetPanel) sheetPanel).setText(name, text);

            }
        }
    }


    private class SheetPanelMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            statusLabel.setText("");
            try {
                sheet.insertExpression(sheet.getNameOfCurrentSlot(), editor.getText());
            } catch (Exception e1) {
                statusLabel.setText(e1.getMessage());
            }
            String nameOfSelected = ((SheetPanel) sheetPanel).getNameOfSelected();

            ((StatusPanel) statusPanel).setCurrentLabelText(nameOfSelected);

            editor.setText(sheet.getString(nameOfSelected));

            sheet.setNameOfCurrentSlot(nameOfSelected);
        }
        }


    private class EditorActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("");
            String nameOfSelected = ((SheetPanel) sheetPanel).getNameOfSelected();
            try {
                sheet.insertExpression(nameOfSelected, editor.getText());
            } catch (Exception e1) {
                statusLabel.setText(e1.getMessage());
            }
        }
    }
}