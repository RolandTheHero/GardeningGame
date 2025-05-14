package roland.games.gardening;

public enum Seed {
    COMMON {
        public long growthTime() { return 600L; }
        public Plant grow() { return Plant.generateCommon(); }
        public String fullName() { return "Seed"; }
        public String codeName() { return "common"; }
    },
    RARE {
        public long growthTime() { return 10800L; }
        public Plant grow() { return Plant.generateCommon(); }
        public String fullName() { return "Rare Seed"; }
        public String codeName() { return "rare"; }
    },
    MAGICAL {
        public long growthTime() { return 86400L; }
        public Plant grow() { return Plant.generateCommon(); }
        public String fullName() { return "Magical Seed"; }
        public String codeName() { return "magical"; }
    };
    public abstract long growthTime();
    public abstract Plant grow();
    public abstract String fullName();
    public abstract String codeName();
}
