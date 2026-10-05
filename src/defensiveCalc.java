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
}
