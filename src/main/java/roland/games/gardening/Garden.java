package roland.games.gardening;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Garden {
    public record Plot(Seed seed, long harvestableAt) {}

    private int size;
    private List<Plot> growingPlants;

    public Garden(int size) {
        this.size = size;
        growingPlants = new ArrayList<>();
    }

    public int size() { return size; }
    public List<Plot> getPlots() { return Collections.unmodifiableList(growingPlants); }

    public boolean isFull() { return growingPlants.size() >= size; }
    public void expand() { size++; }

    public void plant(Seed seed) {
        if (isFull()) throw new Error();
        growingPlants.add(new Plot(seed, Utility.currentTime() + seed.growthTime()));
    }
    public boolean plotOccupied(int plotNum) { return growingPlants.size() > plotNum; }
    public boolean canHarvest(int plotNum) { return plotOccupied(plotNum) && Utility.currentTime() >= growingPlants.get(plotNum).harvestableAt(); }
    public Plant harvest(int plotNum) {
        if (!canHarvest(plotNum)) throw new Error();
        return growingPlants.remove(plotNum).seed().grow();
    }
    public Seed getSeed(int plotNum) {
        if (!plotOccupied(plotNum)) throw new Error();
        return growingPlants.get(plotNum).seed();
    }
}
