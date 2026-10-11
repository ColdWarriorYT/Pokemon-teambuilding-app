import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


public class defensiveCalc {
    public static final String[] TYPES = new String[]{"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
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
