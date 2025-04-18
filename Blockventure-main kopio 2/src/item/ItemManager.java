package item;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import main.GamePanel;
import main.UtilityTool;

public class ItemManager {

    GamePanel gp;
    Graphics2D g2;

    public Item[] item;
    public int index = 0;
    int listIndex = 0;

    public ItemManager(GamePanel gp) {

        this.gp = gp;

        item = new Item[10];

        getItemImage();
    }
    public void draw(Graphics2D g2) {
        ArrayList<Integer> invList = new ArrayList<>();
        invList.clear();
        invList.add(1);
        invList.add(0);
        invList.add(0);
        invList.add(1);
        invList.add(1);
        invList.add(1);
        invList.add(1);
        invList.add(1);
        this.g2 = g2;
        if(gp.gameState == gp.inventoryState) {
            listIndex = 0;
            while(index < 8 && listIndex <  8) {
                drawItem((invList.get(listIndex)), gp.tileSize * index, 85);
                index++;
                listIndex++;
        }
        index = 0;
        
        }

    }
    
    
    public void drawItem(int itemID, int x, int y) {
        g2.drawImage(item[itemID].image, x + 180, y, null);
    }
    public void getItemImage() {
        setup(0, "emptyslot");
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

