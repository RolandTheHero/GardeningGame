package roland.games.gardening;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleOutputDisplayHandler implements DisplayHandler<String> {
    static final private String separator = "--------------------\n";
    static final private int pageSize = 10;

    @Override public void forageSuccess(String cmdOrigin, Map<Seed, Integer> foragedSeeds) {
        String seedsOutput = foragedSeeds.entrySet().stream().map(e -> e.getValue() + " " + e.getKey()).collect(Collectors.joining("\n"));
        System.out.println(separator + "You found\n" + seedsOutput);
    }
    @Override public void forageFailure(String cmdOrigin, long timeAvailable) {
        System.out.println(separator + "You can forage again in " + (timeAvailable - System.currentTimeMillis()/1000L) + " seconds.");
    }
    @Override public void inventory(String cmdOrigin, Inventory inventory) {
        String seedsOutput = inventory.getSeeds().entrySet().stream().map(e -> e.getValue() + " " + e.getKey()).collect(Collectors.joining("\n"));
        System.out.println(separator + "Your Inventory\n" + seedsOutput);
    }
    @Override public void garden(String cmdOrigin, Garden garden) {
        String output = garden.getPlots().stream().map(p -> p.seed() + " " + (p.harvestableAt() - System.currentTimeMillis()/1000L)).collect(Collectors.joining("\n"));
        System.out.println(separator + "Your Garden\n" + output);
    }
    @Override public void plantSeedGardenFullFailure(String cmdOrigin) {
        System.out.println(separator + "You cannot plant anymore seeds in your garden — it's full!");
    }
    @Override public void plantSeedNotEnoughFailure(String cmdOrigin, Seed seed) {
        System.out.println(separator + "You need at least 1 " + seed + " seed to plant it.");
    }
    @Override public void plantSeedSuccess(String cmdOrigin, Seed seed) {
        System.out.println(separator + "You planted a " + seed + " seed in your garden.");
    }
    @Override public void plant(String cmdOrigin, Plant plant) {
        System.out.println(separator + plantNoSeparator(plant));
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
    @Override public void plants(String cmdOrigin, long userid, Map<String, Plant> plants, int page) {
        Map<String, Plant> plantsOwned = plants.entrySet().stream()
            .filter(e -> e.getValue().ownerid() == userid)
            .skip(pageSize * page)
            .limit(pageSize)
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        System.out.println(separator + "Your plants:\n" + plants(plantsOwned));
    }
    @Override public void harvestNotReadyFailure(String cmdOrigin, int plotNum) {
        System.out.println(separator + "The plant in plot " + plotNum + " isn't ready to be harvested. View your garden to see when it's ready.");
    }
    @Override public void harvestEmptyFailure(String cmdOrigin, int plotNum) {
        System.out.println(separator + "There is no plant in plot " + plotNum + ". View your garden to see your growing plants.");
    }
    @Override public void harvestSuccess(String cmdOrigin, Seed seed, Plant harvestedPlant) {
        System.out.println(separator + "Your " + seed + " seed grew into:\n" + plantNoSeparator(harvestedPlant));
    }
    @Override public void plantNonExistentFailure(String cmdOrigin, String code) {
        System.out.println(separator + "There is no plant with the code " + code + ".");
    }
}
