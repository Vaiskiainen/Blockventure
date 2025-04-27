package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_LootedChest extends SuperObject{


    public OBJ_LootedChest() {
        
        name = "LootedChest";
        try {
            // CHANGE THIS TO THE LOOTED 
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/openedchest.png"));
            System.out.println("Loaded image: LootedChest");

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        collision = true;
    }

}
