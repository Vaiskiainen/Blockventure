package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
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
    public final int gameOverState = 5;

    // INVENTORY
    public ArrayList<SuperObject> inventory = new ArrayList<>();

    // FULLSCREEN
    private GraphicsDevice graphicsDevice;
    private boolean isFullscreen = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Get the default screen device for fullscreen mode
        graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/Jersey.ttf");
            Jersey = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40F);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(-99);
        }
    }

    public void setupGame() {
        aSetter.setObject();
        gameState = titleState;
    }
    public void restartGame() {
        // Reset player state
        player.setDefaultValues();
        player.health = player.defaultHealth;
    
        // Reset game state
        gameState = titleState;
    
        // Clear inventory or other game data if needed
        player.inventory.clear();
        ui.holding = "none";
    
        // Reinitialize objects or tiles if necessary
        aSetter.setObject();
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

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                if (keyH.debugMode == true) {
                    System.out.println("FPS: " + drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {
            // do nothing
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            // TILES
            tileM.draw(g2);

            // OBJECTS
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // PLAYER
            player.draw(g2);

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyH.debugMode == true) {
            g2.setColor(Color.white);
            g2.setFont(Jersey.deriveFont(Font.PLAIN, 40F));
            g2.drawString("Player X: " + player.worldX, 10, 75);
            g2.drawString("Player Y: " + player.worldY, 10, 100);
            g2.drawString("Player Speed: " + player.speed, 10, 125);
            g2.drawString("Player Direction: " + player.direction, 10, 150);
            g2.drawString("Health: " + player.health, 10, 175);
        }

        g2.dispose();
    }

    // Method to add an item to the inventory
    public void addToInventory(SuperObject item) {
        inventory.add(item);
        System.out.println(item.name + " added to inventory.");
    }

    // Method to toggle fullscreen mode
    public void toggleFullscreen(JFrame frame) {
        if (!isFullscreen) {
            frame.dispose(); // Dispose the frame to apply fullscreen settings
            frame.setUndecorated(true); // Remove window borders
            graphicsDevice.setFullScreenWindow(frame); // Set fullscreen mode
            isFullscreen = true;
        } else {
            graphicsDevice.setFullScreenWindow(null); // Exit fullscreen mode
            frame.dispose(); // Dispose the frame to reset settings
            frame.setUndecorated(false); // Restore window borders
            frame.setVisible(true); // Make the frame visible again
            isFullscreen = false;
        }
    }
}

