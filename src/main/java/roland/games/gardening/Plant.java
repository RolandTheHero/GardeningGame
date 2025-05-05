package roland.games.gardening;

import java.awt.Color;

public class Plant {
    String code;
    long ownerid;
    long acquisitionTime;

    String name;
    Color colour;
    Trait[] traits;

    public Plant(String name, Color colour, Trait[] traits) {
        acquisitionTime = Utility.currentTime();
        this.name = name;
        this.colour = colour;
        this.traits = traits;
    }
    public void setOwner(long userid) { ownerid = userid; }
    public String code() { return code; }
    public long ownerid() { return ownerid; }
    public long acquisitionTime() { return acquisitionTime; }
    public String name() { return name; }
    public Color colour() { return colour; }
    public Trait[] traits() { return traits; }

    static public Plant generateCommon() {
        return new Plant("Common Plant", Color.BLUE, new Trait[] {new CommonTrait("Pretty")});
    }
}
