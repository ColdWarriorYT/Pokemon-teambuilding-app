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
    private static final HashMap<String, Integer>inputTypes = new HashMap<>();
    private static final HashMap<String, Integer>selectedTypes = new HashMap<>();
    private static final HashMap<String, Boolean>selectedType = new HashMap<>();

    private static void updateHashMap(String type, int value, boolean isSelected) {
        inputTypes.put(type, value);
        selectedType.put(type, isSelected);
    }
    private static void calculateDefensiveCoverage(String[] types) {

        for (String selectedType : types) {
            if (!Boolean.TRUE.equals(selectedTypes.get(selectedType))) {
                continue;
            }
        }
        switch (SelectedType) {
            case "Normal":
                // Calculate defensive coverage for Normal type
                break;
            case "Fire":
                // Calculate defensive coverage for Fire type
                break;
            case "Water":
                // Calculate defensive coverage for Water type
                break;
            case "Electric":
                // Calculate defensive coverage for Electric type
                break;
            case "Grass":
                // Calculate defensive coverage for Grass type
                break;
            case "Ice":
                // Calculate defensive coverage for Ice type
                break;
            case "Fighting":
                // Calculate defensive coverage for Fighting type
                break;
            case "Poison":
                // Calculate defensive coverage for Poison type
                break;
            case "Ground":
                // Calculate defensive coverage for Ground type
                break;
            case "Flying":
                // Calculate defensive coverage for Flying type
                break;
            case "Psychic":
                // Calculate defensive coverage for Psychic type
                break;
            case "Bug":
                // Calculate defensive coverage for Bug type
                break;
            case "Rock":
                // Calculate defensive coverage for Rock type
                break;
            case "Ghost":
                // Calculate defensive coverage for Ghost type
                break;
            case "Dragon":
                // Calculate defensive coverage for Dragon type
                break;
            case "Dark":
                // Calculate defensive coverage for Dark type
                break;
            case "Steel":
                // Calculate defensive coverage for Steel type
                break;
            case "Fairy":
                // Calculate defensive coverage for Fairy type
                break;
        }
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
                        if (value <0) {
                            updateHashMap(type, value, true);
                        } else {
                            updateHashMap(type, value, false);
                        }
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
            System.out.println("Submitted values: " + selectedType);
            // You can add further processing logic here
        });
        frame.add(submitButton);

        frame.setVisible(true);
    }
}
