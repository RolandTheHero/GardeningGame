import java.util.HashMap;
import java.util.Map;

import roland.games.gardening.*;

public class LocalDataHandler implements DataHandler {
    private Map<String, Plant> plants = new HashMap<>();
    private Map<Long, Garden> gardens = new HashMap<>();
    private Map<Long, Inventory> inventories = new HashMap<>();
    private Map<Long, Cooldowns> cooldowns = new HashMap<>();
    
    public void addUser(long userid) {
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
}