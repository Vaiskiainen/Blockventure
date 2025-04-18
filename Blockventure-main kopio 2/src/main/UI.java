package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import item.ItemManager;

import java.awt.Font;
import java.awt.FontFormatException;

public class UI {

    public boolean messageOn = false;
    public String message = "";
    public BufferedImage inventoryImage;
    int messageCounter = 0;
    public int commandNum = 0;
    public int pauseCommandNum = 0;

    Font Jersey;

    Graphics2D g2;
    GamePanel gp;
    ItemManager itemM;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        itemM = new ItemManager(gp);
        loadInventoryImage();

        
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/Jersey.ttf");
            Jersey = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40F);
            is = getClass().getResourceAsStream("/res/fonts/arial.ttf");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
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

    public void draw(Graphics2D g2) {
        // Assign the passed Graphics2D object to the class-level g2 variable
        this.g2 = g2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // Add any play state-specific drawing logic here
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // INVENTORY STATE
        if (gp.gameState == gp.inventoryState) {
            drawInventory();
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
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
    public void loadInventoryImage() {
        // Load the inventory image
        setup("testinv");
    }
    public void drawInventory() {
        // Draw inventory screen
        g2.drawImage(inventoryImage, 150, 70, 450, 450, null);
    }


    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();

        try {

            inventoryImage = ImageIO.read(getClass().getResourceAsStream("/res/guis/" + imageName + ".png"));
            System.out.println("Loaded image: " + imageName);

        }catch(IOException e) {
            e.printStackTrace();
}
        return inventoryImage;
    }
}