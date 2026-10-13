import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Locale;
import javax.swing.*;


public class teamSheet {
    public final Map<String, Map<String, String>> importedPokemon = new LinkedHashMap<>();
    private final JPanel team = new JPanel();

    public teamSheet() {
        JFrame frame = new JFrame("Teamsheet");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 600);

        team.setLayout(new BoxLayout(team, BoxLayout.Y_AXIS));
        JPanel inputPanel = new JPanel();

        JButton button = new JButton("Import team from PokePaste");
        button.addActionListener(e -> importTeam());
        inputPanel.add(button);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(team), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void importTeam() {
        try {
            String clipboardText = (String) Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .getData(DataFlavor.stringFlavor);

            if (clipboardText.contains("pokepaste.es/") || clipboardText.contains("pokepast.es/")) {
                System.out.println("Website import is not supported yet");
                return;
            }

            importedPokemon.clear();
            for (String pokemonText : clipboardText.trim().split("\\R\\s*\\R")) {
                if (!pokemonText.isEmpty()) {
                    Map<String, String> pokemon = parsePokemon(pokemonText);
                    importedPokemon.put(pokemon.get("name"), pokemon);
                }
            }

            displayTeam();
            System.out.println("Imported successfully");
        } catch (Exception ex) {
            System.out.println("Could not import team: " + ex.getMessage());
        }
    }

    private void displayTeam() {
        team.removeAll();
        for (Map<String, String> pokemon : importedPokemon.values()) {
            JTextArea card = new JTextArea(pokemon.get("raw"));
            card.setEditable(false);
            card.setLineWrap(true);
            card.setWrapStyleWord(true);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            team.add(card);
        }
        team.revalidate();
        team.repaint();
    }

    private Map<String, String> parsePokemon(String pokemonText) {
        Map<String, String> pokemon = new LinkedHashMap<>();
        String[] lines = pokemonText.trim().split("\\R");

        String[] header = lines[0].split("@", 2);
        pokemon.put("name", header[0].trim());
        if (header.length > 1) {
            pokemon.put("item", header[1].trim());
        }

        int moveNumber = 1;
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith("- ")) {
                pokemon.put("move" + moveNumber++, line.substring(2));
            } else if (line.endsWith(" Nature")) {
                pokemon.put("nature", line.substring(0, line.length() - " Nature".length()).trim());
            } else if (line.contains(":")) {
                String[] field = line.split(":", 2);
                String label = field[0].trim();
                if (!label.isEmpty()) {
                    pokemon.put(toFieldKey(label), field[1].trim());
                }
            }
        }

        pokemon.put("raw", pokemonText.trim());
        return pokemon;
    }

    private String toFieldKey(String label) {
        String[] words = label.trim().toLowerCase(Locale.ROOT).split("\\s+");
        StringBuilder key = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            key.append(Character.toUpperCase(words[i].charAt(0)))
                .append(words[i].substring(1));
        }
        return key.toString();
    }
}
