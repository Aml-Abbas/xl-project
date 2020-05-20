package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingConstants;

public class SlotLabels extends GridPanel {
    private List<SlotLabel> slotlabelList;
    private Map<String, SlotLabel> slotLabelmap;
    private SlotLabel currentSlotLabel;
    private String currentAddress;
    private String previousAddress;

    public SlotLabels(int rows, int cols) {
        super(rows + 1, cols);
        slotlabelList = new ArrayList<>(rows * cols);
        slotLabelmap = new HashMap<>();

        for (char ch = 'A'; ch < 'A' + cols; ch++) {
            add(new ColoredLabel(Character.toString(ch),
                    Color.LIGHT_GRAY, SwingConstants.CENTER));
        }

        for (int row = 1; row <= rows; row++) {
            for (char ch = 'A'; ch < 'A' + cols; ch++) {
                SlotLabel label = new SlotLabel();
                String address = ch + String.valueOf(row);

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        previousAddress = currentAddress;
                        currentAddress = address;

                        currentSlotLabel.setBackground(Color.WHITE);
                        currentSlotLabel = (SlotLabel) e.getSource();
                        currentSlotLabel.setBackground(Color.YELLOW);
                        currentSlotLabel.getParent().getParent().dispatchEvent(e);

                    }
                });

                add(label);
                slotlabelList.add(label);
                slotLabelmap.put(address, label);

            }
        }

        currentSlotLabel = slotlabelList.get(0);
        currentSlotLabel.setBackground(Color.YELLOW);
        currentAddress = "A1";



    }

    public void setText(String name, String text) {
        slotLabelmap.get(name).setText(text);
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String address) {
        this.currentAddress = address;
    }

    public String getPreviousAddress() {
        return previousAddress;
    }

    public void setPreviousAddress(String previousAddress) {
        this.previousAddress = previousAddress;
    }

    private class LabelMouseAdapter extends MouseAdapter {
        private String address;

        private LabelMouseAdapter(String address) {
            this.address = address;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            currentAddress = address;
            currentSlotLabel.setBackground(Color.WHITE);

            currentSlotLabel = (SlotLabel) e.getSource();
            currentSlotLabel.setBackground(Color.YELLOW);
            currentSlotLabel.getParent().getParent().dispatchEvent(e);
        }
    }

}
