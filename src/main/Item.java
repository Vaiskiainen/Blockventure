package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Item {

    public BufferedImage Key, Boots, Berry,
            Knife, Pickaxe, Paper_Roll, Sword, Axe, Carrots, Potion,
            Water_Bottle, Raw_Berry, MissingTexture;

    public Map<String, String> descriptions = new HashMap<>();

    public void getItemImage() {
        Key = loadImage("/res/items/key.png");
        Boots = loadImage("/res/items/boots_of_swiftness.png");
        Berry = loadImage("/res/items/berry.png");
        Knife = loadImage("/res/items/knife.png");
        Pickaxe = loadImage("/res/items/pickaxe.png");
        Paper_Roll = loadImage("/res/items/paper_roll.png");
        Sword = loadImage("/res/items/sword.png");
        Axe = loadImage("/res/items/axe.png");
        Carrots = loadImage("/res/items/carrots.png");
        Potion = loadImage("/res/items/potion.png");
        Water_Bottle = loadImage("/res/items/water_bottle.png");
        Raw_Berry = loadImage("/res/items/rawberry.png");
        MissingTexture = loadImage("/res/items/missingtexture.png");


        initializeDescriptions();
    }

    private void initializeDescriptions() {


        descriptions.put("Key", "A small key that opens locked doors.");
        descriptions.put("Boots", "Boots of swiftness that increase your speed.\n\nStats:\n" +
                "Speed: +2");
        descriptions.put("Berry", "A delicious berry that restores health.\n\nStats:\n" +
                "Restores: 1");
        descriptions.put("Knife", "A sharp knife for cutting and combat.\n\nStats:\n" +
                "Attack: 2\n" +
                "Durability: 50");
        descriptions.put("Pickaxe", "A tool for mining rocks and ores.\n\nStats:\n" +
                "Durability: 100");
        descriptions.put("Paper Roll", "A roll of paper with mysterious writings.");
        descriptions.put("Sword", "A powerful sword for combat.\n\nStats:\n" +
                "Attack: 5\n" +
                "Durability: 100");
        descriptions.put("Axe", "A sturdy axe for chopping wood.\n\nStats:\n" +
                "Attack: 3\n" +
                "Durability: 75");
        descriptions.put("Carrots", "A bunch of carrots to restore health.\n\nStats:\n" +
                "Restores: 2");
        descriptions.put("Potion", "A magical potion that restores health.\n\nStats:\n" +
                "Restores: All");
        descriptions.put("Water Bottle", "A bottle of water to quench your thirst.");
        descriptions.put("Raw Berry", "An unripe berry, not very tasty.");
        descriptions.put("MissingTexture", "");
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDescription(String itemName) {
        return descriptions.getOrDefault(itemName, "No description available.");
    }
}
