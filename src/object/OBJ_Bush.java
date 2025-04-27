package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Bush extends SuperObject{

    public OBJ_Bush() {

        
        name = "Bush";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/bush.png"));
            System.out.println("Loaded image: Bush");

        }catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        collision = true;
    }

}
