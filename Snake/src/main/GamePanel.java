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
    final int originalTileSize = 20;  // 16x16 px
    final int scale = 3;  // scale up x3

    public final int tileSize = originalTileSize * scale;  // 48x48 tile size
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // world settings
    public final int maxWorldCol = 50;
    public final int maxWordRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;
    public final int middleX = screenWidth / 2 - (tileSize / 2);

    int FPS = 10;

    public boolean gamePaused = false;
    public Snake snake;
    public Apple apple;
    public boolean gameOver;
    public KeyHandler keyHandler = new KeyHandler(snake);
    public CollisionDetector collisionChecker = new CollisionDetector(this);
    private Thread gameThread;
    private ScoreHandler scoreHandler;
    
    public GamePanel() {
        setupGame();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.CYAN);
        this.setDoubleBuffered(true);  // better rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.scoreHandler = new ScoreHandler();
    }

    public void setupGame() {
        System.out.println("\nStarting game");
        snake = new Snake(this);
        apple = new Apple(this);
        gameOver = false;
        keyHandler = new KeyHandler(snake);
        collisionChecker = new CollisionDetector(this);
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

        while (!gameOver) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        };


    }

    public void update() {
        snake.update();
        CollisionType collisionType = collisionChecker.checkCollision();
        if (collisionType == CollisionType.apple) {
            apple.setPosition();
            snake.addTail = true;
            scoreHandler.incrementScore();
        } else if (collisionType !=  CollisionType.none) {
            gameOver = true;
        };
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (gameOver) {
            g2.drawString("GAME OVER!! \n you scored: " + scoreHandler.getScore(), (screenWidth / 2) - 20, (screenHeight / 2) - 20);
            return;
        }
        apple.draw(g2);
        snake.draw(g2);
        g2.setColor(Color.BLACK);
        g2.drawString("Score: " + scoreHandler.getScore(), screenWidth - 60, 20);
        g2.dispose();  // clear resources
    }

}