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
    private List<SlotLabel> labelList;
    private Map<String, SlotLabel> labelMap;
    private SlotLabel selected;
    private String NameOfSelected;

    public SlotLabels(int rows, int cols) {
        super(rows + 1, cols);
        labelList = new ArrayList<>(rows * cols);
        labelMap = new HashMap<>();

        for (char ch = 'A'; ch < 'A' + cols; ch++) {
            add(new ColoredLabel(Character.toString(ch), Color.LIGHT_GRAY, SwingConstants.CENTER));
        }
        for (int row = 1; row <= rows; row++) {
            for (char ch = 'A'; ch < 'A' + cols; ch++) {
                SlotLabel label = new SlotLabel();
                String name = ch + String.valueOf(row);

                label.addMouseListener(new LabelMouseAdapter(name));

                add(label);
                labelList.add(label);
                labelMap.put(name, label);

            }
        }
        selected = labelList.get(0);
        selected.setBackground(Color.YELLOW);
        NameOfSelected = "A1";

    }

    public void setText(String name, String text) {
        labelMap.get(name).setText(text);
    }

    public String getNameOfSelected() {
        return NameOfSelected;
    }

    private class LabelMouseAdapter extends MouseAdapter{
        private String slotName;

        public LabelMouseAdapter(String slotName) {
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
