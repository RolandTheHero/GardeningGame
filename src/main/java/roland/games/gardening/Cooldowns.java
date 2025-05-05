package roland.games.gardening;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {
    private Map<String, Long> endTimes = new HashMap<>();
    public void add(String name, long length) {
        assert hasEnded(name);
        endTimes.put(name, Utility.currentTime() + length);
    }
    public boolean hasEnded(String name) { return Utility.currentTime() >= endTimes.get(name); }
}
