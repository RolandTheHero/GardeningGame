package roland.games.gardening;

import java.util.List;
import java.util.ArrayList;

public class Garden {
    private record Plot(Seed seed, long harvestableAt) {}

    private int size;
    private List<Plot> growingPlants;

    public Garden(int size) {
        this.size = size;
        growingPlants = new ArrayList<>();
    }

    public boolean isFull() { return growingPlants.size() >= size; }
    public void expand() { size++; }
    public void plant(Seed seed) {
        if (isFull()) throw new Error();
        growingPlants.add(new Plot(seed, Utility.currentTime() + seed.growthTime()));
    }
    public boolean canHarvest(int plotNum) {
        return Utility.currentTime() >= growingPlants.get(plotNum).harvestableAt();
    }
    public Plant harvest(int plotNum) {
        if (canHarvest(plotNum)) return growingPlants.remove(plotNum).seed().grow();
        throw new Error();
    }
}
