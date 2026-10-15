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

            Map<String, Map<String, String>> teamData = new LinkedHashMap<>();
            for (String pokemonText : clipboardText.trim().split("\\R\\s*\\R")) {
                if (!pokemonText.isEmpty()) {
                    Map<String, String> pokemon = parsePokemon(pokemonText);
                    teamData.put(pokemon.get("name"), pokemon);
                }
            }

            new SwingWorker<Map<String, Map<String, String>>, Void>() {
                @Override
                protected Map<String, Map<String, String>> doInBackground() throws Exception {
                    Map<String, Map<String, String>> moveData = new LinkedHashMap<>();
                    for (Map<String, String> pokemon : teamData.values()) {
                        PokeApi.addPokemonData(pokemon, moveData);
                    }
                    return teamData;
                }

                @Override
                protected void done() {
                    try {
                        importedPokemon.clear();
                        importedPokemon.putAll(get());
                        displayTeam();
                        System.out.println("Imported successfully");
                    } catch (Exception ex) {
                        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                        JOptionPane.showMessageDialog(team,
                            "Could not load Pokémon data: " + cause.getMessage(),
                            "PokéAPI error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
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
        String name = header[0].trim();
        pokemon.put("name", name);
        pokemon.put("species", getSpeciesName(name));
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

    private String getSpeciesName(String name) {
        int openParenthesis = name.lastIndexOf('(');
        if (openParenthesis >= 0 && name.endsWith(")")) {
            String possibleSpecies = name.substring(openParenthesis + 1, name.length() - 1).trim();
            if (possibleSpecies.equalsIgnoreCase("M")
                || possibleSpecies.equalsIgnoreCase("F")
                || possibleSpecies.equalsIgnoreCase("NB")) {
                return name.substring(0, openParenthesis).trim();
            }
            return possibleSpecies;
        }
        return name;
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
