import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
    @Test public void orderTraits() throws IOException {
        Path pathIn = Paths.get("src/main/java/roland/games/gardening/traits2.txt");
        Path pathOut = Paths.get("src/main/java/roland/games/gardening/traits.txt");
        Scanner scanner = new Scanner(pathIn);
        List<String> traits = new ArrayList<>();
        while (scanner.hasNextLine()) traits.add(scanner.nextLine());
        Collections.sort(traits);
        FileWriter writer = new FileWriter(pathOut.toString());
        for (String trait : traits) writer.write(trait + "\n");
        writer.close();
        scanner.close();
    }
}
