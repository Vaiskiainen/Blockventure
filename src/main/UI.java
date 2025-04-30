package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


import java.awt.Font;
import java.awt.FontFormatException;

public class UI {

    public boolean messageOn = false;
    public String message = "";
    public BufferedImage fullHealth, halfHealth, emptyHealth;
    int messageCounter = 0;
    public int commandNum = 0;
    public int pauseCommandNum = 0;
    public int gameOverCommandNum = 1;
    public int slotCol = 0;
    public int slotRow = 0;
    public int itemIndex = 0;
    public boolean enterPressed = false;
    public String holding = "none";

    Font Jersey;

    Graphics2D g2;
    GamePanel gp;
    Item item = new Item();
    KeyHandler keyH = new KeyHandler(gp);
    
    public UI(GamePanel gp) {
        this.gp = gp;
        item.getItemImage();
        

        
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/Jersey.ttf");
            Jersey = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40F);
            is = getClass().getResourceAsStream("/res/fonts/arial.ttf");
            fullHealth = ImageIO.read(getClass().getResourceAsStream("/res/guis/fullhealth.png"));
            halfHealth = ImageIO.read(getClass().getResourceAsStream("/res/guis/halfhealth.png"));
            emptyHealth = ImageIO.read(getClass().getResourceAsStream("/res/guis/emptyhealth.png"));
            
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(-99);
        }
        
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void drawMessage(String text, int x, int y) {
        g2.setFont(Jersey);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }
    public void drawChestItem(BufferedImage image, int chestX, int chestY) {
        g2.drawImage(image, chestX, chestY, gp.tileSize, gp.tileSize, null);
    }

    public void draw(Graphics2D g2) {
        // Assign the passed Graphics2D object to the class-level g2 variable
        this.g2 = g2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        g2.setColor(Color.white);
        drawHealth();
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            Color c = new Color(0, 0, 0, 200);
            int x = 458;
            int y = 500;
            g2.setColor(c);
            g2.fillRoundRect(456, 500, gp.tileSize, gp.tileSize, 15, 15);

            // Draw the border
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(456 + 2, 500 + 2, gp.tileSize, gp.tileSize, 15, 15);
            if(holding != "none") {
                switch(holding) {
                    case "Key":
                        g2.drawImage(item.Key, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Boots":
                        g2.drawImage(item.Boots, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Berry":
                        g2.drawImage(item.Berry, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Knife":
                        g2.drawImage(item.Knife, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Pickaxe":
                        g2.drawImage(item.Pickaxe, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Paper_Roll":
                        g2.drawImage(item.Paper_Roll, x, y, gp.tileSize, gp.tileSize, null);
                        break;    
                    case "Sword":
                        g2.drawImage(item.Sword, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Carrots":
                        g2.drawImage(item.Carrots, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Axe":
                        g2.drawImage(item.Axe, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Potion":
                        g2.drawImage(item.Potion, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Water_Bottle":
                        g2.drawImage(item.WaterBottle, x, y, gp.tileSize, gp.tileSize, null);
                        break;            
                }
                
                }
                
    
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // INVENTORY STATE
        if (gp.gameState == gp.inventoryState) {
            drawInventory();
            Color c = new Color(0, 0, 0, 200);
            int x = 458;
            int y = 500;
            g2.setColor(c);
            g2.fillRoundRect(456, 500, gp.tileSize, gp.tileSize, 15, 15);

            // Draw the border
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(456 + 2, 500 + 2, gp.tileSize, gp.tileSize, 15, 15);
            if(holding != "none") {
                switch(holding) {
                    case "Key":
                        g2.drawImage(item.Key, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Boots":
                        g2.drawImage(item.Boots, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Berry":
                        g2.drawImage(item.Berry, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Knife":
                        g2.drawImage(item.Knife, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Pickaxe":
                        g2.drawImage(item.Pickaxe, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Paper_Roll":
                        g2.drawImage(item.Paper_Roll, x, y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "Sword":
                        g2.drawImage(item.Sword, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Carrots":
                        g2.drawImage(item.Carrots, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Axe":
                        g2.drawImage(item.Axe, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Potion":
                        g2.drawImage(item.Potion, x, y, gp.tileSize, gp.tileSize, null);
                        break;
    
                    case "Water_Bottle":
                        g2.drawImage(item.WaterBottle, x, y, gp.tileSize, gp.tileSize, null);
                        break;                
                }
            }
    
    }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }

    public void drawTitleScreen() {

        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(Jersey.deriveFont(Font.BOLD, 96F));
        String text = "Blockventure";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPauseScreen() {
        
        g2.setFont(Jersey.deriveFont(Font.BOLD, 96F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2 - 50;

        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        text = "RESUME";
        g2.setFont(Jersey.deriveFont(Font.BOLD, 60F));
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 50;

        g2.setColor(Color.black);
        g2.drawString(text, x + 4, y + 4);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);


        if(pauseCommandNum == 0) {
            g2.setColor(Color.black);
            g2.drawString(">", x - gp.tileSize + 4, y + 4);

            g2.setColor(Color.white);
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "TITLE SCREEN";
        g2.setFont(Jersey.deriveFont(Font.BOLD, 60F));
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 125;

        g2.setColor(Color.black);
        g2.drawString(text, x + 4, y + 4);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(pauseCommandNum == 1) {
            g2.setColor(Color.black);
            g2.drawString(">", x - gp.tileSize +4, y +4);

            g2.setColor(Color.white);
            g2.drawString(">", x - gp.tileSize, y);
        }

    }
    public void drawDialogueScreen() {
        // Draw the dialogue box
        int x = gp.tileSize;
        int y = gp.tileSize * 4;
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 3;

        drawSubWindow(x, y, width, height);
    }
    public void drawGameOverScreen() {

        g2.setColor(new Color(102, 0, 19, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(Jersey.deriveFont(Font.BOLD, 96F));
        String text = "GAME OVER";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2 - 50;

        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        text = "TITLE SCREEN";
        g2.setFont(Jersey.deriveFont(Font.BOLD, 60F));
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + 125;

        g2.setColor(Color.black);
        g2.drawString(text, x + 4, y + 4);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if(gameOverCommandNum == 1) {
            g2.setColor(Color.black);
            g2.drawString(">", x - gp.tileSize +4, y +4);

            g2.setColor(Color.white);
            g2.drawString(">", x - gp.tileSize, y);
        }
    }
    public void drawHealth() {
        switch (gp.player.health) {
            case 0:
                g2.drawImage(emptyHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;

            case 1:
                g2.drawImage(halfHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;

            case 2:
                g2.drawImage(fullHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;

            case 3:
                g2.drawImage(fullHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(halfHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;

            case 4:
                g2.drawImage(fullHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(fullHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(emptyHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;

            case 5:
                g2.drawImage(fullHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(fullHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(halfHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;

            case 6:
                g2.drawImage(fullHealth, 35, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(fullHealth, 85, 30, gp.tileSize, gp.tileSize, null);
                g2.drawImage(fullHealth, 135, 30, gp.tileSize, gp.tileSize, null);
                break;
        }
    }
    public void drawInventory() {
        // Draw the inventory window

        

        int x = 312;
        int y = gp.tileSize;
        int width = 325;
        int height = gp.tileSize * 9;

        drawSubWindow(x, y, width, height);


        // SLOT
        final int slotXStart = x + 20;
        final int slotYStart = y + 20;
        int slotX = slotXStart;
        int slotY = slotYStart + 30;

        // CURSOR
        int cursorX = slotX + (slotCol * gp.tileSize);
        int cursorY = slotY + (slotRow * gp.tileSize);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
       


        // ITEMS


        while(gp.gameState == gp.inventoryState && itemIndex < gp.player.inventory.size()) {
        
            
            switch (gp.player.inventory.get(itemIndex)) {
                case "Key":
                    g2.drawImage(item.Key, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Boots":
                    g2.drawImage(item.Boots, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Berry":
                    g2.drawImage(item.Berry, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Knife":
                    g2.drawImage(item.Knife, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Pickaxe":
                    g2.drawImage(item.Pickaxe, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Paper_Roll":
                    g2.drawImage(item.Paper_Roll, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Sword":
                    g2.drawImage(item.Sword, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Carrots":
                    g2.drawImage(item.Carrots, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Axe":
                    g2.drawImage(item.Axe, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Potion":
                    g2.drawImage(item.Potion, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "Water_Bottle":
                    g2.drawImage(item.WaterBottle, slotX, slotY, gp.tileSize, gp.tileSize, null);
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                case "none":
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;

                default:
                    System.out.println("[DEBUG] No matching case for item: " + gp.player.inventory.get(itemIndex));
                    itemIndex++;
                    slotX += gp.tileSize;
                    break;
            }
            
            if(slotX >= slotXStart + (gp.tileSize * 6)) {
                slotX = slotXStart;
                slotY += gp.tileSize;
            }
        }
        
        if(enterPressed == true) {
            if(slotCol + slotRow * 6 < gp.player.inventory.size()) {
                if(holding != "none") {
                    gp.player.inventory.add(holding);
                }
                holding = gp.player.inventory.get(slotCol + slotRow * 6);
                gp.player.inventory.remove(slotCol + slotRow * 6);
                
                System.out.println("Holding item: " + holding);
            } else {
                System.out.println("No item to hold at index: " + (slotCol + slotRow * 6));
            }
            enterPressed = false;
        }
        
        itemIndex = 0;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        // Draw the player's inventory items
        g2.setFont(Jersey.deriveFont(Font.PLAIN, 40F));
        g2.setColor(Color.white);
        g2.drawString("Inventory", x + 20, y + 40);
    }


    
    public void drawSubWindow(int x, int y, int width, int height) {
        // Draw the window
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 20, 20);

        // Draw the border
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 2, y + 2, width, height, 20, 20);
    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

}