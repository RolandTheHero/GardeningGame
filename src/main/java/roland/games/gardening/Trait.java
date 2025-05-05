package roland.games.gardening;

enum TraitRarity {
    COMMON,
    RARE,
    MAGICAL;
}

interface Trait {
    public String name();
    public TraitRarity rarity();
}

record CommonTrait(String name) implements Trait {
    public TraitRarity rarity() { return TraitRarity.COMMON; }
}
