package roland.games.gardening;

import java.util.Map;
import java.util.stream.Collectors;

public interface DataHandler {
    public void addUser(long userid);
    public void save(Plant plant);
    
    public Plant getPlant(String code);
    public Map<String, Plant> getPlants();
    public Cooldowns getCooldowns(long userid);
    public Inventory getInventory(long userid);
    public Garden getGarden(long userid);

    default public void addToInventory(long userid, Map<Seed, Integer> seeds) {
        if (getInventory(userid) == null) addUser(userid);
        getInventory(userid).addSeeds(seeds);
    }
    default public void subtractFromInventory(long userid, Map<Seed, Integer> seeds) {
        Map<Seed, Integer> inverted = seeds.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> -e.getValue()));
        addToInventory(userid, inverted);
    }
    default public boolean addCooldown(long userid, String cooldownName, long length) {
        return getCooldowns(userid).add(cooldownName, length);
    }
    default public long getCooldown(long userid, String name) {
        return getCooldowns(userid).getEndTime(name);
    }
}
