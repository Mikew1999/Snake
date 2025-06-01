package entity;

import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;
import main.GamePanel;

public class Apple extends Entity {
    private GamePanel gamePanel;
    public int width = 10;
    public int height = 10;

    public Apple(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setPosition();
    };

    private int getPosWithinRange(int min, int max) {
        if (min % 10 != 0 || max % 10 != 0) throw new IllegalArgumentException("Min and max must be multiples of 10");
        Random random = new Random();
        int numOfMultiplesOf10 = (max - min) / 10 + 1;
        return min + (random.nextInt(numOfMultiplesOf10) * 10);
    };

    public void setPosition() {
        this.xPos = getPosWithinRange(20, this.gamePanel.screenWidth - 20);
        this.yPos = getPosWithinRange(20, this.gamePanel.screenHeight - 20);
    };

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.drawRect(xPos, yPos, width, height);
    }
}
