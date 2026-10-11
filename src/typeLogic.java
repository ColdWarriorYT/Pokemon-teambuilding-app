import java.util.HashMap;

public class typeLogic {
    private static final String[] TYPES = {
        "Normal", "Fire", "Water", "Grass", "Electric", "Ice",
        "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug",
        "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
    };
    

    public static HashMap<String,Integer> TypeCalculation(String function, HashMap SelectedTypes){
        HashMap<String, Integer>outputTypes = new HashMap<>();
        for (int i=0; i<TYPES.length; i++) {
            SelectedTypes.put(TYPES[i], 0);
            outputTypes.put(TYPES[i], 0);
        }
        if (function.equals("DEFENSE")) {
            //a
        } else if (function.equals("ATTACK")) {
            //aa
            for (int i=0; i<TYPES.length; i++) {
                
            }
        }
        return outputTypes;
    }
}