package SnakeFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SessionSetup extends JDialog {

    //swing components for data collection
    private JComboBox<String> snakeColorComboBox;
    private JComboBox<String> bgColorComboBox;
    private JComboBox<String> snakeSpeedComboBox;
    private JCheckBox gridComboBox;

    //set up variables
    Color snakeColor = Color.GREEN;
    Color bgColor = Color.BLACK;

    int speedLevel = 1;

    boolean gridCondition = false;

    static ArrayList<JFrame> openWindows = new ArrayList<>();

    public SessionSetup(JFrame parentFrame) {

        super(parentFrame,"Game-Setup",true); //modal suggests that this child frame would have to be closed in order for other game to be played.
        setLayout(new GridLayout(5,2));

        snakeColorComboBox = new JComboBox<>(new String[]{"Green", "Red", "Blue"});
        bgColorComboBox = new JComboBox<>(new String[]{"Black", "White", "Gray"});
        snakeSpeedComboBox = new JComboBox<>(new String[]{"Level 1", "Level 2", "Level 3"});
        gridComboBox = new JCheckBox("Show Grid");


        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        confirmButton.addActionListener(e -> {
            // Retrieve the selected options
            snakeColor = getSelectedColor((String) snakeColorComboBox.getSelectedItem());
            bgColor = getSelectedColor((String) bgColorComboBox.getSelectedItem());
            speedLevel = snakeSpeedComboBox.getSelectedIndex() + 1; // Indexes start from 0
            gridCondition = gridComboBox.isSelected();

            dispose();
        });


        cancelButton.addActionListener(e -> {
            
            closeAllWindows();
            dispose();
        });


        add(new JLabel("Snake Color:"));
        add(snakeColorComboBox);
        add(new JLabel("Background Color:"));
        add(bgColorComboBox);
        add(new JLabel("Speed Level:"));
        add(snakeSpeedComboBox);
        add(gridComboBox);
        add(confirmButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(parentFrame);
        openWindows.add(parentFrame);

    }

    private static void closeAllWindows() {
        for (JFrame window : openWindows) {
            window.dispose();
        }
    }

    public Color getSnakeColor() {
        return snakeColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public boolean getGridCondition() {
        return gridCondition;
    }

    private Color getSelectedColor(String selectedItem) {
        switch (selectedItem) {
            case "Green" -> {
                return Color.GREEN;
            }
            case "Red" -> {
                return Color.RED;
            }
            case "Blue" -> {
                return Color.BLUE;
            }
            case "Gray" -> {
                return Color.GRAY;
            }
            case "White" -> {
                return Color.WHITE;
            }
            default -> {
                return Color.BLACK;
            }
        }
    }


}

