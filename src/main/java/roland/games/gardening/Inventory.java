package roland.games.gardening;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Seed, Integer> seeds = new HashMap<>();

    public Map<Seed, Integer> getSeeds() { return Collections.unmodifiableMap(seeds); }
    
    public void addSeeds(Map<Seed, Integer> seeds) {
        for (Map.Entry<Seed, Integer> e : seeds.entrySet()) {
            Seed seed = e.getKey();
            if (this.seeds.get(seed) == null) this.seeds.put(seed, 0);
            this.seeds.put(seed, this.seeds.get(seed) + e.getValue());
            if (this.seeds.get(seed) == 0) this.seeds.remove(seed);
        }
    }
    public String toString() {
        return seeds.toString();
    }
}
