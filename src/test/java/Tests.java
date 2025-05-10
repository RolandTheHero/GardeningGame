import org.junit.jupiter.api.*;

import roland.games.gardening.*;

/*
 * Use terminal command: mvn -Dtest=Tests test
 */
public class Tests {
    @Test public void test_seedDistribution() {
        GardeningGame<String> gg = new GardeningGame<>(new LocalDataHandler(), new ConsoleOutputDisplayHandler());
        for (int i = 0; i < 10; i++) gg.forage(null, 0);
        gg.seedInventory(null, 0);
    }
}
