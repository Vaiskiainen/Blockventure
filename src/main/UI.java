package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Font;

public class UI {

    public boolean messageOn = false;
    public String message = "";
    public BufferedImage inventoryImage;
    int messageCounter = 0;
    public int commandNum = 0;

    Font arial_40;

    Graphics2D g2;
    GamePanel gp;
    
    public UI(GamePanel gp) {
        this.gp = gp;
        loadInventoryImage();

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE

        if(gp.gameState == gp.playState) {

        }

        // PAUSE STATE

        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        if(gp.gameState == gp.inventoryState) {
            drawInventory();
        }

    }
    public void drawTitleScreen() {

        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
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
        
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2 - 30;

        g2.drawString(text, x, y);

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