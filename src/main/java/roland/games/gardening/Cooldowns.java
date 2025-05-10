package roland.games.gardening;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {
    private Map<String, Long> endTimes = new HashMap<>();
    public long getEndTime(String name) {
        Long time = endTimes.get(name);
        if (time == null) return 0;
        return time;
    }
    public boolean add(String name, long length) {
        if (!hasEnded(name)) return false;
        endTimes.put(name, Utility.currentTime() + length);
        return true;
    }
    public boolean hasEnded(String name) {
        Long endTime = endTimes.get(name);
        if (endTime == null) return true;
        return Utility.currentTime() >= endTimes.get(name);
    }
}
