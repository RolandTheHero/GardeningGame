package roland.games.gardening;

import java.awt.Color;

public class Plant {
    final String code;
    long ownerid;
    final long acquisitionTime;

    String name;
    final Color colour;
    final Trait[] traits;

    private Plant(String code, long acquisitionTime, String name, Color colour, Trait[] traits) {
        this.code = code;
        this.ownerid = 0;
        this.acquisitionTime = acquisitionTime;
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

    static public Plant generateCommon(String code) {
        return new Plant(code, Utility.currentTime(), "Common Plant", Color.BLUE, new Trait[] {new CommonTrait("Pretty")});
    }
}
