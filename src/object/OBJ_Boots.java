package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject {

    GamePanel gp;

    public OBJ_Boots(GamePanel gp) {

        this.gp = gp;
        
        name = "Boots of Swiftness";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots_of_swiftness.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
            
    }

}
