import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class calc {
    private static final int FRAME_WIDTH = 360;
    private static final int FRAME_HEIGHT = 600;
    private static final Color INPUT_PANEL_COLOR = new Color(204, 204, 204);
    private static final String[] TYPES = {
        "Normal", "Fire", "Water", "Grass", "Electric", "Ice",
        "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug",
        "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
    };
    private static final HashMap<String, Boolean> selectedTypes = new HashMap<>();
    private static final HashMap<String, Integer> outputTypes = new HashMap<>();

    private static class RoundedToggleButton extends JToggleButton {
        RoundedToggleButton(String text) {
            super(text);
            setPreferredSize(new Dimension(120, 30));
            setContentAreaFilled(false);
            setOpaque(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D roundedGraphics = (Graphics2D) graphics.create();
            roundedGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int arc = Math.min(getWidth(), getHeight()) - 2;
            roundedGraphics.setColor(getBackground());
            roundedGraphics.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);
            roundedGraphics.setColor(getModel().isSelected() ? Color.WHITE : new Color(0, 0, 0, 70));
            roundedGraphics.setStroke(new BasicStroke(getModel().isSelected() ? 2f : 1f));
            roundedGraphics.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
            roundedGraphics.dispose();
            super.paintComponent(graphics);
        }
    }

    private static void colorButton(JToggleButton button, int typeIndex) {
        switch (typeIndex) {
            case 0:
                button.setBackground(Color.GRAY);
                break;
            case 1:
                button.setBackground(Color.RED);
                break;
            case 2:
                button.setBackground(Color.BLUE);
                break;
            case 3:
                button.setBackground(Color.GREEN);
                break;
            case 4:
                button.setBackground(Color.YELLOW);
                break;
            case 5:
                button.setBackground(Color.CYAN);
                break;
            case 6:
                button.setBackground(new Color(139, 69, 19)); // Brown
                break;
            case 7:
                button.setBackground(new Color(128, 0, 128)); // Purple
                break;
            case 8:
                button.setBackground(new Color(210, 180, 140)); // Tan
                break;
            case 9:
                button.setBackground(new Color(135, 206, 235)); // Sky Blue
                break;
            case 10:
                button.setBackground(new Color(255, 20, 147)); // Deep Pink
                break;
            case 11:
                button.setBackground(new Color(154, 205, 50)); // Yellow Green
                break;
            case 12:
                button.setBackground(new Color(169, 169, 169)); // Dark Gray
                break;
            case 13:
                button.setBackground(new Color(75, 0, 130)); // Indigo
                break;
            case 14:
                button.setBackground(new Color(0, 0, 139)); // Dark Blue
                break;
            case 15:
                button.setBackground(new Color(47, 79, 79)); // Dark Slate Gray
                break;
            case 16:
                button.setBackground(new Color(192, 192, 192)); // Silver
                break;
            case 17:
                button.setBackground(new Color(255, 182, 193)); // Light Pink
                break;
            }
        }

    private static void checkTypes(String[] types) {
        outputTypes.clear();

        for (String selectedType : types) {
            if (!Boolean.TRUE.equals(selectedTypes.get(selectedType))) {
                continue;
            }

            for (String targetType : TYPES) {
                switch (targetType) {
                    case "Normal":
                        break;
                    case "Fire":
                        outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 0) + 1);
                        outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 0) + 1);
                        outputTypes.put("Bug", outputTypes.getOrDefault("Bug", 0) + 1);
                        outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 0) + 1);
                        break;
                    case "Water":
                        outputTypes.put("Fire", outputTypes.getOrDefault("Fire", 0) + 1);
                        outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 0) + 1);
                        outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 0) + 1);
                        break;
                    case "Grass":
                        outputTypes.put("Water", outputTypes.getOrDefault("Water", 0) + 1);
                        outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 0) + 1);
                        outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 0) + 1);
                        break;
                    case "Electric":
                        outputTypes.put("Water", outputTypes.getOrDefault("Water", 0) + 1);
                        outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 0) + 1);
                        break;
                    case "Ice":
                        outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 0) + 1);
                        outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 0) + 1);
                        outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 0) + 1);
                        outputTypes.put("Dragon", outputTypes.getOrDefault("Dragon", 0) + 1);
                        break;
                    case "Fighting":
                        outputTypes.put("Normal", outputTypes.getOrDefault("Normal", 0) + 1);
                        outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 0) + 1);
                        outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 0) + 1);
                        outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 0) + 1);
                        outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 0) + 1);
                        break;
                    case "Poison":
                        outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 0) + 1);
                        outputTypes.put("Fairy", outputTypes.getOrDefault("Fairy", 0) + 1);
                        break;
                    case "Ground":
                        outputTypes.put("Fire", outputTypes.getOrDefault("Fire", 0) + 1);
                        outputTypes.put("Electric", outputTypes.getOrDefault("Electric", 0) + 1);
                        outputTypes.put("Poison", outputTypes.getOrDefault("Poison", 0) + 1);
                        outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 0) + 1);
                        outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 0) + 1);
                        break;
                    case "Flying":
                        outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 0) + 1);
                        outputTypes.put("Fighting", outputTypes.getOrDefault("Fighting", 0) + 1);
                        outputTypes.put("Bug", outputTypes.getOrDefault("Bug", 0) + 1);
                        break;
                    case "Psychic":
                        outputTypes.put("Fighting", outputTypes.getOrDefault("Fighting", 0) + 1);
                        outputTypes.put("Poison", outputTypes.getOrDefault("Poison", 0) + 1);
                        break;
                    case "Bug":
                        outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 0) + 1);
                        outputTypes.put("Psychic", outputTypes.getOrDefault("Psychic", 0) + 1);
                        outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 0) + 1);
                        break;
                    case "Rock":
                        outputTypes.put("Fire", outputTypes.getOrDefault("Fire", 0) + 1);
                        outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 0) + 1);
                        outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 0) + 1);
                        outputTypes.put("Bug", outputTypes.getOrDefault("Bug", 0) + 1);
                        break;
                    case "Ghost":
                        outputTypes.put("Psychic", outputTypes.getOrDefault("Psychic", 0) + 1);
                        outputTypes.put("Ghost", outputTypes.getOrDefault("Ghost", 0) + 1);
                        break;
                    case "Dragon":
                        outputTypes.put("Dragon", outputTypes.getOrDefault("Dragon", 0) + 1);
                        break;
                    case "Dark":
                        outputTypes.put("Psychic", outputTypes.getOrDefault("Psychic", 0) + 1);
                        outputTypes.put("Ghost", outputTypes.getOrDefault("Ghost", 0) + 1);
                        break;
                    case "Steel":
                        outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 0) + 1);
                        outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 0) + 1);
                        outputTypes.put("Fairy", outputTypes.getOrDefault("Fairy", 0) + 1);
                        break;
                    case "Fairy":
                        outputTypes.put("Fighting", outputTypes.getOrDefault("Fighting", 0) + 1);
                        outputTypes.put("Dragon", outputTypes.getOrDefault("Dragon", 0) + 1);
                        outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 0) + 1);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void calculateCoverage(String type) {
        // placeholder for coverage calculation logic
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calc());
    }

    public calc() {
        JFrame frame = new JFrame("Coverage Calculator");
        JPanel inputPanel = new JPanel();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        inputPanel.setLayout(new GridLayout(3, 6, 8, 8));
        inputPanel.setBackground(INPUT_PANEL_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(inputPanel, BorderLayout.NORTH);

        for (int i = 0; i < TYPES.length; i++) {
            String buttonValue = TYPES[i];
            JToggleButton button = new RoundedToggleButton(buttonValue);
            colorButton(button, i);
            Color buttonColor = button.getBackground();
            int brightness = (buttonColor.getRed() * 299 + buttonColor.getGreen() * 587 + buttonColor.getBlue() * 114) / 1000;
            button.setForeground(brightness < 145 ? Color.WHITE : new Color(25, 25, 25));
            button.setFont(new Font("Arial", Font.BOLD, 11));
            button.setMargin(new Insets(5, 0, 5, 0));
            button.setBorder(new LineBorder(Color.BLACK, 1));
            button.addActionListener(e -> {
                JToggleButton clicked = (JToggleButton) e.getSource();
                String type = clicked.getText();
                boolean selected = clicked.isSelected();
                if (selected) {
                    selectedTypes.put(type, true);
                } else {
                    selectedTypes.remove(type);
                }
            });
            inputPanel.add(button);
        }

        for (int i = 0; i < selectedTypes.size(); i++) {
            if (selectedTypes.get(TYPES[i]).equals(true)) {
                calculateCoverage(TYPES[i]);
            }
        }

        frame.setVisible(true);
    }
}
