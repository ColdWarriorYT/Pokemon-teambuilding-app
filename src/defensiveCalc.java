import javax.swing.*;
import java.awt.*;

import java.util.HashMap;

public class defensiveCalc {
    private static final String[] TYPES = new String[]{"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
    private static final int FRAME_WIDTH = 360;
    private static final int FRAME_HEIGHT = 600;
    private static final int GRID_ROWS = 6;
    private static final int GRID_COLS = 3;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 30;
    private static final int BUTTON_SPACING = 10;
    private static final HashMap<String, Integer> hashMap = new HashMap<>();
    static {
        for (int i = 0; i < TYPES.length; i++) {
            hashMap.put(TYPES[i], 0);
        }
    }
    private static void updateHashMap(String type, int value) {
        hashMap.put(type, value);
    }
    public defensiveCalc() {
        JFrame frame = new JFrame("Defensive Coverage Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(new GridLayout(GRID_ROWS, GRID_COLS, BUTTON_SPACING, BUTTON_SPACING));

        for (String type : TYPES) {
            JButton button = new JButton(type);
            button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            button.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(frame, "Enter value for " + type + ":");
                if (input != null && !input.isEmpty()) {
                    try {
                        int value = Integer.parseInt(input);
                        updateHashMap(type, value);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid integer.");
                    }
                }
            });
            frame.add(button);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Handle the submission of the hashMap values here
            System.out.println("Submitted values: " + hashMap);
            // You can add further processing logic here
        });
        frame.add(submitButton);

        frame.setVisible(true);
    }
}
