package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.Font;
import java.awt.FontFormatException;

public class UI {

    private Map<String, BufferedImage> itemImages = new HashMap<>();
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
    public String description;

    String dialogues[] = new String[20];

    Font Jersey;

    Graphics2D g2;
    GamePanel gp;
    Item item = new Item();
    KeyHandler keyH = new KeyHandler(gp);
    
    public UI(GamePanel gp) {
        this.gp = gp;
        item.getItemImage(); // Load images and initialize descriptions

        
        
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

        // Initialize the item-image map
        itemImages.put("Key", item.Key);
        itemImages.put("Boots", item.Boots);
        itemImages.put("Berry", item.Berry);
        itemImages.put("Knife", item.Knife);
        itemImages.put("Pickaxe", item.Pickaxe);
        itemImages.put("Paper Roll", item.Paper_Roll);
        itemImages.put("Sword", item.Sword);
        itemImages.put("Carrots", item.Carrots);
        itemImages.put("Axe", item.Axe);
        itemImages.put("Potion", item.Potion);
        itemImages.put("Water Bottle", item.Water_Bottle);
        itemImages.put("Raw Berry", item.Raw_Berry);
        itemImages.put("none", null); // For empty slots
        itemImages.put("MissingTexture", item.MissingTexture);
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

        }
        if (
            gp.gameState != gp.gameOverState &&
            gp.gameState != gp.dialogueState &&
            gp.gameState != gp.titleState &&
            gp.gameState != gp.pauseState
            )
            {

            Color c = new Color(0, 0, 0, 200);
            int x = 458;
            int y = 500;
            g2.setColor(c);
            g2.fillRoundRect(456, 500, gp.tileSize, gp.tileSize, 15, 15);

            // Draw the border
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(456 + 2, 500 + 2, gp.tileSize, gp.tileSize, 15, 15);
            if (holding != "none") {
                BufferedImage image = itemImages.getOrDefault(holding, item.MissingTexture);
                g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
            }
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
        drawSubWindow(20, gp.tileSize, 270, height);

        final int slotXStart = x + 20;
        final int slotYStart = y + 20;
        int slotX = slotXStart;
        int slotY = slotYStart + 30;

        int cursorX = slotX + (slotCol * gp.tileSize);
        int cursorY = slotY + (slotRow * gp.tileSize);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
       
        while (gp.gameState == gp.inventoryState && itemIndex < gp.player.inventory.size()) {
            String itemName = gp.player.inventory.get(itemIndex);
            BufferedImage image = itemImages.getOrDefault(itemName, item.MissingTexture);

            g2.drawImage(image, slotX, slotY, gp.tileSize, gp.tileSize, null);
            itemIndex++;
            slotX += gp.tileSize;

            if (slotX >= slotXStart + (gp.tileSize * 6)) {
                slotX = slotXStart;
                slotY += gp.tileSize;
            }
        }
        
        if (enterPressed) {
            int index = slotCol + slotRow * 6;
            if (index < gp.player.inventory.size()) {
                if (!holding.equals("none")) {
                    gp.player.inventory.add(holding);
                }
                holding = gp.player.inventory.get(index);
                gp.player.inventory.remove(index);

                System.out.println("Holding item: " + holding);
            } else {
                System.out.println("No item to hold at index: " + index);
            }
            enterPressed = false;
        }
        
        itemIndex = 0;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        int itemNameY = 88;
        int itemNameX = 37;
        int descriptionY = 128;
        int descriptionX = 37;
        g2.setFont(Jersey.deriveFont(Font.PLAIN, 40F));
        g2.setColor(Color.white);
        g2.drawString("Inventory", x + 20, y + 40);
        if (slotCol + slotRow * 6 < gp.player.inventory.size()) {
            g2.drawString(gp.player.inventory.get(slotCol + slotRow * 6), itemNameX, itemNameY);
            g2.setFont(Jersey.deriveFont(Font.PLAIN, 30F));
            description = item.getDescription(gp.player.inventory.get(slotCol + slotRow * 6));

            // Wrap the description text
            String[] wrappedText = wrapText(description, 250); // 250 is the max width for the description
            int lineHeight = g2.getFontMetrics().getHeight();
            for (int i = 0; i < wrappedText.length; i++) {
                g2.drawString(wrappedText[i], descriptionX, descriptionY + (i * lineHeight));
            }
        }
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

    private String[] wrapText(String text, int maxWidth) {
        FontMetrics metrics = g2.getFontMetrics();
        String[] manualLines = text.split("\n"); // Split text by manual line breaks
        java.util.List<String> wrappedLines = new java.util.ArrayList<>();

        for (String manualLine : manualLines) {
            String[] words = manualLine.split(" ");
            StringBuilder line = new StringBuilder();

            for (String word : words) {
                if (metrics.stringWidth(line + word) > maxWidth) {
                    wrappedLines.add(line.toString());
                    line = new StringBuilder(word + " ");
                } else {
                    line.append(word).append(" ");
                }
            }
            if (!line.isEmpty()) {
                wrappedLines.add(line.toString());
            }
        }

        return wrappedLines.toArray(new String[0]);
    }
}