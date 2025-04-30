package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {

    public BufferedImage Key;
    public BufferedImage Boots;
    public BufferedImage HoldSelected;
    public BufferedImage Berry;
    public BufferedImage Knife;
    public BufferedImage Pickaxe;
    public BufferedImage Paper_Roll;
    public BufferedImage Sword;
    public BufferedImage Axe;
    public BufferedImage Carrots;
    public BufferedImage Potion;
    public BufferedImage WaterBottle;

    public void getItemImage() {
        Key = loadImage("/res/items/key.png");
        Boots = loadImage("/res/items/boots_of_swiftness.png");
        HoldSelected = loadImage("/res/items/selected.png");
        Berry = loadImage("/res/items/berry.png");
        Knife = loadImage("/res/items/knife.png");
        Pickaxe = loadImage("/res/items/pickaxe.png");
        Paper_Roll = loadImage("/res/items/paper_roll.png");
        Sword = loadImage("/res/items/sword.png");
        Axe = loadImage("/res/items/axe.png");
        Carrots = loadImage("/res/items/carrots.png");
        Potion = loadImage("/res/items/potion.png");
        WaterBottle = loadImage("/res/items/water_bottle.png");
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
