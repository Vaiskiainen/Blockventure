package object;

import java.io.IOException;

import javax.imageio.ImageIO;


public class OBJ_BerryBush extends SuperObject{


    public OBJ_BerryBush() {
        
        name = "BerryBush";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/berrybush.png"));
            System.out.println("Loaded image: BerryBush");

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        collision = true;
    }

}
