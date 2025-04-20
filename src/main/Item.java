package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {

    public BufferedImage Key;
    
    public void getItemImage() {
        
        try {
            
        Key = ImageIO.read(getClass().getResourceAsStream("/res/items/key.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
}
}
