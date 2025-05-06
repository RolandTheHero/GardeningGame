import org.junit.jupiter.api.*;

import roland.games.gardening.*;

public class Tests {
    @Test public void test_seedDistribution() {
        GardeningGame gg = new GardeningGame(new LocalDataHandler(), new ConsoleOutputDisplayHandler());
        for (int i = 0; i < 10; i++) System.out.println(gg.forage(0));
        System.out.println(gg.seedInventory(0));
    }
    @Test public void test_forage() {
        GardeningGame gg = new GardeningGame(new LocalDataHandler(), new ConsoleOutputDisplayHandler());
        System.out.println(gg.forage(0));
    }
}
