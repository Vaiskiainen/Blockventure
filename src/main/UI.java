package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


import java.awt.Font;
import java.awt.FontFormatException;

public class UI {

    public boolean messageOn = false;
    public String message = "";
    public BufferedImage inventoryImage;
    int messageCounter = 0;
    public int commandNum = 0;
    public int pauseCommandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    public int itemIndex = 0;

    Font Jersey;

    Graphics2D g2;
    GamePanel gp;
    Item item = new Item();
    KeyHandler keyH = new KeyHandler(gp);
    
    public UI(GamePanel gp) {
        this.gp = gp;
        

        
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
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
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
    public void drawInventory() {
        // Draw the inventory window

        
        int x = gp.tileSize * 5;
        int y = gp.tileSize;
        int width = gp.tileSize * 7;
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
        
        item.getItemImage();
        switch(gp.player.inventory.get(itemIndex)) {
            case "Key":
                g2.drawImage(item.Key, slotX, slotY, gp.tileSize, gp.tileSize, null);
                itemIndex++;
                slotX += gp.tileSize;

        
        
    }
    if(slotX >= slotXStart + (gp.tileSize * 6)) {
        slotX = slotXStart;
        slotY += gp.tileSize;
    }
    

    
        
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

// Removed the Entity class as it is now in its own file