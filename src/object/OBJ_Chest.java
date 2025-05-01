package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{

    public OBJ_Chest() {
  
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
            System.out.println("Loaded image: Chest");

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        collision = true;
    }
}
