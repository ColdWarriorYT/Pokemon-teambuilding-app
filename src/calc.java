import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.HashMap;

public class calc {
    // Window settings and the Pokemon types shown in both grids.
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

    // Paints the shared rounded background and outline.
    private static void paintRoundedBackground(Graphics graphics, JComponent component, boolean selected) {
        Graphics2D roundedGraphics = (Graphics2D) graphics.create();
        roundedGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = Math.min(component.getWidth(), component.getHeight()) - 2;
        roundedGraphics.setColor(component.getBackground());
        roundedGraphics.fillRoundRect(1, 1, component.getWidth() - 2, component.getHeight() - 2, arc, arc);
        roundedGraphics.setColor(selected ? Color.WHITE : new Color(0, 0, 0, 70));
        roundedGraphics.setStroke(new BasicStroke(selected ? 2f : 1f));
        roundedGraphics.drawRoundRect(1, 1, component.getWidth() - 3, component.getHeight() - 3, arc, arc);
        roundedGraphics.dispose();
    }

    // Selectable input button for a Pokemon type.
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
            paintRoundedBackground(graphics, this, getModel().isSelected());
            super.paintComponent(graphics);
        }
    }

    // Read-only output label styled like an unselected type button.
    private static class RoundedLabel extends JLabel {
        RoundedLabel(String text) {
            super(text);
            setPreferredSize(new Dimension(120, 30));
            setOpaque(false);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            paintRoundedBackground(graphics, this, false);
            super.paintComponent(graphics);
        }
    }

    // Assigns the display color for a type.
    private static void colorButton(JComponent button, int typeIndex) {
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
    // Counts which types are covered by the selected types.
    private static void checkTypes(String[] types) {
        outputTypes.clear();

        for (String selectedType : types) {
            if (!Boolean.TRUE.equals(selectedTypes.get(selectedType))) {
                continue;
            }

            for (int i = 0; i < TYPES.length; i++) {
                outputTypes.put(TYPES[i], 1);
            }
            
            for (int i = 0; i < selectedTypes.size(); i++) {
                switch (selectedType) {
                        case "Normal":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Ghost", outputTypes.getOrDefault("Ghost", 1) - 1);
                            }
                            break;
                        case "Fire":
                            outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 1) + 1);
                            outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 1) + 1);
                            outputTypes.put("Bug", outputTypes.getOrDefault("Bug", 1) + 1);
                            outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 1) + 1);
                            break;
                        case "Water":
                            outputTypes.put("Fire", outputTypes.getOrDefault("Fire", 1) + 1);
                            outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 1) + 1);
                            outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 1) + 1);
                            break;
                        case "Grass":
                            outputTypes.put("Water", outputTypes.getOrDefault("Water", 1) + 1);
                            outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 1) + 1);
                            outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 1) + 1);
                            break;
                        case "Electric":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 1) - 1);
                            }
                            outputTypes.put("Water", outputTypes.getOrDefault("Water", 1) + 1);
                            outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 1) + 1);
                            break;
                        case "Ice":
                            outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 1) + 1);
                            outputTypes.put("Ground", outputTypes.getOrDefault("Ground", 1) + 1);
                            outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 1) + 1);
                            outputTypes.put("Dragon", outputTypes.getOrDefault("Dragon", 1) + 1);
                            break;
                        case "Fighting":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Ghost", outputTypes.getOrDefault("Ghost", 1) - 1);
                            }
                            outputTypes.put("Normal", outputTypes.getOrDefault("Normal", 1) + 1);
                            outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 1) + 1);
                            outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 1) + 1);
                            outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 1) + 1);
                            outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 1) + 1);
                            break;
                        case "Poison":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 1) - 1);
                            }
                            outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 1) + 1);
                            outputTypes.put("Fairy", outputTypes.getOrDefault("Fairy", 1) + 1);
                            break;
                        case "Ground":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 1) - 1);
                            }
                            outputTypes.put("Fire", outputTypes.getOrDefault("Fire", 1) + 1);
                            outputTypes.put("Electric", outputTypes.getOrDefault("Electric", 1) + 1);
                            outputTypes.put("Poison", outputTypes.getOrDefault("Poison", 1) + 1);
                            outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 1) + 1);
                            outputTypes.put("Steel", outputTypes.getOrDefault("Steel", 1) + 1);
                            break;
                        case "Flying":
                            outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 1) + 1);
                            outputTypes.put("Fighting", outputTypes.getOrDefault("Fighting", 1) + 1);
                            outputTypes.put("Bug", outputTypes.getOrDefault("Bug", 1) + 1);
                            break;
                        case "Psychic":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 1) - 1);
                            }
                            outputTypes.put("Fighting", outputTypes.getOrDefault("Fighting", 1) + 1);
                            outputTypes.put("Poison", outputTypes.getOrDefault("Poison", 1) + 1);
                            break;
                        case "Bug":
                            outputTypes.put("Grass", outputTypes.getOrDefault("Grass", 1) + 1);
                            outputTypes.put("Psychic", outputTypes.getOrDefault("Psychic", 1) + 1);
                            outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 1) + 1);
                            break;
                        case "Rock":
                            outputTypes.put("Fire", outputTypes.getOrDefault("Fire", 1) + 1);
                            outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 1) + 1);
                            outputTypes.put("Flying", outputTypes.getOrDefault("Flying", 1) + 1);
                            outputTypes.put("Bug", outputTypes.getOrDefault("Bug", 1) + 1);
                            break;
                        case "Ghost":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Normal", outputTypes.getOrDefault("Normal", 1));
                            }
                            outputTypes.put("Psychic", outputTypes.getOrDefault("Psychic", 1) + 1);
                            outputTypes.put("Ghost", outputTypes.getOrDefault("Ghost", 1) + 1);
                            break;
                        case "Dragon":
                            if (selectedTypes.size() == 1) {
                                outputTypes.put("Fairy", outputTypes.getOrDefault("Fairy", 1) -1);
                            }
                            outputTypes.put("Dragon", outputTypes.getOrDefault("Dragon", 1) + 1);
                            break;
                        case "Dark":
                            outputTypes.put("Psychic", outputTypes.getOrDefault("Psychic", 1) + 1);
                            outputTypes.put("Ghost", outputTypes.getOrDefault("Ghost", 1) + 1);
                            break;
                        case "Steel":
                            outputTypes.put("Ice", outputTypes.getOrDefault("Ice", 1) + 1);
                            outputTypes.put("Rock", outputTypes.getOrDefault("Rock", 1) + 1);
                            outputTypes.put("Fairy", outputTypes.getOrDefault("Fairy", 1) + 1);
                            break;
                        case "Fairy":
                            outputTypes.put("Fighting", outputTypes.getOrDefault("Fighting", 1) + 1);
                            outputTypes.put("Dragon", outputTypes.getOrDefault("Dragon", 1) + 1);
                            outputTypes.put("Dark", outputTypes.getOrDefault("Dark", 1) + 1);
                            break;
                        default:
                            break;
                }
            }
        }
    }

    // Refreshes the count shown in each output label.
    private static void updateOutputLabels(JLabel[] labels) {
        for (int i = 0; i < TYPES.length; i++) {
            labels[i].setText(TYPES[i] + " " + outputTypes.getOrDefault(TYPES[i], 0));
        }
    }

    // Starts the interface on Swing's event-dispatch thread.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calc());
    }

    // Creates and lays out the calculator window.
    public calc() {
        JFrame frame = new JFrame("Coverage Calculator");
        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JLabel[] outputLabels = new JLabel[TYPES.length];

        // Set up the window and its input grid.
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        inputPanel.setLayout(new GridLayout(3, 6, 8, 8));
        inputPanel.setBackground(INPUT_PANEL_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(inputPanel, BorderLayout.NORTH);
        JPanel outputContainer = new JPanel(new BorderLayout());
        outputContainer.setBackground(INPUT_PANEL_COLOR);

        // Create a selectable button for each Pokemon type.
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
                checkTypes(selectedTypes.keySet().toArray(new String[0]));
                updateOutputLabels(outputLabels);
            });
            inputPanel.add(button);
        }

        // Create output labels and keep their grid at its preferred height.
        outputPanel.setLayout(new GridLayout(3, 6, 8, 8));
        outputPanel.setBackground(INPUT_PANEL_COLOR);
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputContainer.add(outputPanel, BorderLayout.NORTH);
        frame.add(outputContainer, BorderLayout.CENTER);

        // Add one coverage label for each Pokemon type.
        for (int i = 0; i < TYPES.length; i++) {
            String labelValue;
            if (selectedTypes.size() == 0) {
                labelValue = TYPES[i] + " x 0";
            } else {
                labelValue = TYPES[i] + " x " + outputTypes.getOrDefault(TYPES[i], 1);
            }
            JLabel label = new RoundedLabel(labelValue);
            colorButton(label, i);
            Color labelColor = label.getBackground();
            int brightness = (labelColor.getRed() * 299 + labelColor.getGreen() * 587 + labelColor.getBlue() * 114) / 1000;
            label.setForeground(brightness < 145 ? Color.WHITE : new Color(25, 25, 25));
            label.setFont(new Font("Arial", Font.BOLD, 11));
            outputLabels[i] = label;
            outputPanel.add(label);
        }

        // Display the window after all components are ready.
        frame.setVisible(true);
    }
}