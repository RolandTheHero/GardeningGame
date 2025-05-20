package roland.games.gardening;

import java.awt.Color;

public class Plant {
    final String code;
    long ownerid;
    final long acquisitionTime;

    final Color colour;
    final Trait[] traits;

    Plant(String code, long acquisitionTime, Color colour, Trait[] traits) {
        this.code = code;
        this.ownerid = 0;
        this.acquisitionTime = acquisitionTime;
        this.colour = colour;
        this.traits = traits;
    }
    public void setOwner(long userid) { ownerid = userid; }
    public String code() { return code; }
    public long ownerid() { return ownerid; }
    public long acquisitionTime() { return acquisitionTime; }
    public Color colour() { return colour; }
    public Trait[] traits() { return traits; }
    static public Plant generateCommon(String code) {
        Color colour = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        return new Plant(code, Utility.currentTime(), colour, new Trait[] {new CommonTrait("Pretty")});
    }
}
