package roland.games.gardening;

import java.util.HashMap;
import java.util.Map;

public record GardeningGame(DataHandler data) {
    static final private double seedRareChance = 0.05d;

    public Map<Seed, Integer> forage(long userid) {
        Map<Seed, Integer> foragedSeeds = new HashMap<>();
        double chance = 1d;
        while (Math.random() < chance) {
            chance -= 0.1d;
            Seed s = Seed.COMMON;
            if (Math.random() < seedRareChance) {
                s = Seed.RARE;
                if (Math.random() < seedRareChance) s = Seed.MAGICAL;
            }
            if (foragedSeeds.get(s) == null) foragedSeeds.put(s, 0);
            foragedSeeds.put(s, foragedSeeds.get(s) + 1);
        }
        data.addToInventory(userid, foragedSeeds);
        return foragedSeeds;
    }
    public Map<Seed, Integer> seedInventory(long userid) {
        return data.getInventory(userid).getSeeds();
    }
    public void plant(long userid, Seed seed) {
        if (data.getGarden(userid).isFull()) return;
        data.subtractFromInventory(userid, Map.of(seed, 1));
        data.getGarden(userid).plant(seed);
    }
    public void harvest(long userid, int plotNum) {
        if (!data.getGarden(userid).canHarvest(plotNum)) return;
        Plant harvestedPlant = data.getGarden(userid).harvest(plotNum);
        data.save(harvestedPlant);
    }
}
