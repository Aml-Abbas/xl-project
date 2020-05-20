package gui;

import model.CurrentSlot;

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
    private SlotLabel selected;
    private String NameOfSelected;

    public SlotLabels(int rows, int cols) {
        super(rows + 1, cols);
        slotlabelList = new ArrayList<>(rows * cols);
        slotLabelmap = new HashMap<>();

        for (char ch = 'A'; ch < 'A' + cols; ch++) {
            add(new ColoredLabel(Character.toString(ch), Color.LIGHT_GRAY, SwingConstants.CENTER));
        }
        for (int row = 1; row <= rows; row++) {
            for (char ch = 'A'; ch < 'A' + cols; ch++) {
                SlotLabel label = new SlotLabel();
                String name = ch + String.valueOf(row);

                label.addMouseListener(new LabelMouseAdapter(name));

                add(label);
                slotlabelList.add(label);
                slotLabelmap.put(name, label);

            }
        }
        selected = slotlabelList.get(0);
        selected.setBackground(Color.YELLOW);
        NameOfSelected = "A1";

    }

    public void setText(String name, String text) {
        slotLabelmap.get(name).setText(text);
    }

    public String getNameOfSelected() {
        return NameOfSelected;
    }
    public void setNameOfSelected(String name) {
        this.NameOfSelected=name;
    }

    private class LabelMouseAdapter extends MouseAdapter{
        private String slotName;

        private LabelMouseAdapter(String slotName) {
            this.slotName = slotName;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            NameOfSelected = slotName;
            selected.setBackground(Color.WHITE);
            selected = (SlotLabel) e.getSource();
            selected.setBackground(Color.YELLOW);
            selected.getParent().getParent().dispatchEvent(e);
        }
    }

}
