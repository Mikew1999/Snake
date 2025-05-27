package main;

import main.KeyHandler.Keys;

public class CollisionDetector {
    private GamePanel gamePanel;

    public enum CollisionType {
        none,
        apple,
        wall,
        self
    }

    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    };

    public CollisionType checkCollision() {
        Keys facing = gamePanel.snake.currentDirection;
        if (checkHitApple(facing)) return CollisionType.apple;
        if (checkHitWall(facing)) return CollisionType.wall;
        return CollisionType.none;
    };

    private boolean checkHitWall(Keys facing) {
        switch (facing) {
            case up:
                if ((gamePanel.snake.yPos + (gamePanel.snake.height / 2)) < (gamePanel.snake.height / 2))
                    return true;
                break;
            case down:
                if (gamePanel.snake.yPos > (gamePanel.screenHeight - gamePanel.snake.height))
                    return true;
                break;
            case left:
                if (gamePanel.snake.xPos < 0)
                    return true;
                break;
            case right:
                if (gamePanel.snake.xPos > (gamePanel.screenWidth - gamePanel.snake.width))
                    return true;
                break;
            default:
                break;
        }
        return false;
    };

    private boolean checkUpDown() {
        return gamePanel.apple.xPos > gamePanel.snake.xPos && gamePanel.apple.xPos < (gamePanel.snake.xPos + gamePanel.snake.width);
    };

    private boolean checkLeftRight() {
        return gamePanel.apple.yPos > gamePanel.snake.yPos && gamePanel.apple.yPos < (gamePanel.snake.yPos + gamePanel.snake.height);
    };

    private boolean checkHitApple(Keys facing) {
        return (checkUpDown() && checkLeftRight());
    };

}
