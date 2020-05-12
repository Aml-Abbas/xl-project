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
    private Map<String, SlotLabel> map;
    private String nameOfSelected;
    private SlotLabel selected;

    public SlotLabels(int rows, int cols) {
        super(rows + 1, cols);
        labelList = new ArrayList<>(rows * cols);
        map=new HashMap<>();
        for (char ch = 'A'; ch < 'A' + cols; ch++) {
            add(new ColoredLabel(Character.toString(ch), Color.LIGHT_GRAY,
                    SwingConstants.CENTER));
        }
        for (int row = 1; row <= rows; row++) {
            for (char ch = 'A'; ch < 'A' + cols; ch++) {
                SlotLabel label = new SlotLabel();
                String name= ch +String.valueOf(row);

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        nameOfSelected= name;
                        selected.setBackground(Color.WHITE);
                        selected= (SlotLabel) e.getSource();
                        selected.setBackground(Color.YELLOW);
                        selected.getParent().getParent().dispatchEvent(e);
                    }
                });

                add(label);
                labelList.add(label);
                map.put(name,label);
            }
        }

        selected=labelList.get(0);
        selected.setBackground(Color.YELLOW);
        nameOfSelected="A1";

    }

    public void setText(String name, String text) {
        map.get(name).setText(text);
    }

    public String getNameOfSelected() {
        return nameOfSelected;
    }
}
