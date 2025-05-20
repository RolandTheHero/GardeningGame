package roland.games.gardening;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LocalDataHandler implements DataHandler {
    private final Map<String, Plant> plants = new HashMap<>();
    private final Map<Long, Garden> gardens = new HashMap<>();
    private final Map<Long, Inventory> inventories = new HashMap<>();
    private final Map<Long, Cooldowns> cooldowns = new HashMap<>();
    static public final List<Trait> allTraits = new ArrayList<>();
    private long codeNum = 703;
    
    public void addUser(long userid) {
        if (gardens.get(userid) != null) return; // User is already added
        gardens.put(userid, new Garden(3));
        inventories.put(userid, new Inventory());
        cooldowns.put(userid, new Cooldowns());
    }
    public void save(Plant plant) { plants.put(plant.code(), plant); }

    public Plant getPlant(String code) { return plants.get(code); }
    public Map<String, Plant> getPlants() { return plants; }
    public Garden getGarden(long userid) { return gardens.get(userid); }
    public Inventory getInventory(long userid) { return inventories.get(userid); }
    public Cooldowns getCooldowns(long userid) { return cooldowns.get(userid); }
    public String nextPlantCode() { return Long.toUnsignedString(codeNum++, 26); }
    public Trait randomTrait() {
        if (allTraits.isEmpty()) {
            Path pathIn = Paths.get("src/main/java/roland/games/gardening/traits.txt"); 
            try (Scanner scanner = new Scanner(pathIn)) {
                while (scanner.hasNextLine()) allTraits.add(new CommonTrait(scanner.nextLine()));
            } catch (IOException e) { e.printStackTrace(); }
        }
        return allTraits.get((int)(Math.random()*allTraits.size()));
    }
}