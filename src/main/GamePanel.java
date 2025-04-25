package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    Font Jersey = new Font("Jersey", Font.PLAIN, 40);

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int inventoryState = 4;

    // INVENTORY
    public ArrayList<SuperObject> inventory = new ArrayList<>();

    public GamePanel()  {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/Jersey.ttf");
            Jersey = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40F);
            is = getClass().getResourceAsStream("/res/fonts/arial.ttf");
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(-99);
        }

    }
    public void setupGame()  {
        aSetter.setObject();
        gameState = titleState;
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        setFullScreen();
    }
    public void setFullScreen() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void startnewGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }


    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1)  {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                if(keyH.debugMode == true) {
                    
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
                }
            }
            
        }    

        update();
        repaint();
    }
    public void update() {

        if(gameState == playState) {
            player.update();

            // Example interaction logic
        }
        if(gameState == pauseState) {
            // do nothing
        }

    }
    public void drawToTempScreen() {
        // DEBUG

        long drawStart = 0;
        if (keyH.debugMode == true) {
            
        drawStart = System.nanoTime();
        }

        // TITLE SCREEN

        if(gameState == titleState) {
            ui.draw(g2);
        }
        else {
            // TILES
            tileM.draw(g2);

            // OBJECTS
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // PLAYER
            player.draw(g2);

            // UI
            ui.draw(g2);

            // INVENTORY
            drawInventory(g2);
        }

        // DEBUG

        if(keyH.debugMode == true) {
            

        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.setFont(Jersey.deriveFont(Font.PLAIN, 40F));
        g2.drawString("Draw Time: " + passed, 10, 25);
        g2.drawString("FPS: " + FPS, 10, 50);
        g2.drawString("Player X: " + player.worldX, 10, 75);
        g2.drawString("Player Y: " + player.worldY, 10, 100);
        g2.drawString("Player Speed: " + player.speed, 10, 125);
        g2.drawString("Player Direction: " + player.direction, 10, 150);
        g2.drawString("Health: " + player.health, 10, 175);
        }


    }
    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    

    // Method to add an item to the inventory
    public void addToInventory(SuperObject item) {
        inventory.add(item);
        System.out.println(item.name + " added to inventory.");
    }

    // Method to draw the inventory
    public void drawInventory(Graphics2D g2) {
        int x = 10; // Starting x position
        int y = 10; // Starting y position
        int tileSize = 48; // Size of each inventory slot

        for (int i = 0; i < inventory.size(); i++) {
            SuperObject item = inventory.get(i);
            g2.drawImage(item.image, x, y, tileSize, tileSize, null);
            x += tileSize + 10; // Move to the next slot
        }
    }
}
