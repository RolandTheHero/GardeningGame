package roland.games.gardening;

import java.util.Map;

public interface DisplayHandler<T> {
    public void forageSuccess(T cmdOrigin, Map<Seed, Integer> foragedSeeds);
    public void forageFailure(T cmdOrigin, long timeAvailable);
    public void inventory(T cmdOrigin, long userid, Inventory inventory);
    public void garden(T cmdOrigin, long userid, Garden garden);
    public void plantSeedNotExistentFailure(T cmdOrigin);
    public void plantSeedNotEnoughFailure(T cmdOrigin, Seed seed);
    public void plantSeedGardenFullFailure(T cmdOrigin);
    public void plantSeedSuccess(T cmdOrigin, Seed seed);
    public void harvestNotReadyFailure(T cmdOrigin, int plotNum);
    public void harvestEmptyFailure(T cmdOrigin, int plotNum);
    public void harvestSuccess(T cmdOrigin, Seed seed, Plant harvestedPlant);
    public void plant(T cmdOrigin, Plant plant);
    public void plantNonExistentFailure(T cmdOrigin, String code);
    public void plants(T cmdOrigin, long userid, Map<String, Plant> plants, int page);
}
