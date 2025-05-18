package object;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject {

    public boolean isRandomLoot = true; // Determines if the chest uses random loot
    public String specificItem = null; // The specific item to give if not random

    public OBJ_Chest() {
  
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
            System.out.println("Loaded image: Chest");

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        collision = true;
    }

    public String openChest(List<String> lootTable) {
        if (isRandomLoot) {
            // Use the random loot table
            Random random = new Random();
            int randomIndex = random.nextInt(lootTable.size());
            return lootTable.get(randomIndex);
        } else {
            // Always give the specific item
            return specificItem;
        }
    }
}
