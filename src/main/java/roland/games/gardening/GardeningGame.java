package roland.games.gardening;

import java.util.HashMap;
import java.util.Map;

public record GardeningGame(DataHandler data, DisplayHandler display) {
    static final private double seedRareChance = 0.05d;
    static final private long forageCooldown = 600L; // Seconds

    public String forage(long userid) {
        if (!data.addCooldown(userid, "forage", forageCooldown)) return display.forageFailure(data.getCooldown(userid, "forage"));
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
        return display.forageSuccess(foragedSeeds);
    }
    public String seedInventory(long userid) {
        return display.inventory(data.getInventory(userid));
    }
    public String plantSeed(long userid, Seed seed) {
        if (data.getGarden(userid).isFull()) return display.plantSeedGardenFullFailure();
        Integer quantityInInventory = data.getInventory(userid).getSeeds().get(seed);
        if (quantityInInventory == null || quantityInInventory < 1) return display.plantSeedNotEnoughFailure(seed);
        data.subtractFromInventory(userid, Map.of(seed, 1));
        data.getGarden(userid).plant(seed);
        return display.plantSeedSuccess(seed);
    }
    public String harvest(long userid, int plotNum) {
        Garden garden = data.getGarden(userid);
        if (!garden.plotOccupied(plotNum)) return display.harvestEmptyFailure(plotNum);
        if (!garden.canHarvest(plotNum)) return display.harvestNotReadyFailure(plotNum);
        Seed fromSeed = garden.getSeed(plotNum);
        Plant harvestedPlant = data.getGarden(userid).harvest(plotNum);
        data.save(harvestedPlant);
        return display.harvestSuccess(fromSeed, harvestedPlant);
    }
    public String viewPlant(String code) {
        Plant plant = data.getPlant(code);
        if (plant == null) return display.plantNonExistentFailure(code);
        return display.plant(plant);
    }
    public String plantInventory(long userid, int page) {
        return display.plants(userid, data.getPlants(), page);
    }
}
