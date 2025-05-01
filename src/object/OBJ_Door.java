package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{

    public OBJ_Door() {
        
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            System.out.println("Loaded image: Door");

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        collision = true;
    }
}
