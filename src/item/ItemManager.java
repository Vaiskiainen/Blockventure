package item;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import tile.Tile;
import main.GamePanel;

public class ItemManager {

    GamePanel gp;

    public Item[] item;

    public ItemManager(GamePanel gp) {

        this.gp = gp;

        item = new Item[10];

        getItemImage();
    }
    public void getItemImage() {
        setup(1, "key");
}
public void setup(int itemID, String imageName) {
        UtilityTool uTool = new UtilityTool();

        try {

            item[itemID] = new Item();
            item[itemID].image = ImageIO.read(getClass().getResourceAsStream("/res/items/" + imageName + ".png"));
            item[itemID].image = uTool.scaleImage(item[itemID].image, gp.tileSize, gp.tileSize);
            System.out.println("Loaded image: " + imageName);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
