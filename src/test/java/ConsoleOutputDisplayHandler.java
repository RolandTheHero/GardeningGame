import java.util.Map;
import java.util.stream.Collectors;

import roland.games.gardening.*;

public class ConsoleOutputDisplayHandler implements DisplayHandler {
    static final private String separator = "--------------------\n";
    static final private int pageSize = 10;

    @Override public String forageSuccess(Map<Seed, Integer> foragedSeeds) {
        String seedsOutput = foragedSeeds.entrySet().stream().map(e -> e.getValue() + " " + e.getKey()).collect(Collectors.joining("\n"));
        return separator + "You found\n" + seedsOutput;
    }
    @Override public String forageFailure(long timeAvailable) {
        return separator + "You can forage again in " + (timeAvailable - System.currentTimeMillis()/1000L) + " seconds.";
    }
    @Override public String inventory(Inventory inventory) {
        String seedsOutput = inventory.getSeeds().entrySet().stream().map(e -> e.getValue() + " " + e.getKey()).collect(Collectors.joining("\n"));
        return separator + "Your Inventory\n" + seedsOutput;
    }
    @Override public String garden(Garden garden) {
        String output = garden.getPlots().stream().map(p -> p.seed() + " " + (p.harvestableAt() - System.currentTimeMillis()/1000L)).collect(Collectors.joining("\n"));
        return separator + "Your Garden\n" + output;
    }
    @Override public String plantSeedGardenFullFailure() {
        return separator + "You cannot plant anymore seeds in your garden — it's full!";
    }
    @Override public String plantSeedNotEnoughFailure(Seed seed) {
        return separator + "You need at least 1 " + seed + " seed to plant it.";
    }
    @Override public String plantSeedSuccess(Seed seed) {
        return separator + "You planted a " + seed + " seed in your garden.";
    }
    @Override public String plant(Plant plant) {
        return separator + plantNoSeparator(plant);
    }
    private String plantNoSeparator(Plant plant) {
        return plant.name() + 
            "\nCode: " + plant.code() +
            "\nOwner: " + plant.ownerid() +
            "\nAcquisition Date: " + plant.acquisitionTime() + 
            "\nColour: " + plant.colour() + 
            "\n" + plant.traits();
    }
    private String plants(Map<String, Plant> plants) {
        return plants.entrySet().stream().map(e -> {
            Plant p = e.getValue();
            return p.code() + " " + p.name() + " " + p.traits();
        }).collect(Collectors.joining("\n"));
    }
    @Override public String plants(long userid, Map<String, Plant> plants, int page) {
        Map<String, Plant> plantsOwned = plants.entrySet().stream()
            .filter(e -> e.getValue().ownerid() == userid)
            .skip(pageSize * page)
            .limit(pageSize)
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        return separator + "Your plants:\n" + plants(plantsOwned);
    }
    @Override public String harvestNotReadyFailure(int plotNum) {
        return separator + "The plant in plot " + plotNum + " isn't ready to be harvested. View your garden to see when it's ready.";
    }
    @Override public String harvestEmptyFailure(int plotNum) {
        return separator + "There is no plant in plot " + plotNum + ". View your garden to see your growing plants.";
    }
    @Override public String harvestSuccess(Seed seed, Plant harvestedPlant) {
        return separator + "Your " + seed + " seed grew into:\n" + plantNoSeparator(harvestedPlant);
    }
    @Override public String plantNonExistentFailure(String code) {
        return separator + "There is no plant with the code " + code + ".";
    }
}
