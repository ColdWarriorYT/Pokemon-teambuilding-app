import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import javax.swing.*;

public class teamSheet {
    public teamSheet() {
        JFrame frame = new JFrame("Teamsheet");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 600);

        JPanel team = new JPanel();
        team.setLayout(new BoxLayout(team, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel();

        JButton button = new JButton("Import team from PokePaste");
        inputPanel.add(button);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(team), BorderLayout.CENTER);

        button.addActionListener(e -> {
            try {
                String clipboardText = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .getData(DataFlavor.stringFlavor);

                if (!clipboardText.contains("pokepaste.es/") && !clipboardText.contains("pokepast.es/")) {
                    team.removeAll();
                    for (String pokemon : clipboardText.trim().split("\\R\\s*\\R")) {
                        if (!pokemon.isEmpty()) {
                            JTextArea pokemonCard = new JTextArea(pokemon.trim());
                            pokemonCard.setEditable(false);
                            pokemonCard.setLineWrap(true);
                            pokemonCard.setWrapStyleWord(true);
                            pokemonCard.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                            team.add(pokemonCard);
                        }
                    }
                    team.revalidate();
                    team.repaint();
                    System.out.println("Imported succesfully");
                } else {
                    System.out.println("Website import is not supported yet");
                }
            } catch (Exception ex) {
                System.out.println("Could not import team" + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
