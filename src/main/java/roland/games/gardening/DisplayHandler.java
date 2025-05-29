package roland.games.gardening;

import java.util.Map;

public interface DisplayHandler<T> {
    /** Forage successful. */
    public void forageSuccess(T cmdOrigin, Map<Seed, Integer> foragedSeeds);
    /** Forage failed due to cooldown. */
    public void forageFailure(T cmdOrigin, long timeAvailable);
    /** User's inventory. */
    public void inventory(T cmdOrigin, long userid, Inventory inventory);
    /** User's garden. */
    public void garden(T cmdOrigin, long userid, Garden garden);
    /** Seed does not exist. */
    public void plantSeedNotExistentFailure(T cmdOrigin);
    /** User does not have the seed to plant. */
    public void plantSeedNotEnoughFailure(T cmdOrigin, Seed seed);
    /** Garden is full. */
    public void plantSeedGardenFullFailure(T cmdOrigin);
    /** Seed planted successfully. */
    public void plantSeedSuccess(T cmdOrigin, Seed seed);
    /** Plant is not ready to be harvested. */
    public void harvestNotReadyFailure(T cmdOrigin, int plotNum);
    /** There is no plant in the given plot number. */
    public void harvestEmptyFailure(T cmdOrigin, int plotNum);
    /** Harvest successful. */
    public void harvestSuccess(T cmdOrigin, Seed seed, Plant harvestedPlant);
    /** View plant details. */
    public void plant(T cmdOrigin, Plant plant);
    /** Given plant code does not exist. */
    public void plantNonExistentFailure(T cmdOrigin, String code);
    /** User's plant inventory. */
    public void plants(T cmdOrigin, long userid, Map<String, Plant> plants, int page);
}
