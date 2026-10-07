import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

public class coverageCalc {
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
    private static final HashMap<String, Integer> outputSuperEffectiveTypes = new HashMap<>();
    private static final HashMap<String, Integer> outputNotVeryEffectiveTypes = new HashMap<>();
    private static final HashMap<String, Integer> outputImmuneTypes = new HashMap<>();

    // Shared visual styling for the rounded components.
    private static final class RoundedVisualStyle {
        private static void apply(JComponent component, boolean selected, boolean isButton) {
            component.setOpaque(false);
            if (isButton && component instanceof JButton button) {
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
            }

            Color componentColor = component.getBackground();
            int brightness = (componentColor.getRed() * 299 + componentColor.getGreen() * 587 + componentColor.getBlue() * 114) / 1000;
            component.setForeground(brightness < 145 ? Color.WHITE : new Color(25, 25, 25));
        }

        private static void paintBackground(Graphics graphics, JComponent component, boolean selected) {
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
    }

    // Selectable input button for a Pokemon type.
    private static class RoundedToggleButton extends JToggleButton {
        RoundedToggleButton(String text) {
            super(text);
            setPreferredSize(new Dimension(120, 30));
            RoundedVisualStyle.apply(this, false, true);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            RoundedVisualStyle.paintBackground(graphics, this, getModel().isSelected());
            super.paintComponent(graphics);
        }
    }

    // Read-only output label styled like an unselected type button.
    private static class RoundedLabel extends JLabel {
        RoundedLabel(String text) {
            super(text);
            setPreferredSize(new Dimension(120, 30));
            setHorizontalAlignment(SwingConstants.CENTER);
            RoundedVisualStyle.apply(this, false, false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            RoundedVisualStyle.paintBackground(graphics, this, false);
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
        outputNotVeryEffectiveTypes.clear();
        outputSuperEffectiveTypes.clear();
        outputImmuneTypes.clear();

        for (String selectedType : types) {
            if (!Boolean.TRUE.equals(selectedTypes.get(selectedType))) {
                continue;
            }

        switch (selectedType) {
            case "Normal":
                outputImmuneTypes.put("Ghost", outputImmuneTypes.getOrDefault("Ghost", 0) + 1);
                outputNotVeryEffectiveTypes.put("Rock", outputNotVeryEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Fire":
                outputSuperEffectiveTypes.put("Grass", outputSuperEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputSuperEffectiveTypes.put("Ice", outputSuperEffectiveTypes.getOrDefault("Ice", 0) + 1);
                outputSuperEffectiveTypes.put("Bug", outputSuperEffectiveTypes.getOrDefault("Bug", 0) + 1);
                outputSuperEffectiveTypes.put("Steel", outputSuperEffectiveTypes.getOrDefault("Steel", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fire", outputNotVeryEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputNotVeryEffectiveTypes.put("Water", outputNotVeryEffectiveTypes.getOrDefault("Water", 0) + 1);
                outputNotVeryEffectiveTypes.put("Rock", outputNotVeryEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputNotVeryEffectiveTypes.put("Dragon", outputNotVeryEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                break;
            case "Water":
                outputSuperEffectiveTypes.put("Fire", outputSuperEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputSuperEffectiveTypes.put("Ground", outputSuperEffectiveTypes.getOrDefault("Ground", 0) + 1);
                outputSuperEffectiveTypes.put("Rock", outputSuperEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputNotVeryEffectiveTypes.put("Water", outputNotVeryEffectiveTypes.getOrDefault("Water", 0) + 1);
                outputNotVeryEffectiveTypes.put("Grass", outputNotVeryEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputNotVeryEffectiveTypes.put("Dragon", outputNotVeryEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                break;
            case "Grass":
                outputSuperEffectiveTypes.put("Water", outputSuperEffectiveTypes.getOrDefault("Water", 0) + 1);
                outputSuperEffectiveTypes.put("Ground", outputSuperEffectiveTypes.getOrDefault("Ground", 0) + 1);
                outputSuperEffectiveTypes.put("Rock", outputSuperEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fire", outputNotVeryEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputNotVeryEffectiveTypes.put("Grass", outputNotVeryEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputNotVeryEffectiveTypes.put("Poison", outputNotVeryEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputNotVeryEffectiveTypes.put("Flying", outputNotVeryEffectiveTypes.getOrDefault("Flying", 0) + 1);
                outputNotVeryEffectiveTypes.put("Bug", outputNotVeryEffectiveTypes.getOrDefault("Bug", 0) + 1);
                outputNotVeryEffectiveTypes.put("Dragon", outputNotVeryEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Electric":
                outputImmuneTypes.put("Ground", outputImmuneTypes.getOrDefault("Ground", 0) + 1);
                outputSuperEffectiveTypes.put("Water", outputSuperEffectiveTypes.getOrDefault("Water", 0) + 1);
                outputSuperEffectiveTypes.put("Flying", outputSuperEffectiveTypes.getOrDefault("Flying", 0) + 1);
                outputNotVeryEffectiveTypes.put("Electric", outputNotVeryEffectiveTypes.getOrDefault("Electric", 0) + 1);
                outputNotVeryEffectiveTypes.put("Grass", outputNotVeryEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputNotVeryEffectiveTypes.put("Dragon", outputNotVeryEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                break;
            case "Ice":
                outputSuperEffectiveTypes.put("Grass", outputSuperEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputSuperEffectiveTypes.put("Ground", outputSuperEffectiveTypes.getOrDefault("Ground", 0) + 1);
                outputSuperEffectiveTypes.put("Flying", outputSuperEffectiveTypes.getOrDefault("Flying", 0) + 1);
                outputSuperEffectiveTypes.put("Dragon", outputSuperEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fire", outputNotVeryEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputNotVeryEffectiveTypes.put("Water", outputNotVeryEffectiveTypes.getOrDefault("Water", 0) + 1);
                outputNotVeryEffectiveTypes.put("Ice", outputNotVeryEffectiveTypes.getOrDefault("Ice", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Fighting":
                outputImmuneTypes.put("Ghost", outputImmuneTypes.getOrDefault("Ghost", 0) + 1);
                outputSuperEffectiveTypes.put("Normal", outputSuperEffectiveTypes.getOrDefault("Normal", 0) + 1);
                outputSuperEffectiveTypes.put("Ice", outputSuperEffectiveTypes.getOrDefault("Ice", 0) + 1);
                outputSuperEffectiveTypes.put("Rock", outputSuperEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputSuperEffectiveTypes.put("Dark", outputSuperEffectiveTypes.getOrDefault("Dark", 0) + 1);
                outputSuperEffectiveTypes.put("Steel", outputSuperEffectiveTypes.getOrDefault("Steel", 0) + 1);
                outputNotVeryEffectiveTypes.put("Poison", outputNotVeryEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputNotVeryEffectiveTypes.put("Flying", outputNotVeryEffectiveTypes.getOrDefault("Flying", 0) + 1);
                outputNotVeryEffectiveTypes.put("Psychic", outputNotVeryEffectiveTypes.getOrDefault("Psychic", 0) + 1);
                outputNotVeryEffectiveTypes.put("Bug", outputNotVeryEffectiveTypes.getOrDefault("Bug", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fairy", outputNotVeryEffectiveTypes.getOrDefault("Fairy", 0) + 1);
                break;
            case "Poison":
                outputImmuneTypes.put("Steel", outputImmuneTypes.getOrDefault("Steel", 0) + 1);
                outputSuperEffectiveTypes.put("Grass", outputSuperEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputSuperEffectiveTypes.put("Fairy", outputSuperEffectiveTypes.getOrDefault("Fairy", 0) + 1);
                outputNotVeryEffectiveTypes.put("Poison", outputNotVeryEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputNotVeryEffectiveTypes.put("Ground", outputNotVeryEffectiveTypes.getOrDefault("Ground", 0) + 1);
                outputNotVeryEffectiveTypes.put("Rock", outputNotVeryEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputNotVeryEffectiveTypes.put("Ghost", outputNotVeryEffectiveTypes.getOrDefault("Ghost", 0) + 1);
                break;
            case "Ground":
                outputImmuneTypes.put("Flying", outputImmuneTypes.getOrDefault("Flying", 0) + 1);
                outputSuperEffectiveTypes.put("Fire", outputSuperEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputSuperEffectiveTypes.put("Electric", outputSuperEffectiveTypes.getOrDefault("Electric", 0) + 1);
                outputSuperEffectiveTypes.put("Poison", outputSuperEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputSuperEffectiveTypes.put("Rock", outputSuperEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputSuperEffectiveTypes.put("Steel", outputSuperEffectiveTypes.getOrDefault("Steel", 0) + 1);
                outputNotVeryEffectiveTypes.put("Grass", outputNotVeryEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputNotVeryEffectiveTypes.put("Bug", outputNotVeryEffectiveTypes.getOrDefault("Bug", 0) + 1);
                break;
            case "Flying":
                outputSuperEffectiveTypes.put("Grass", outputSuperEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputSuperEffectiveTypes.put("Fighting", outputSuperEffectiveTypes.getOrDefault("Fighting", 0) + 1);
                outputSuperEffectiveTypes.put("Bug", outputSuperEffectiveTypes.getOrDefault("Bug", 0) + 1);
                outputNotVeryEffectiveTypes.put("Electric", outputNotVeryEffectiveTypes.getOrDefault("Electric", 0) + 1);
                outputNotVeryEffectiveTypes.put("Rock", outputNotVeryEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Psychic":
                outputImmuneTypes.put("Dark", outputImmuneTypes.getOrDefault("Dark", 0) + 1);
                outputSuperEffectiveTypes.put("Fighting", outputSuperEffectiveTypes.getOrDefault("Fighting", 0) + 1);
                outputSuperEffectiveTypes.put("Poison", outputSuperEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputNotVeryEffectiveTypes.put("Psychic", outputNotVeryEffectiveTypes.getOrDefault("Psychic", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Bug":
                outputSuperEffectiveTypes.put("Grass", outputSuperEffectiveTypes.getOrDefault("Grass", 0) + 1);
                outputSuperEffectiveTypes.put("Psychic", outputSuperEffectiveTypes.getOrDefault("Psychic", 0) + 1);
                outputSuperEffectiveTypes.put("Dark", outputSuperEffectiveTypes.getOrDefault("Dark", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fire", outputNotVeryEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fighting", outputNotVeryEffectiveTypes.getOrDefault("Fighting", 0) + 1);
                outputNotVeryEffectiveTypes.put("Poison", outputNotVeryEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputNotVeryEffectiveTypes.put("Flying", outputNotVeryEffectiveTypes.getOrDefault("Flying", 0) + 1);
                outputNotVeryEffectiveTypes.put("Ghost", outputNotVeryEffectiveTypes.getOrDefault("Ghost", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fairy", outputNotVeryEffectiveTypes.getOrDefault("Fairy", 0) + 1);
                break;
            case "Rock":
                outputSuperEffectiveTypes.put("Fire", outputSuperEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputSuperEffectiveTypes.put("Ice", outputSuperEffectiveTypes.getOrDefault("Ice", 0) + 1);
                outputSuperEffectiveTypes.put("Flying", outputSuperEffectiveTypes.getOrDefault("Flying", 0) + 1);
                outputSuperEffectiveTypes.put("Bug", outputSuperEffectiveTypes.getOrDefault("Bug", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fighting", outputNotVeryEffectiveTypes.getOrDefault("Fighting", 0) + 1);
                outputNotVeryEffectiveTypes.put("Ground", outputNotVeryEffectiveTypes.getOrDefault("Ground", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Ghost":
                outputImmuneTypes.put("Normal", outputImmuneTypes.getOrDefault("Normal", 0) + 1);
                outputSuperEffectiveTypes.put("Psychic", outputSuperEffectiveTypes.getOrDefault("Psychic", 0) + 1);
                outputSuperEffectiveTypes.put("Ghost", outputSuperEffectiveTypes.getOrDefault("Ghost", 0) + 1);
                outputNotVeryEffectiveTypes.put("Dark", outputNotVeryEffectiveTypes.getOrDefault("Dark", 0) + 1);
                break;
            case "Dragon":
                outputImmuneTypes.put("Fairy", outputImmuneTypes.getOrDefault("Fairy", 0) +1);  
                outputSuperEffectiveTypes.put("Dragon", outputSuperEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Dark":
                outputSuperEffectiveTypes.put("Psychic", outputSuperEffectiveTypes.getOrDefault("Psychic", 0) + 1);
                outputSuperEffectiveTypes.put("Ghost", outputSuperEffectiveTypes.getOrDefault("Ghost", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fighting", outputNotVeryEffectiveTypes.getOrDefault("Fighting", 0) + 1);
                outputNotVeryEffectiveTypes.put("Dark", outputNotVeryEffectiveTypes.getOrDefault("Dark", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fairy", outputNotVeryEffectiveTypes.getOrDefault("Fairy", 0) + 1);
                break;
            case "Steel":
                outputSuperEffectiveTypes.put("Ice", outputSuperEffectiveTypes.getOrDefault("Ice", 0) + 1);
                outputSuperEffectiveTypes.put("Rock", outputSuperEffectiveTypes.getOrDefault("Rock", 0) + 1);
                outputSuperEffectiveTypes.put("Fairy", outputSuperEffectiveTypes.getOrDefault("Fairy", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fire", outputNotVeryEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputNotVeryEffectiveTypes.put("Water", outputNotVeryEffectiveTypes.getOrDefault("Water", 0) + 1);
                outputNotVeryEffectiveTypes.put("Electric", outputNotVeryEffectiveTypes.getOrDefault("Electric", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            case "Fairy":
                outputSuperEffectiveTypes.put("Fighting", outputSuperEffectiveTypes.getOrDefault("Fighting", 0) + 1);
                outputSuperEffectiveTypes.put("Dragon", outputSuperEffectiveTypes.getOrDefault("Dragon", 0) + 1);
                outputSuperEffectiveTypes.put("Dark", outputSuperEffectiveTypes.getOrDefault("Dark", 0) + 1);
                outputNotVeryEffectiveTypes.put("Fire", outputNotVeryEffectiveTypes.getOrDefault("Fire", 0) + 1);
                outputNotVeryEffectiveTypes.put("Poison", outputNotVeryEffectiveTypes.getOrDefault("Poison", 0) + 1);
                outputNotVeryEffectiveTypes.put("Steel", outputNotVeryEffectiveTypes.getOrDefault("Steel", 0) + 1);
                break;
            default:
                break;
            }
        }
    }

    private static String formatTypeText(String type) {
        return type + " SE:" + outputSuperEffectiveTypes.getOrDefault(type, 0)
            + " NVE:" + outputNotVeryEffectiveTypes.getOrDefault(type, 0)
            + " I:" + outputImmuneTypes.getOrDefault(type, 0);
    }

    // Refreshes the count shown in each output label.
    private static void updateOutputLabels(JLabel[] labels) {
        for (int i = 0; i < TYPES.length; i++) {
            labels[i].setText(formatTypeText(TYPES[i]));
        }
    }

    // Starts the interface on Swing's event-dispatch thread.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new coverageCalc());
    }

    // Creates and lays out the calculator window.
    public coverageCalc() {
        JFrame frame = new JFrame("Coverage Calculator");
        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JLabel[] outputLabels = new JLabel[TYPES.length];

        // Set up the window and its input grid.
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
            button.setMargin(new Insets(5, 0, 5, 0));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
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
            String labelValue = selectedTypes.size() == 0
                ? TYPES[i] + " SE:0 NVE:0 I:0"
                : formatTypeText(TYPES[i]);

            JLabel label = new RoundedLabel(labelValue);
            colorButton(label, i);
            Color labelColor = label.getBackground();
            int brightness = (labelColor.getRed() * 299 + labelColor.getGreen() * 587 + labelColor.getBlue() * 114) / 1000;
            label.setForeground(brightness < 145 ? Color.WHITE : new Color(25, 25, 25));
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
            outputLabels[i] = label;
            outputPanel.add(label);
        }

        // Display the window after all components are ready.
        frame.setVisible(true);


    }
}