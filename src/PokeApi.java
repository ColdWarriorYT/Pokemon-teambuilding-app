import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PokeApi {
    private static final String API_URL = "https://pokeapi.co/api/v2/";
    private static final HttpClient CLIENT = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private PokeApi() {}

    static void addPokemonData(
        Map<String, String> pokemon,
        Map<String, Map<String, String>> moveCache
    ) throws IOException, InterruptedException {
        String species = pokemon.get("species");
        String pokemonJson = getJson("pokemon/" + toApiName(species));
        String types = getArray(pokemonJson, "types");
        Matcher typeMatcher = Pattern.compile("\"type\"\\s*:\\s*\\{\\s*\"name\"\\s*:\\s*\"([^\"]+)\"")
            .matcher(types);

        int typeNumber = 1;
        while (typeMatcher.find()) {
            pokemon.put("type" + typeNumber++, typeMatcher.group(1));
        }

        Matcher spriteMatcher = Pattern.compile("\"front_default\"\\s*:\\s*(null|\"([^\"]+)\")")
            .matcher(pokemonJson);
        if (spriteMatcher.find() && !spriteMatcher.group(1).equals("null")) {
            pokemon.put("spriteUrl", spriteMatcher.group(2));
        }

        Matcher statMatcher = Pattern.compile(
            "\"base_stat\"\\s*:\\s*(\\d+).*?\"stat\"\\s*:\\s*\\{\\s*\"name\"\\s*:\\s*\"([^\"]+)\"",
            Pattern.DOTALL
        ).matcher(getArray(pokemonJson, "stats"));
        while (statMatcher.find()) {
            String statKey = getStatKey(statMatcher.group(2));
            if (statKey != null) {
                pokemon.put("base" + statKey, statMatcher.group(1));
            }
        }

        for (int i = 1; pokemon.containsKey("move" + i); i++) {
            String moveName = toApiName(pokemon.get("move" + i));
            Map<String, String> moveData = moveCache.get(moveName);
            if (moveData == null) {
                moveData = getMoveData(moveName);
                moveCache.put(moveName, moveData);
            }
            for (Map.Entry<String, String> field : moveData.entrySet()) {
                pokemon.put("move" + i + Character.toUpperCase(field.getKey().charAt(0))
                    + field.getKey().substring(1), field.getValue());
            }
        }
    }

    private static Map<String, String> getMoveData(String moveName)
        throws IOException, InterruptedException {
        String json = getJson("move/" + moveName);
        Map<String, String> data = new HashMap<>();
        data.put("type", getString(json, "type"));
        String power = getNumberOrNull(json, "power");
        String accuracy = getNumberOrNull(json, "accuracy");
        if (power != null) {
            data.put("power", power);
        }
        if (accuracy != null) {
            data.put("accuracy", accuracy);
        }
        return data;
    }

    private static String getJson(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL + endpoint))
            .timeout(Duration.ofSeconds(20))
            .header("Accept", "application/json")
            .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("PokéAPI returned HTTP " + response.statusCode()
                + " for " + endpoint);
        }
        return response.body();
    }

    private static String getArray(String json, String key) throws IOException {
        Matcher keyMatcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\\[")
            .matcher(json);
        if (!keyMatcher.find()) {
            throw new IOException("PokéAPI response is missing " + key);
        }

        int start = keyMatcher.end();
        int depth = 1;
        boolean inString = false;
        boolean escaped = false;
        for (int i = start; i < json.length(); i++) {
            char current = json.charAt(i);
            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (current == '\\') {
                    escaped = true;
                } else if (current == '"') {
                    inString = false;
                }
            } else if (current == '"') {
                inString = true;
            } else if (current == '[') {
                depth++;
            } else if (current == ']' && --depth == 0) {
                return json.substring(start, i);
            }
        }
        throw new IOException("PokéAPI response has an invalid " + key + " array");
    }

    private static String getString(String json, String key) throws IOException {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key)
            + "\"\\s*:\\s*\\{\\s*\"name\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        if (!matcher.find()) {
            throw new IOException("PokéAPI response is missing " + key);
        }
        return matcher.group(1);
    }

    private static String getNumberOrNull(String json, String key) throws IOException {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key)
            + "\"\\s*:\\s*(null|\\d+)").matcher(json);
        if (!matcher.find()) {
            throw new IOException("PokéAPI response is missing " + key);
        }
        return matcher.group(1).equals("null") ? null : matcher.group(1);
    }

    private static String getStatKey(String apiName) {
        switch (apiName) {
            case "hp": return "Hp";
            case "attack": return "Attack";
            case "defense": return "Defense";
            case "special-attack": return "SpecialAttack";
            case "special-defense": return "SpecialDefense";
            case "speed": return "Speed";
            default: return null;
        }
    }

    private static String toApiName(String name) {
        return name.toLowerCase(Locale.ROOT)
            .replace("'", "")
            .replace(".", "")
            .replaceAll("\\s+", "-");
    }
}
