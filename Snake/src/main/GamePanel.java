package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Apple;
import entity.Snake;
import main.CollisionDetector.CollisionType;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16;  // 16x16 px
    final int scale = 3;  // scale up x3

    public final int tileSize = originalTileSize * scale;  // 48x48 tile size
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // world settings
    public final int maxWorldCol = 50;
    public final int maxWordRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;
    public final int middleX = screenWidth / 2 - (tileSize / 2);

    int FPS = 60;

    public Snake snake = new Snake(this);
    public Apple apple = new Apple(this);
    public boolean gameOver = false;
    public KeyHandler keyHandler = new KeyHandler(snake);
    public CollisionDetector collisionChecker = new CollisionDetector(this);
    private Thread gameThread;
    private ScoreHandler scoreHandler;
    
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.CYAN);
        this.setDoubleBuffered(true);  // better rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.scoreHandler = new ScoreHandler();
    }

    public void setupGame() {
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    };

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;  // 1 second / 60
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (!gameOver) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                System.out.println(snake.xPos);
                System.out.println(snake.yPos);
                System.out.println();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        };


    }

    public void update() {
        snake.update();
        CollisionType collisionType = collisionChecker.checkCollision();
        if (collisionType == CollisionType.apple) {
            apple.setPosition();
            scoreHandler.incrementScore();
        } else if (collisionType !=  CollisionType.none) {
            gameOver = true;
        };
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;  // typecast graphics to 2d
        apple.draw(g2);
        snake.draw(g2);
        g2.setColor(Color.BLACK);
        g2.drawString("Score: " + scoreHandler.getScore(), screenWidth - 60, 20);
        g2.dispose();  // clear resources
    }

}