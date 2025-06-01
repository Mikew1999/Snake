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
    public LinkedList<Position> tail = new LinkedList<Position>();
    private LinkedList<Position> pathHistory = new LinkedList<Position>();

    public Keys lastDirection = Keys.down;
    public Keys currentDirection = Keys.down;

    public boolean addTail = false;

    public Snake(GamePanel gamePanel) {
        this.head = new Position(gamePanel.middleX, 10);
        this.tail.addFirst(head);
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

    public void handleUnpressed(Keys key) {};

    public void addToTail() {
        Position newPos;
        switch (currentDirection) {
            case right:
                newPos = new Position(head.xPos - width, head.yPos);
                break;
            case left:
                newPos = new Position(head.xPos + width, head.yPos);
                break;
            case up:
                newPos = new Position(head.xPos, head.yPos + height);
                break;
            case down:
                newPos = new Position(head.xPos, head.yPos - height);
                break;
            default:
                return;
        }

        this.tail.add(newPos);
    };

    public void update() {
        Position newPathPosition = new Position(head.xPos, head.yPos);
        switch (currentDirection) {
            case right:
                head.xPos += speed;
                newPathPosition.xPos = head.xPos + (speed * 2);
                break;
            case left:
                head.xPos -= speed;
                newPathPosition.xPos = head.xPos - (speed * 2);
                break;
            case up:
                head.yPos -= speed;
                newPathPosition.xPos = head.yPos - (speed * 2);
                break;
            case down:
                head.yPos += speed;
                newPathPosition.xPos = head.yPos + (speed * 2);
                break;
            default:
                break;
        }

        if (addTail) {
            addToTail();
            addTail = false;
        }

        pathHistory.addFirst(new Position(head.xPos, head.yPos));

        int tailLength = tail.size();
        while (pathHistory.size() > tailLength) {
            pathHistory.removeLast();
        }

        for (int i = 0; i < tailLength; i++) {
            Position pos = pathHistory.get(i);
            tail.set(i, new Position(pos.xPos, pos.yPos));
        }
    };

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.drawRect(head.xPos, head.yPos, width, height);
        for (int i = 0; i < tail.size(); i++) {
            g2.drawRect(tail.get(i).xPos, tail.get(i).yPos, width, height);
        };
    }
}
