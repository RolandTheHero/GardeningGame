import org.junit.jupiter.api.*;

import roland.games.gardening.*;

public class Tests {
    @Test public void test_seedDistribution() {
        GardeningGame gg = new GardeningGame(new LocalDataHandler());
        for (int i = 0; i < 10; i++) System.out.println(gg.forage(0));
        System.out.println(gg.data().getInventory(0));
    }
    @Test public void test_harvest() {
        GardeningGame gg = new GardeningGame(new LocalDataHandler());
        gg.forage(0);
        gg.plant(0, Seed.COMMON);
        gg.harvest(0, 0);
        System.out.println(gg.data().getPlants());
    }
}
