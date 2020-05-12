package gui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;

public class StatusPanel extends BorderPanel {
    private CurrentLabel currentLabel;

    protected StatusPanel(StatusLabel statusLabel) {
        currentLabel= new CurrentLabel();
        add(WEST, new CurrentLabel());
        add(CENTER, statusLabel);
    }
    public void setCurrentLabelText(String text){
        currentLabel.setText(text);
    }
}