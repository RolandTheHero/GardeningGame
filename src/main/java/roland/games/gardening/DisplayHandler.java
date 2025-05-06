package roland.games.gardening;

import java.util.Map;

public interface DisplayHandler {
    public String forageSuccess(Map<Seed, Integer> foragedSeeds);
    public String forageFailure(long timeAvailable);
    public String inventory(Inventory inventory);
    public String garden(Garden garden);
    public String plantSeedNotEnoughFailure(Seed seed);
    public String plantSeedGardenFullFailure();
    public String plantSeedSuccess(Seed seed);
    public String harvestNotReadyFailure(int plotNum);
    public String harvestEmptyFailure(int plotNum);
    public String harvestSuccess(Seed seed, Plant harvestedPlant);
    public String plant(Plant plant);
    public String plantNonExistentFailure(String code);
    public String plants(long userid, Map<String, Plant> plants, int page);
}
