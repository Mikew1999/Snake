package entity;

import java.awt.Graphics2D;
import java.awt.Color;
import main.GamePanel;

public class Apple extends Entity {
    private GamePanel gamePanel;

    public Apple(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setPosition();
    };

    private int getPosWithinRange(int min, int max) {
        return (int) (Math.random() * (max + 1) + min); 
    };

    public void setPosition() {
        this.xPos = getPosWithinRange(20, this.gamePanel.screenWidth - 20);
        this.yPos = getPosWithinRange(20, this.gamePanel.screenHeight - 20);
    };

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.drawRect(xPos, yPos, 10, 10);
    }
}
