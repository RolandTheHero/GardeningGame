package roland.games.gardening;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Seed, Integer> seeds = new HashMap<>();

    public Map<Seed, Integer> getSeeds() { return Collections.unmodifiableMap(seeds); }
    
    public void addSeeds(Map<Seed, Integer> seeds) {
        for (Map.Entry<Seed, Integer> e : seeds.entrySet()) {
            if (this.seeds.get(e.getKey()) == null) this.seeds.put(e.getKey(), 0);
            this.seeds.put(e.getKey(), this.seeds.get(e.getKey()) + e.getValue());
        }
    }
    public String toString() {
        return seeds.toString();
    }
}
