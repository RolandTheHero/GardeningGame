package roland.games.gardening;

import java.util.HashMap;
import java.util.Map;

public record GardeningGame<T>(DataHandler data, DisplayHandler<T> display) {
    static final private double seedRareChance = 0.04d;
    static final private long forageCooldown = 600L; // Seconds

    public void forage(T cmdOrigin, long userid) {
        data.addUser(userid);
        if (!data.addCooldown(userid, "forage", forageCooldown)) {
            display.forageFailure(cmdOrigin, data.getCooldown(userid, "forage"));
            return;
        }
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
        display.forageSuccess(cmdOrigin, foragedSeeds);
    }
    public void seedInventory(T cmdOrigin, long userid) {
        data.addUser(userid);
        display.inventory(cmdOrigin, data.getInventory(userid));
    }
    public void plantSeed(T cmdOrigin, long userid, Seed seed) {
        data.addUser(userid);
        if (data.getGarden(userid).isFull()) {
            display.plantSeedGardenFullFailure(cmdOrigin);
            return;
        }
        Integer quantityInInventory = data.getInventory(userid).getSeeds().get(seed);
        if (quantityInInventory == null || quantityInInventory < 1) {
            display.plantSeedNotEnoughFailure(cmdOrigin, seed);
            return;
        }
        data.subtractFromInventory(userid, Map.of(seed, 1));
        data.getGarden(userid).plant(seed);
        display.plantSeedSuccess(cmdOrigin, seed);
    }
    public void harvest(T cmdOrigin, long userid, int plotNum) {
        data.addUser(userid);
        Garden garden = data.getGarden(userid);
        if (!garden.plotOccupied(plotNum)) {
            display.harvestEmptyFailure(cmdOrigin, plotNum);
            return;
        }
        if (!garden.canHarvest(plotNum)) {
            display.harvestNotReadyFailure(cmdOrigin, plotNum);
            return;
        }
        Seed fromSeed = garden.getSeed(plotNum);
        Plant harvestedPlant = data.getGarden(userid).harvest(plotNum);
        data.save(harvestedPlant);
        display.harvestSuccess(cmdOrigin, fromSeed, harvestedPlant);
    }
    public void viewPlant(T cmdOrigin, String code) {
        Plant plant = data.getPlant(code);
        if (plant == null) {
            display.plantNonExistentFailure(cmdOrigin, code);
            return;
        }
        display.plant(cmdOrigin, plant);
    }
    public void plantInventory(T cmdOrigin, long userid, int page) {
        display.plants(cmdOrigin, userid, data.getPlants(), page);
    }
}
