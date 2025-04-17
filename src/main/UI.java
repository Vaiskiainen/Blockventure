package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.awt.Font;

public class UI {

    public boolean messageOn = false;
    public String message = "";
    public BufferedImage inventoryImage;
    int messageCounter = 0;

    Font arial_40;

    Graphics2D g2;
    GamePanel gp;
    
    public UI(GamePanel gp) {
        this.gp = gp;

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

        if(gp.gameState == gp.playState) {


        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
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

<<<<<<< HEAD
}
=======
} 
>>>>>>> ff59f8a301c2c4b2b780f9a269700f5c0505d6a2
