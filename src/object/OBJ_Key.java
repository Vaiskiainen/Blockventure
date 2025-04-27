package object;

import java.io.IOException;

import javax.imageio.ImageIO;


public class OBJ_Key extends SuperObject {

    public OBJ_Key() {
        
        name = "Key";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/items/key.png"));
            System.out.println("Loaded image: Key");

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

            
    }
}
