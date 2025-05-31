package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import main.GamePanel;
import main.KeyHandler.Keys;

public class Snake extends Entity implements IHandleKeys {
    public int width = 10;
    public int height = 10;
    public int speed = 10;

    public Position head;
    public LinkedList<Position> tail;

    public Keys currentDirection = Keys.down;

    public Snake(GamePanel gamePanel) {
        this.head = new Position(gamePanel.middleX, 10);
        // this.tail
    }

    public void handlePressed(Keys key) {
        if (
            key == this.currentDirection
            || (this.currentDirection == Keys.up && key == Keys.down)
            || (this.currentDirection == Keys.left && key == Keys.right)
            || (this.currentDirection == Keys.down && key == Keys.up)
            || (this.currentDirection == Keys.right && key == Keys.left)
        ) return;
        this.currentDirection = key;
    };
    
    public void handleUnpressed(Keys key) {
    };

    public void update() {
        switch (currentDirection) {
            case right:
                head.xPos += speed;
                break;
            case left:
                head.xPos -= speed;
                break;
            case up:
                head.yPos -= speed;
                break;
            case down:
                head.yPos += speed;
                break;
            default:
                break;
        }
    };

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.drawRect(head.xPos, head.yPos, width, height);
    }
}
