package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UI;
import main.UtilityTool;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_LootedChest;
import object.OBJ_Bush;
import object.OBJ_BerryBush;

public class Player extends Entity {
    
    GamePanel gp;
    KeyHandler keyH;
    public UI ui;
    
    public final int screenX;
    public final int screenY;
    public ArrayList<String> inventory = new ArrayList<>();
    public ArrayList<String> lootTable = new ArrayList<>();
    
    Random rand = new Random();
    public final int inventorySize = 24;
    public String holding;
    public boolean enterPressed = false;
    public int health = 3;
    public int maxHealth = 6;
    public int defaultHealth = 3;
    public boolean ePressed = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // Explicitly call the constructor of the Entity class
        this.gp = gp;
        this.keyH = keyH;
        this.ui = gp.ui;
        Graphics2D g2;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // HITBOX FOR PLAYER

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 26;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 17;

        setDefaultValues();
        getPlayerImage();
        setItems();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 30;
        speed = 4;
        direction = "down";

        lootTable.add("Boots");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Berry");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Carrots");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Sword");
        lootTable.add("Paper_Roll");
        lootTable.add("Paper_Roll");
        lootTable.add("Paper_Roll");
        lootTable.add("Paper_Roll");
        lootTable.add("Paper_Roll");
        lootTable.add("Paper_Roll");
        lootTable.add("Potion");
        lootTable.add("Potion");
        lootTable.add("Potion");
    }
    
    public void setItems() {
        inventory.add("Key");
    }

    
    public void getPlayerImage() {

        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    } 
    
    public BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return image;
    }
    
    public void update() {
        if (ui.holding == "Boots") {
            speed = 6;
        } else {
            speed = 4;
        }
        if (health <= 0) {
            gp.gameState = gp.gameOverState;
        }

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }

                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            }

            // CHECK IF PLAYER IS OUT OF BOUNDS
            if (worldX < 0 || worldY < 0  || worldX > gp.worldWidth - gp.tileSize || worldY > gp.worldHeight - 50) {
                health = 0;
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].name;
            switch(objectName) {
                case "Key":
                    gp.obj[i] = null;
                    inventory.add("Key");
                case "Chest":
                    lootChest(0,  i);
                    break;
                case "BerryBush":
                    takeBerry(i); 
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {

        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
    public void interactWithHoldingItem() {
        switch (ui.holding) {
        case "Berry": 
            if (health < maxHealth) {
                health += 1;
                ui.holding = "none";
                break;
            }else {
                break;
            }
        case "Carrots": 
            if (health < maxHealth) {
                health += 2;
                ui.holding = "none";
                break;
            }else {
                break;
            }
        case "Potion": 
            if (health < maxHealth) {
                health = maxHealth;
                ui.holding = "none";
                break;
            }else {
                break;
            }
        case "Sword":
            if(health > 0) {
                health -= 1;
                break;
            } else {
                break;
            }
        case "Raw_Berry":
            if(health > 0) {
                ui.holding = "none";
                health -= 1;
                break;
            } else {
                break;
            }
        }
            
        
    }
    public void lootChest(int rarity, int objNum) {
        int randomElement = rand.nextInt(lootTable.size());
        String loot = lootTable.get(randomElement);
        if(ui.holding == "Key") {
        ui.holding = "none";
        inventory.add(loot);
        int x = gp.obj[objNum].worldX;
        int y = gp.obj[objNum].worldY;
        gp.obj[objNum] = null;
        gp.obj[objNum] = new OBJ_LootedChest();
        gp.obj[objNum].worldX = x;
        gp.obj[objNum].worldY = y;
        }
    
}
public void takeBerry(int objNum) {
    int rawRandom = rand.nextInt(100);
    if(rawRandom >= 10) {
    inventory.add("Berry");
    int x = gp.obj[objNum].worldX;
    int y = gp.obj[objNum].worldY;
    gp.obj[objNum] = null;
    gp.obj[objNum] = new OBJ_Bush();
    gp.obj[objNum].worldX = x;
    gp.obj[objNum].worldY = y;
    } else {
    inventory.add("Raw_Berry");
    int x = gp.obj[objNum].worldX;
    int y = gp.obj[objNum].worldY;
    gp.obj[objNum] = null;
    gp.obj[objNum] = new OBJ_Bush();
    gp.obj[objNum].worldX = x;
    gp.obj[objNum].worldY = y;
    }

}
}
